import { defineStore } from 'pinia'

export const useUiStore = defineStore('ui', {
  state: () => ({
    toast: null as { message: string; type: 'success' | 'warning' | 'error' } | null,
  }),

  actions: {
    setSuccess(message: string, timeout = 4_000) {
      this.toast = { message, type: 'success' }
      setTimeout(() => (this.toast = null), timeout)
    },

    setWarning(message: string, timeout = 4_000) {
      this.toast = { message, type: 'warning' }
      setTimeout(() => (this.toast = null), timeout)
    },

    setError(message: string, timeout = 4_000) {
      this.toast = { message, type: 'error' }
      setTimeout(() => (this.toast = null), timeout)
    },

    clearToast() {
      this.toast = null
    },
  },
})
