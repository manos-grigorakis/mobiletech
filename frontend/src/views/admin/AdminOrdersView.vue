<script setup lang="ts">
import OrdersTable from '@/components/admin/OrdersTable.vue'
import MainButton from '@/components/ui/MainButton.vue'
import { useOrderStore } from '@/stores/order'
import { RefreshCw } from '@lucide/vue'
import { ref } from 'vue'

const orderStore = useOrderStore()
const refreshing = ref(false)

const handleRefresh = async () => {
  refreshing.value = true

  // Small delay for refresh icon animation
  try {
    await Promise.all([
      orderStore.fetchOrders(),
      new Promise((resolve) => setTimeout(resolve, 600)),
    ])
  } finally {
    refreshing.value = false
  }
}
</script>

<template>
  <!-- Header -->
  <div class="flex justify-between items-center mb-6">
    <h2 class="text-xl font-semibold">Orders</h2>

    <div class="w-32">
      <MainButton title="Refresh" @click="handleRefresh" :disabled="refreshing">
        <RefreshCw :size="16" :class="{ 'animate-spin': refreshing }" />
      </MainButton>
    </div>
  </div>
  <OrdersTable />
</template>
