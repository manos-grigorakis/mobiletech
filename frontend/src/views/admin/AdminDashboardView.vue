<script setup lang="ts">
import AnalyticsCard from '@/components/admin/AnalyticsCard.vue'
import { useAnalyticsStore } from '@/stores/analytics'
import { formatPrice } from '@/utils/format-price.util'
import { computed, onMounted } from 'vue'
import { Bar, Line } from 'vue-chartjs'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  PointElement,
  LineElement,
} from 'chart.js'
import MainButton from '@/components/ui/MainButton.vue'
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  PointElement,
  LineElement,
)

const analyticsStore = useAnalyticsStore()

const totalRevenue = computed(() =>
  analyticsStore.totalRevenue != null ? '€' + formatPrice(analyticsStore.totalRevenue) : '-',
)

const stockValue = computed(() =>
  analyticsStore.stockValue != null ? '€' + formatPrice(analyticsStore.stockValue) : '-',
)

const revenueByCategoryChartData = computed(() => ({
  labels: analyticsStore.revenueByCategory.map((i) => i.category),
  datasets: [
    {
      label: 'Revenue by Category',
      data: analyticsStore.revenueByCategory.map((i) => i.amount),
      backgroundColor: ['#6366f1', '#f59e0b', '#10b981', '#3b82f6', '#ef4444', '#8b5cf6'],
    },
  ],
}))

const monthlySalesTrendChartData = computed(() => ({
  labels: analyticsStore.monthlySalesTrend.map((i) => `${i.month}/${i.year}`),
  datasets: [
    {
      label: 'Monthly Revenue',
      data: analyticsStore.monthlySalesTrend.map((i) => i.revenue),
      borderColor: '#6366f1',
      backgroundColor: '#6366f1',
      tension: 0.4,
    },
  ],
}))

const topSellingProductsChartData = computed(() => ({
  labels: analyticsStore.topSellingProducts.map((i) => i.name),
  datasets: [
    {
      label: 'Units Sold',
      data: analyticsStore.topSellingProducts.map((i) => i.totalSold),
      backgroundColor: ['#6366f1', '#f59e0b', '#10b981', '#3b82f6', '#ef4444', '#8b5cf6'],
    },
  ],
}))

const refresh = () => {
  analyticsStore.fetchTotalRevenue()
  analyticsStore.fetchUnitsSold()
  analyticsStore.fetchStockValue()
  analyticsStore.fetchProductsToRestock()
  analyticsStore.fetchRevenueByCategory()
  analyticsStore.fetchMonthlySalesTrend()
  analyticsStore.fetchTopSellingProducts()
}

onMounted(() => {
  refresh()
})
</script>

<template>
  <section>
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-xl font-semibold">Analytics Overview</h1>
      <div class="w-32">
        <MainButton title="Refresh" @click="refresh" />
      </div>
    </div>

    <!-- Cards -->
    <div class="grid grid-cols-4 gap-4">
      <AnalyticsCard label="Total Revenue" :value="totalRevenue" />
      <AnalyticsCard label="Units Sold" :value="analyticsStore.unitsSold ?? '-'" />
      <AnalyticsCard label="Stock Value" :value="stockValue" />
      <AnalyticsCard label="Products to Restock" :value="analyticsStore.productsToRestock ?? '-'" />
    </div>

    <!-- Charts -->
    <div class="grid grid-cols-2 gap-4 mt-6">
      <div class="bg-white rounded-md shadow-sm border border-gray-100 px-4 py-4">
        <p class="text-sm font-medium text-gray-600 mb-2">Revenue by Category</p>
        <Bar id="revenue-by-category" :data="revenueByCategoryChartData" />
      </div>

      <div class="bg-white rounded-md shadow-sm border border-gray-100 px-4 py-4">
        <p class="text-sm font-medium text-gray-600 mb-2">Monthly Revenue</p>
        <Line id="monthly-sales-trend" :data="monthlySalesTrendChartData" />
      </div>

      <div class="bg-white rounded-md shadow-sm border border-gray-100 px-4 py-4">
        <p class="text-sm font-medium text-gray-600 mb-2">Monthly Revenue</p>
        <Bar id="top-selling-products" :data="topSellingProductsChartData" />
      </div>
    </div>
  </section>
</template>
