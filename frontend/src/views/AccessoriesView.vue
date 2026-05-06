<script setup lang="ts">
import CategoryTile from '@/components/ui/CategoryTile.vue'
import { useCategoryStore } from '@/stores/category'
import { computed, onMounted } from 'vue'

const categoryStore = useCategoryStore()
const excludedCategories: string[] = ['smartphones', 'refurbished-phones']

const filteredCategories = computed(() => {
  return categoryStore.categories.filter(
    (category) => !excludedCategories.includes(category.slug.toLocaleLowerCase()),
  )
})

onMounted(() => categoryStore.fetchCategories())
</script>

<template>
  <section class="px-4 py-24">
    <div class="grid gap-4 sm:grid-cols-2 max-w-6xl mx-auto md:grid-cols-3 lg:grid-cols-4">
      <CategoryTile
        v-for="category in filteredCategories"
        :key="category.id"
        :category="category"
      />
    </div>
  </section>
</template>
