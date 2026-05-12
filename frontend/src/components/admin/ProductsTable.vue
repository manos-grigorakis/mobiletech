<script setup lang="ts">
import { useProductStore } from '@/stores/product'
import { PenIcon, Trash2Icon } from '@lucide/vue'
import { onMounted } from 'vue'
import ThePagination from '../ui/ThePagination.vue'

const productStore = useProductStore()

const handlePagination = (page: number) => {
  productStore.fetchProducts({ page: page })
}

onMounted(() => {
  productStore.fetchProducts()
})
</script>

<template>
  <div class="overflow-x-auto rounded-lg">
    <table class="w-full bg-white shadow-md mb-8">
      <thead class="text-sm text-left bg-gray-300 rounded text-body">
        <tr>
          <th class="w-12 px-6 py-3 font-medium">#</th>
          <th class="px-6 py-3 font-medium w-full">Name</th>
          <th class="w-32 px-6 py-3 font-medium">Brand</th>
          <th class="w-60 px-6 py-3 font-medium">Category</th>
          <th class="w-28 px-6 py-3 font-medium">Price (€)</th>
          <th class="w-20 px-6 py-3 font-medium">Stock</th>
          <th class="w-24 px-6 py-3 font-medium">Actions</th>
        </tr>
      </thead>

      <tbody v-if="productStore.isLoading">
        <tr>
          <td colspan="7" class="px-6 py-8 text-center text-gray-400">Loading...</td>
        </tr>
      </tbody>

      <tbody v-else>
        <tr v-if="productStore.products.length === 0">
          <td colspan="7" class="px-6 py-8 text-center text-gray-400">No products found</td>
        </tr>

        <tr
          v-else
          v-for="product in productStore.products"
          :key="product.id"
          class="hover:bg-gray-100"
        >
          <td class="w-16 px-6 py-4">{{ product.id }}</td>
          <td class="px-6 py-4">{{ product.name }}</td>
          <td class="px-6 py-4">{{ product.brand }}</td>
          <td class="px-6 py-4">{{ product.category.name }}</td>
          <td class="px-6 py-4">{{ product.price.toFixed(2) }}</td>
          <td class="px-6 py-4">{{ product.stock }}</td>
          <td class="px-6 py-4">
            <div class="flex items-center gap-4">
              <RouterLink
                :to="{ name: 'admin-edit-product', params: { id: product.id } }"
                class="text-primary-500 hover:text-primary-600 hover:cursor-pointer"
                ><PenIcon :size="18"
              /></RouterLink>
              <button @click="productStore.deleteProductById(product.id)">
                <Trash2Icon
                  :size="18"
                  class="text-red-500 hover:text-red-600 hover:cursor-pointer"
                />
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <ThePagination
      v-if="productStore.pagination && productStore.products.length > 0"
      :pagination="productStore.pagination"
      @next-page="handlePagination"
      @previous-page="handlePagination"
      @go-to-page="handlePagination"
      class="flex justify-center"
    />
  </div>
</template>
