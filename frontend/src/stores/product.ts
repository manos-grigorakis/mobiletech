import type { Product } from '@/types/product'
import { defineStore } from 'pinia'
import axios from 'axios'

const API_URL = import.meta.env.VITE_API_URL

export const useProductStore = defineStore('product', {
  state: () => ({
    products: [] as Product[],
    product: null as Product | null,
    isLoading: false as boolean,
    error: null as string | null,
  }),

  actions: {
    async fetchProducts(params = {}) {
      this.isLoading = true

      try {
        const res = await axios.get(`${API_URL}/products`, { params })
        this.products = res.data.data.content
      } catch (e) {
        console.error('Failed to fetch products', e)
        this.error = 'Failed to fetch products'
      } finally {
        this.isLoading = false
      }
    },

    async fetchProduct(id: number) {
      this.isLoading = true

      try {
        const res = await axios.get(`${API_URL}/products/${id}`)
        this.product = res.data.data
      } catch (e) {
        console.error(`Failed to fetch product with id: ${id}`, e)
        this.error = 'Failed to fetch product'
      } finally {
        this.isLoading = false
      }
    },
  },
})
