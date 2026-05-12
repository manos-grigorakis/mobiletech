import api from '@/api/api'
import type { Category } from '@/types/category'
import { defineStore } from 'pinia'

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
  },
})
