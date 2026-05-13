<script setup lang="ts">
import { onMounted } from 'vue'
import { useCategoryStore } from '@/stores/category'

const categoryStore = useCategoryStore()

onMounted(() => {
  categoryStore.fetchCategories()
})
</script>

<template>
  <div class="overflow-x-auto rounded-lg">
    <table class="w-full bg-white shadow-md mb-8">
      <thead class="text-sm text-left bg-gray-300 rounded text-body">
        <tr>
          <th class="w-12 px-6 py-3 font-medium">#</th>
          <th class="px-6 py-3 font-medium">Name</th>
          <th class="px-6 py-3 font-medium">Slug</th>
        </tr>
      </thead>

      <tbody v-if="categoryStore.isLoading">
        <tr>
          <td colspan="7" class="px-6 py-8 text-center text-gray-400">Loading...</td>
        </tr>
      </tbody>

      <tbody v-else>
        <tr v-if="categoryStore.categories.length === 0">
          <td colspan="7" class="px-6 py-8 text-center text-gray-400">No categories found</td>
        </tr>

        <tr
          v-else
          v-for="category in categoryStore.categories"
          :key="category.id"
          class="hover:bg-gray-100"
        >
          <td class="w-16 px-6 py-4">{{ category.id }}</td>
          <td class="px-6 py-4">{{ category.name }}</td>
          <td class="px-6 py-4">{{ category.slug }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
