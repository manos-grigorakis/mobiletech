<script setup lang="ts">
import ProductCard from '@/components/ui/ProductCard.vue'
import SectionWrapper from '@/components/ui/SectionWrapper.vue'
import { useProductStore } from '@/stores/product'
import { computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const productStore = useProductStore()

const selectedCategory = computed(() => {
  return route.query.category as string | undefined
})

const fetchProducts = (category?: string | null) => {
  productStore.fetchProducts({ size: 12, page: 0, category: category ?? null })
}

onMounted(() => {
  fetchProducts(selectedCategory.value)
})

watch(selectedCategory, (val) => fetchProducts(val))
</script>

<template>
  <SectionWrapper title="Products">
    <div v-if="selectedCategory" class="mb-4 text-sm text-gray-500 capitalize">
      Category: {{ selectedCategory }}
    </div>

    <div class="grid grid-cols-2 gap-4 md:grid-cols-3 lg:grid-cols-4">
      <ProductCard v-for="product in productStore.products" :key="product.id" :product="product" />
    </div>
  </SectionWrapper>
</template>
