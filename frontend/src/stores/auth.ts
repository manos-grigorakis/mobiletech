import api from '@/api/axios'
import type { LoginRequest } from '@/types/login-request'
import type { User } from '@/types/user'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import { jwtDecode } from 'jwt-decode'
import type { JwtPayload } from '@/types/jwt-payload'

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

    logout() {
      this.token = null
      this.isAuthenticated = false
    },
  },
})
