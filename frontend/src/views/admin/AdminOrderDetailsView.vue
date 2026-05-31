<script setup lang="ts">
import { useOrderStore } from '@/stores/order'
import { applyOrderStatusBadge } from '@/utils/apply-order-status-badge.util'
import { FormatDate } from '@/utils/format-date.util'
import { PhoneCallIcon } from '@lucide/vue'
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const orderStore = useOrderStore()
const selectedStatus = ref(orderStore.order?.orderStatus)

const customerInitials = computed(() => {
  const order = orderStore.order
  if (!order) return ''
  return (order.firstName?.[0] ?? '') + (order.lastName?.[0] ?? '')
})

const updateStatus = async () => {
  if (!orderStore.order || !selectedStatus.value) return
  await orderStore.updateOrderStatusById(orderStore.order.id, selectedStatus.value)
}

onMounted(() => {
  const id = parseInt(route.params.id as string)
  if (!id) return
  orderStore.fetchOrderById(id)
})
</script>

<template>
  <section>
    <div v-if="orderStore.order">
      <div class="flex items-start justify-between flex-wrap gap-3 mb-6">
        <div>
          <h3 class="text-xl font-medium">Order #{{ orderStore.order.id }}</h3>
          <div class="flex items-center gap-2 mt-1">
            <span class="text-sm text-gray-400"
              >Created {{ FormatDate(orderStore.order.createdAt) }}</span
            >
            <span
              class="text-xs px-2 py-0.5 rounded-full uppercase border font-medium"
              :class="applyOrderStatusBadge(orderStore.order.orderStatus)"
            >
              {{ orderStore.order.orderStatus }}
            </span>
          </div>
        </div>
        <div class="flex items-center gap-2">
          <select
            class="text-sm px-3 py-2 rounded-md border border-gray-200 bg-white focus:outline-none focus:border-primary-500"
            v-model="selectedStatus"
          >
            <option value="pending">Pending</option>
            <option value="processing">Processing</option>
            <option value="confirmed">Confirmed</option>
            <option value="shipped">Shipped</option>
            <option value="delivered">Delivered</option>
            <option value="canceled">Canceled</option>
          </select>
          <button
            @click="updateStatus"
            class="p-2 text-sm font-medium tracking-wide text-white transition-colors duration-200 rounded-lg disabled:cursor-not-allowed disabled:bg-gray-500 bg-primary-600 hover:bg-primary-700 hover:cursor-pointer"
          >
            Update status
          </button>
        </div>
      </div>

      <!-- Cards -->
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-3 mb-4">
        <!-- Customer card -->
        <div class="bg-white rounded-lg shadow-xs px-5 py-4 border border-gray-100">
          <p class="text-xs font-medium text-gray-400 uppercase tracking-wider mb-3">Customer</p>
          <div class="flex items-center mb-3 gap-2">
            <div
              class="w-9 h-9 rounded-full bg-primary-100 text-primary-800 flex items-center justify-center text-sm font-medium shrink-0"
            >
              {{ customerInitials }}
            </div>
            <div>
              <p class="capitalize font-medium text-sm">
                {{ orderStore.order.firstName + ' ' + orderStore.order.lastName }}
              </p>
              <p class="text-gray-500 text-xs">
                {{ orderStore.order.email.toLocaleLowerCase() }}
              </p>
            </div>
          </div>

          <div class="border-t border-gray-100 pt-3 flex items-center gap-2 text-sm text-gray-500">
            <PhoneCallIcon :size="13" /> {{ orderStore.order.phone ?? '-' }}
          </div>
        </div>

        <!-- Shipping -->
        <div class="bg-white border border-gray-100 shadow-xs rounded-lg px-5 py-4">
          <p class="text-xs font-medium text-gray-400 uppercase tracking-wider mb-3">
            Shipping address
          </p>
          <p class="text-sm">{{ orderStore.order.address }}</p>
          <p class="text-sm text-gray-500">
            {{ orderStore.order.city }}, {{ orderStore.order.postalCode }}
          </p>
          <p class="text-sm text-gray-500">{{ orderStore.order.country }}</p>
        </div>
      </div>

      <!-- Items -->
      <div class="bg-white border shadow-xs border-gray-100 rounded-lg overflow-hidden">
        <p
          class="text-xs font-medium text-gray-400 uppercase tracking-wider px-4 py-3 border-b border-gray-100"
        >
          Order items
        </p>
        <table class="w-full text-sm">
          <thead class="bg-gray-50">
            <tr>
              <th class="text-left px-4 py-3 text-gray-400 font-normal">Product ID</th>
              <th class="text-center px-4 py-3 text-gray-400 font-normal">Qty</th>
              <th class="text-right px-4 py-3 text-gray-400 font-normal">Unit price</th>
              <th class="text-right px-4 py-3 text-gray-400 font-normal">Subtotal</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="item in orderStore.order.orderItems"
              :key="item.id"
              class="border-t border-gray-100 hover:bg-gray-50"
            >
              <td class="px-4 py-3">#{{ item.id }}</td>
              <td class="px-4 py-3 text-center">{{ item.quantity }}</td>
              <td class="px-4 py-3 text-right">€{{ item.price.toFixed(2) }}</td>
              <td class="px-4 py-3 text-right">€{{ (item.quantity * item.price).toFixed(2) }}</td>
            </tr>
          </tbody>
          <tfoot>
            <tr class="border-t border-gray-200">
              <td colspan="3" class="px-4 py-3 text-right font-medium">Total</td>
              <td class="px-4 py-3 text-right font-medium">
                €{{ orderStore.order.totalAmount.toFixed(2) }}
              </td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>
  </section>
</template>
