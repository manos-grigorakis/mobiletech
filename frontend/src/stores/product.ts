import api from '@/api/axios'
import type { Product } from '@/types/product'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import type { Pagination } from '@/types/pagination'

export const useProductStore = defineStore('product', {
  state: () => ({
    products: [] as Product[],
    product: null as Product | null,
    pagination: undefined as Pagination | undefined,
    isLoading: false as boolean,
    error: null as string | null,
  }),

  actions: {
    async fetchProducts(params = {}) {
      const loadingTimeout = setTimeout(() => (this.isLoading = true), 200)

      try {
        const res = await api.get('/products', { params })
        const data = res.data.data
        this.products = data.content
        this.pagination = {
          totalElements: data.totalElements,
          last: data.last,
          size: data.size,
          number: data.number,
        }
      } catch (e) {
        console.error('Failed to fetch products', e)
        this.error = 'Failed to fetch products'
      } finally {
        clearTimeout(loadingTimeout)
        this.isLoading = false
      }
    },

    async fetchProduct(id: number) {
      this.isLoading = true

      try {
        const res = await api.get(`/products/${id}`)
        this.product = res.data.data
      } catch (e) {
        console.error(`Failed to fetch product with id: ${id}`, e)
        this.error = 'Failed to fetch product'
      } finally {
        this.isLoading = false
      }
    },

    async deleteProductById(id: number) {
      const loadingTimeout = setTimeout(() => (this.isLoading = true), 200)

      try {
        await api.delete(`products/${id}`)
        this.products = this.products.filter((p) => p.id !== id)
        useUiStore().setSuccess(`Product with id ${id} deleted successfully!`)
      } catch (e) {
        useUiStore().setError(`Failed to delete product with id ${id}`)
      } finally {
        clearTimeout(loadingTimeout)
        this.isLoading = false
      }
    },
  },
})
