import type { OrderRequest } from '@/types/order-request'
import axios from 'axios'
import { defineStore } from 'pinia'

const API_URL = import.meta.env.VITE_API_URL

export const useOrderStore = defineStore('order', {
  state: () => ({
    orderId: null as number | null,
    isLoading: false as boolean,
    error: null as string | null,
  }),

  persist: true,

  actions: {
    async createOrder(payload: OrderRequest) {
      this.isLoading = true

      try {
        const res = await axios.post(`${API_URL}/orders`, payload)
        this.orderId = res.data.data.id
      } catch (e) {
        console.error(`Failed to create order`, e)
        this.error = 'Failed to create order. Please try again'
      } finally {
        this.isLoading = false
      }
    },
  },
})
