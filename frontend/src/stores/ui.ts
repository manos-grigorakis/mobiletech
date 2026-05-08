import { defineStore } from 'pinia'

export const useUiStore = defineStore('ui', {
  state: () => ({
    error: null as string | null,
  }),

  actions: {
    setError(message: string, timeout = 4000) {
      this.error = message
      setTimeout(() => (this.error = null), timeout)
    },
    clearError() {
      this.error = null
    },
  },
})
