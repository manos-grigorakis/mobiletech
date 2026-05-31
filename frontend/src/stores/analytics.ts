import api from '@/api/api'
import type { MonthlySalesTrendItem } from '@/types/monthly-sales-trend-item'
import type { RevenueByCategoryItem } from '@/types/revenue-by-category-item'
import type { TopSellingProductsItem } from '@/types/top-selling-products-item'
import { defineStore } from 'pinia'

export const useAnalyticsStore = defineStore('analytics', {
  state: () => ({
    totalRevenue: null as number | null,
    unitsSold: null as number | null,
    productsToRestock: null as number | null,
    stockValue: null as number | null,
    revenueByCategory: [] as RevenueByCategoryItem[],
    monthlySalesTrend: [] as MonthlySalesTrendItem[],
    topSellingProducts: [] as TopSellingProductsItem[],
  }),

  actions: {
    async fetchTotalRevenue() {
      try {
        const res = await api.get('/analytics/total-revenue')
        this.totalRevenue = res.data.data.value
      } catch (e) {
        console.error('Failed to fetch total revenue', e)
      }
    },

    async fetchUnitsSold() {
      try {
        const res = await api.get('/analytics/units-sold')
        this.unitsSold = res.data.data.value
      } catch (e) {
        console.error('Failed to fetch units sold', e)
      }
    },

    async fetchProductsToRestock() {
      try {
        const res = await api.get('/analytics/products-re-stock')
        this.productsToRestock = res.data.data.value
      } catch (e) {
        console.error('Failed to fetch products to restock', e)
      }
    },

    async fetchStockValue() {
      try {
        const res = await api.get('/analytics/stock-value')
        this.stockValue = res.data.data.value
      } catch (e) {
        console.error('Failed to fetch stock value', e)
      }
    },

    async fetchRevenueByCategory() {
      try {
        const res = await api.get('/analytics/revenue-by-category')
        this.revenueByCategory = res.data.data
      } catch (e) {
        console.error('Failed to fetch stock value', e)
      }
    },

    async fetchMonthlySalesTrend() {
      try {
        const res = await api.get('/analytics/monthly-sales-trend')
        this.monthlySalesTrend = res.data.data
      } catch (e) {
        console.error('Failed to fetch monthly sales trend', e)
      }
    },

    async fetchTopSellingProducts(limit?: number) {
      try {
        const res = await api.get('/analytics/top-selling-products', { params: { limit } })
        this.topSellingProducts = res.data.data
      } catch (e) {
        console.error('Failed to fetch top selling products', e)
      }
    },
  },
})
