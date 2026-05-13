import api from '@/api/api'
import type { Product } from '@/types/product'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import type { Pagination } from '@/types/pagination'
import type { ProductRequest } from '@/types/product-request'
import axios from 'axios'
import { toProductFormData } from '@/utils/to-product-form-data.utils'

export const useProductStore = defineStore('product', {
  state: () => ({
    products: [] as Product[],
    product: null as Product | null,
    pagination: undefined as Pagination | undefined,
    isLoading: false as boolean,
    error: null as string | null,
    hasError: false as boolean,
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

    async createProduct(request: ProductRequest) {
      this.isLoading = true
      this.hasError = false

      try {
        await api.post('products', toProductFormData(request))
        useUiStore().setSuccess('Product created successfully')
        this.hasError = false
      } catch (e) {
        this.hasError = true
        if (axios.isAxiosError(e) && e.response?.status === 404) {
          useUiStore().setError('Category dont exist')
          return
        }

        useUiStore().setError('Failed to create product')
        throw e
      } finally {
        this.isLoading = false
      }
    },

    async updateProductById(id: number, request: ProductRequest) {
      this.isLoading = true
      this.hasError = false

      try {
        await api.put(`products/${id}`, toProductFormData(request))
        useUiStore().setSuccess(`Product with id ${id} updated successfully`)
        this.hasError = false
        this.product = null // reset product
      } catch (e) {
        this.hasError = true
        if (axios.isAxiosError(e) && e.response?.status === 404) {
          useUiStore().setError('Category dont exist')
        }

        useUiStore().setError(`Failed to updated product with id ${id}`)
        throw e
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
