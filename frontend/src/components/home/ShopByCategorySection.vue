<script setup lang="ts">
import { useCategoryStore } from '@/stores/category'
import CategoryTile from '../ui/CategoryTile.vue'
import SectionWrapper from '../ui/SectionWrapper.vue'
import { onMounted } from 'vue'

const categoryStore = useCategoryStore()

onMounted(() => categoryStore.fetchCategories())
</script>

<template>
  <SectionWrapper title="Shop by Category">
    <div class="grid gap-4 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4">
      <CategoryTile
        v-for="category in categoryStore.categories"
        :key="category.id"
        :category="category"
      />
    </div>
  </SectionWrapper>
</template>
