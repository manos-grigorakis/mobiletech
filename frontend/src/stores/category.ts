import type { Category } from '@/types/category'
import axios from 'axios'
import { defineStore } from 'pinia'

const API_URL = import.meta.env.VITE_API_URL

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
        const res = await axios.get(`${API_URL}/categories`)
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
