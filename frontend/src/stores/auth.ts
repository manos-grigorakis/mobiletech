import api from '@/api/api'
import type { LoginRequest } from '@/types/login-request'
import type { User } from '@/types/user'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import { jwtDecode } from 'jwt-decode'
import type { JwtPayload } from '@/types/jwt-payload'
import type { RegisterRequest } from '@/types/register-request'
import router from '@/router'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null,
    token: null as string | null,
    isAuthenticated: false as boolean,
    sessionExpiresAt: null as number | null,
  }),

  persist: {
    pick: ['user', 'token', 'isAuthenticated', 'sessionExpiresAt'],
  },

  getters: {
    isAdminOrManager: (state) => state.user?.role === 'ADMIN' || state.user?.role === 'MANAGER',
    isDemoUser: (state) => state.user?.role === 'DEMO',
    dashboardRoute: (state) => {
      if (
        state.user?.role === 'ADMIN' ||
        state.user?.role === 'MANAGER' ||
        state.user?.role === 'DEMO'
      ) {
        return { name: 'admin-dashboard' }
      }
      return { name: 'account' }
    },
  },

  actions: {
    async login(payload: LoginRequest) {
      try {
        const res = await api.post('auth/login', payload)
        this.token = res.data.data.token
        const decoded = jwtDecode<JwtPayload>(this.token!)
        this.user = {
          id: decoded.id,
          email: decoded.sub,
          firstName: decoded.firstName,
          lastName: decoded.lastName,
          role: decoded.role,
        }

        this.isAuthenticated = true
        this.sessionExpiresAt = decoded.exp! * 1000
        this.scheduleLogout()
      } catch (e) {
        console.error(e)
        this.isAuthenticated = false
        useUiStore().setError('Invalid credentials')
      }
    },

    async register(payload: RegisterRequest): Promise<boolean> {
      try {
        await api.post('/auth/register', payload)
        useUiStore().setSuccess('Account created successfully!')
        return true
      } catch (e) {
        useUiStore().setError('An error occurred. Please try again')
        return false
      }
    },

    scheduleLogout() {
      if (!this.sessionExpiresAt) return
      const msUntilExpiration = this.sessionExpiresAt - Date.now()

      // 2 minutes before expiration show banner
      if (msUntilExpiration > 120_000) {
        setTimeout(() => {
          useUiStore().setWarning('Your session expires in 2 minutes', 119_000)
        }, msUntilExpiration - 120_000)
      }

      setTimeout(() => {
        useUiStore().clearToast()
        useUiStore().setWarning('Your session has expired. Please log in again.')
        setTimeout(() => {
          this.logout()
          router.push({ name: 'login' })
        }, 500)
      }, msUntilExpiration)
    },

    logout() {
      this.token = null
      this.isAuthenticated = false
      this.sessionExpiresAt = null
    },
  },
})
