<script setup lang="ts">
import ProductsTable from '@/components/admin/ProductsTable.vue'
import MainButton from '@/components/ui/MainButton.vue'
import { useProductStore } from '@/stores/product'
import { RefreshCw } from '@lucide/vue'
import { ref } from 'vue'

const refreshing = ref(false)
const productStore = useProductStore()

const handleRefresh = async () => {
  refreshing.value = true

  // Small delay for refresh icon animation
  try {
    await Promise.all([
      productStore.fetchProducts(),
      new Promise((resolve) => setTimeout(resolve, 600)),
    ])
  } finally {
    refreshing.value = false
  }
}
</script>

<template>
  <section>
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-xl font-semibold">Products</h2>
      <div class="flex items-center gap-4">
        <div class="w-32 -mt-2">
          <MainButton title="Refresh" @click="handleRefresh" :disabled="refreshing">
            <RefreshCw :size="16" :class="{ 'animate-spin': refreshing }" />
          </MainButton>
        </div>

        <RouterLink
          :to="{ name: 'admin-create-product' }"
          class="px-4 py-3 text-sm font-medium tracking-wide text-white transition-colors duration-200 rounded-lg bg-green-500 hover:bg-green-600 hover:cursor-pointer"
          >Create product</RouterLink
        >
      </div>
    </div>
    <ProductsTable />
  </section>
</template>
