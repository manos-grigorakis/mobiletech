import type { OrderRequest } from '@/types/order-request'
import axios from 'axios'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'

const API_URL = import.meta.env.VITE_API_URL

export const useOrderStore = defineStore('order', {
  state: () => ({
    orderId: null as number | null,
    isLoading: false as boolean,
    hasError: false as boolean,
  }),

  persist: true,

  actions: {
    async createOrder(payload: OrderRequest) {
      this.isLoading = true
      this.hasError = false

      try {
        const res = await axios.post(`${API_URL}/orders`, payload)
        this.orderId = res.data.data.id
      } catch (e) {
        const ui = useUiStore()
        ui.setError('Failed to create order. Please try again')
        this.hasError = true
        return
      } finally {
        this.isLoading = false
      }
    },
  },
})
