import type { OrderRequest } from '@/types/order-request'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import api from '@/api/api'
import type { Pagination } from '@/types/pagination'
import type { Order } from '@/types/order'

export const useOrderStore = defineStore('order', {
  state: () => ({
    orders: [] as Order[],
    order: null as Order | null,
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

    async fetchOrderById(id: number) {
      this.isLoading = true

      try {
        const res = await api.get(`orders/${id}`)
        this.order = res.data.data
      } catch (e) {
        useUiStore().setError(`Failed to fetch order with id ${id}`)
      } finally {
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

    async updateOrderStatusById(id: number, status: string) {
      this.isLoading = true
      this.hasError = false

      try {
        await api.patch(`orders/${id}/status`, { status })
        useUiStore().setSuccess(`Order with id ${id} updated successfully`)
        if (this.order) {
          this.order.orderStatus = status
        }
      } catch (e) {
        useUiStore().setError(`Failed to update order status with id ${id}`)
        this.hasError = true
      } finally {
        this.isLoading = false
      }
    },
  },
})
