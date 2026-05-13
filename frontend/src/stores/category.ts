import api from '@/api/api'
import type { Category } from '@/types/category'
import type { CategoryRequest } from '@/types/category-request'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import axios from 'axios'

export const useCategoryStore = defineStore('category', {
  state: () => ({
    categories: [] as Category[],
    isLoading: false as boolean,
    error: null as string | null,
  }),

  actions: {
    async fetchCategories() {
      this.isLoading = true

      try {
        const res = await api.get('/categories')
        this.categories = res.data.data
      } catch (e) {
        console.error('Failed to fetch categories', e)
        this.error = 'Failed to fetch categories'
      } finally {
        this.isLoading = false
      }
    },

    async createCategory(request: CategoryRequest) {
      this.isLoading = true

      try {
        await api.post('/categories', request)
        useUiStore().setSuccess('Category created successfully!')
      } catch (e) {
        useUiStore().setError('Failed to create category')
      } finally {
        this.isLoading = false
      }
    },

    async deleteCategoryById(id: number) {
      const loadingTimeout = setTimeout(() => (this.isLoading = true), 200)

      try {
        await api.delete(`categories/${id}`)
        this.categories = this.categories.filter((c) => c.id !== id)
        useUiStore().setSuccess(`Category with id ${id} deleted successfully!`)
      } catch (e) {
        if (
          axios.isAxiosError(e) &&
          e.response?.status === 409 &&
          e.response?.data?.error?.errorCode === 'CATEGORY_HAS_PRODUCTS'
        ) {
          useUiStore().setError('Cannot delete category with associated products')
          return
        }

        useUiStore().setError(`Failed to delete category with id ${id}`)
      } finally {
        clearTimeout(loadingTimeout)
        this.isLoading = false
      }
    },
  },
})
