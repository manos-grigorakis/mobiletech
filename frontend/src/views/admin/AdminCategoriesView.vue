<script setup lang="ts">
import CategoriesTable from '@/components/admin/CategoriesTable.vue'
</script>

<template>
  <section>
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-xl font-medium">Categories</h2>
      <RouterLink
        :to="{ name: 'admin-create-category' }"
        class="px-4 py-2 text-sm font-medium tracking-wide text-white transition-colors duration-200 rounded-lg bg-green-500 hover:bg-green-600 hover:cursor-pointer"
        >Create category</RouterLink
      >
    </div>
    <CategoriesTable />
  </section>
</template>
