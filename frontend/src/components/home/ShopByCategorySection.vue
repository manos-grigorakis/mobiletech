<script setup lang="ts">
import CategoryTile from '../ui/CategoryTile.vue'
import SectionWrapper from '../ui/SectionWrapper.vue'
import { categories } from '@/data/Categories'
</script>

<template>
  <SectionWrapper title="Shop by Category">
    <div class="grid gap-4 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4">
      <CategoryTile v-for="category in categories" :key="category.id" :category="category" />
    </div>
  </SectionWrapper>
</template>
