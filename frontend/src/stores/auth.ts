import api from '@/api/api'
import type { LoginRequest } from '@/types/login-request'
import type { User } from '@/types/user'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import { jwtDecode } from 'jwt-decode'
import type { JwtPayload } from '@/types/jwt-payload'
import type { RegisterRequest } from '@/types/register-request'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null,
    token: null as string | null,
    isAuthenticated: false as boolean,
  }),

  persist: {
    pick: ['user', 'token', 'isAuthenticated'],
  },

  getters: {
    isAdminOrManager: (state) => state.user?.role === 'ADMIN' || state.user?.role === 'MANAGER',
    dashboardRoute: (state) => {
      if (state.user?.role === 'ADMIN' || state.user?.role === 'MANAGER') {
        return { name: 'admin' }
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
      } catch (e) {
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

    logout() {
      this.token = null
      this.isAuthenticated = false
    },
  },
})
