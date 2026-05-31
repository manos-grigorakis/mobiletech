<script setup lang="ts">
import AnalyticsCard from '@/components/admin/AnalyticsCard.vue'
import { useAnalyticsStore } from '@/stores/analytics'
import { formatPrice } from '@/utils/format-price.util'
import { computed, onMounted, ref } from 'vue'
import { Bar, Line, Doughnut } from 'vue-chartjs'
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
  ArcElement,
} from 'chart.js'
import MainButton from '@/components/ui/MainButton.vue'
import { RefreshCw } from '@lucide/vue'
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  PointElement,
  LineElement,
  ArcElement,
)

const analyticsStore = useAnalyticsStore()
const refreshing = ref(false)

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

const ordersByStatusChartData = computed(() => ({
  labels: analyticsStore.ordersByStatus.map((i) => i.orderStatus),
  datasets: [
    {
      data: analyticsStore.ordersByStatus.map((i) => i.count),
      backgroundColor: [
        '#6366f1',
        '#f59e0b',
        '#10b981',
        '#3b82f6',
        '#ef4444',
        '#8b5cf6',
        '#ec4899',
      ],
    },
  ],
}))

const ordersByStatusChartOptions = {
  responsive: true,
  maintainAspectRatio: true,
  cutout: '65%',
  plugins: {
    legend: {
      position: 'bottom' as const,
    },
  },
}

const fetchData = () => {
  analyticsStore.fetchTotalRevenue()
  analyticsStore.fetchUnitsSold()
  analyticsStore.fetchStockValue()
  analyticsStore.fetchProductsToRestock()
  analyticsStore.fetchRevenueByCategory()
  analyticsStore.fetchMonthlySalesTrend()
  analyticsStore.fetchTopSellingProducts()
  analyticsStore.fetchOrdersByStatus()
}

const handleRefresh = async () => {
  refreshing.value = true
  // Small delay for refresh icon animation
  const [_] = await Promise.all([fetchData(), new Promise((r) => setTimeout(r, 600))])
  refreshing.value = false
}

onMounted(() => {
  fetchData()
})
</script>

<template>
  <section>
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-xl font-semibold">Analytics Overview</h1>
      <div class="w-32">
        <MainButton title="Refresh" @click="handleRefresh">
          <RefreshCw :size="16" :class="{ 'animate-spin': refreshing }" />
        </MainButton>
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
        <p class="text-sm font-medium text-gray-600 mb-2">Orders by Status</p>

        <div class="h-80 flex justify-center items-center">
          <Doughnut
            id="orders-by-status"
            :data="ordersByStatusChartData"
            :options="ordersByStatusChartOptions"
          />
        </div>
      </div>

      <div class="bg-white rounded-md shadow-sm border border-gray-100 px-4 py-4">
        <p class="text-sm font-medium text-gray-600 mb-2">Monthly Revenue</p>
        <Line id="monthly-sales-trend" :data="monthlySalesTrendChartData" />
      </div>

      <div class="bg-white rounded-md shadow-sm border border-gray-100 px-4 py-4">
        <p class="text-sm font-medium text-gray-600 mb-2">Top Selling Products</p>
        <Bar id="top-selling-products" :data="topSellingProductsChartData" />
      </div>
    </div>
  </section>
</template>
