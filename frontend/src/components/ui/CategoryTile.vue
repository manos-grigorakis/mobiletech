<script setup lang="ts">
import type { Category } from '@/types/category'

const { category } = defineProps<{ category: Category }>()
</script>

<template>
  <RouterLink
    :to="{ path: '/products', query: { category: category.slug } }"
    class="h-full px-8 py-4 text-lg font-medium capitalize transition bg-white rounded-lg shadow-sm hover:shadow-md"
    >{{ category.name }}</RouterLink
  >
</template>
