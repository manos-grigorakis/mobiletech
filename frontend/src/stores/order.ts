import type { OrderRequest } from '@/types/order-request'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import api from '@/api/api'
import type { Pagination } from '@/types/pagination'
import type { Order } from '@/types/order'

export const useOrderStore = defineStore('order', {
  state: () => ({
    orders: [] as Order[],
    orderId: null as number | null,
    pagination: undefined as Pagination | undefined,
    isLoading: false as boolean,
    hasError: false as boolean,
  }),

  persist: true,

  actions: {
    async fetchOrders(params = {}) {
      const loadingTimeout = setTimeout(() => (this.isLoading = true), 200)

      try {
        const res = await api.get('orders', { params })
        const data = res.data.data
        this.orders = data.content
        this.pagination = {
          totalElements: data.totalElements,
          last: data.last,
          size: data.size,
          number: data.number,
        }
      } catch (e) {
        useUiStore().setError('Failed to fetch orders')
      } finally {
        clearTimeout(loadingTimeout)
        this.isLoading = false
      }
    },

    async createOrder(payload: OrderRequest) {
      this.isLoading = true
      this.hasError = false

      try {
        const res = await api.post('/orders', payload)
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
