<script setup lang="ts">
import { EyeIcon } from '@lucide/vue'
import { onMounted } from 'vue'
import ThePagination from '../ui/ThePagination.vue'
import { useOrderStore } from '@/stores/order'
import { applyOrderStatusBadge } from '@/utils/apply-order-status-badge.util'

const ordersStore = useOrderStore()

const handlePagination = (page: number) => {
  ordersStore.fetchOrders({ page: page })
}

onMounted(() => {
  ordersStore.fetchOrders()
})
</script>

<template>
  <div class="overflow-x-auto rounded-lg">
    <table class="w-full bg-white shadow-md mb-8">
      <thead class="text-sm text-left bg-gray-300 rounded text-body">
        <tr>
          <th class="w-12 px-6 py-3 font-medium">#</th>
          <th class="w-32 px-6 py-3 font-medium">Status</th>
          <th class="w-32 px-6 py-3 font-medium">Customer Email</th>
          <th class="w-28 px-6 py-3 font-medium">Total Amount (€)</th>
          <th class="w-12 px-6 py-3 font-medium">Items Count</th>
          <th class="w-20 px-6 py-3 font-medium">Actions</th>
        </tr>
      </thead>

      <tbody v-if="ordersStore.isLoading">
        <tr>
          <td colspan="7" class="px-6 py-8 text-center text-gray-400">Loading...</td>
        </tr>
      </tbody>

      <tbody v-else>
        <tr v-if="ordersStore.orders.length === 0">
          <td colspan="7" class="px-6 py-8 text-center text-gray-400">No orders found</td>
        </tr>

        <tr v-else v-for="order in ordersStore.orders" :key="order.id" class="hover:bg-gray-100">
          <td class="w-16 px-6 py-4">{{ order.id }}</td>
          <td class="px-6 py-4">
            <span
              :class="applyOrderStatusBadge(order.orderStatus.toLocaleLowerCase())"
              class="px-2 text-xs py-1 border rounded text-sm uppercase"
              >{{ order.orderStatus }}</span
            >
          </td>
          <td class="px-6 py-4 truncate">{{ order.email }}</td>
          <td class="px-6 py-4">{{ order.totalAmount.toFixed(2) }}</td>
          <td class="px-6 py-4">{{ order.orderItems.length }}</td>
          <td class="px-6 py-4">
            <RouterLink
              :to="{ name: 'admin-view-order', params: { id: order.id } }"
              class="text-primary-500 hover:text-primary-600 hover:cursor-pointer"
              ><EyeIcon :size="18"
            /></RouterLink>
          </td>
        </tr>
      </tbody>
    </table>

    <ThePagination
      v-if="ordersStore.pagination && ordersStore.orders.length > 0"
      :pagination="ordersStore.pagination"
      @next-page="handlePagination"
      @previous-page="handlePagination"
      @go-to-page="handlePagination"
      class="flex justify-center"
    />
  </div>
</template>
