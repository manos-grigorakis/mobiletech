<script setup lang="ts">
import ProductCard from '@/components/ui/ProductCard.vue'
import SectionWrapper from '@/components/ui/SectionWrapper.vue'
import { products } from '@/data/Products'
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const selectedCategory = computed(() => {
  return route.query.category as string | undefined
})

// Filter products based on category
const filteredProducts = computed(() => {
  if (!selectedCategory.value) return products

  return products.filter((product) => product.category === selectedCategory.value)
})
</script>

<template>
  <SectionWrapper title="Products">
    <div v-if="selectedCategory" class="mb-4 text-sm text-gray-500 capitalize">
      Category: {{ selectedCategory }}
    </div>

    <div class="grid grid-cols-2 gap-4 md:grid-cols-3 lg:grid-cols-4">
      <ProductCard v-for="product in filteredProducts" :key="product.id" :product="product" />
    </div>
  </SectionWrapper>
</template>
