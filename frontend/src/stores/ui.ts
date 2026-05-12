import { defineStore } from 'pinia'

export const useUiStore = defineStore('ui', {
  state: () => ({
    toast: null as { message: string; type: 'success' | 'error' } | null,
  }),

  actions: {
    setSuccess(message: string, timeout = 4000) {
      this.toast = { message, type: 'success' }
      setTimeout(() => (this.toast = null), timeout)
    },

    setError(message: string, timeout = 4000) {
      this.toast = { message, type: 'error' }
      setTimeout(() => (this.toast = null), timeout)
    },

    clearToast() {
      this.toast = null
    },
  },
})
