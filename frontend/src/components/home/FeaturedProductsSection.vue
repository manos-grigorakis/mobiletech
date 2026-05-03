<script setup lang="ts">
import { useProductStore } from '@/stores/product'
import ProductCard from '../ui/ProductCard.vue'
import SectionWrapper from '../ui/SectionWrapper.vue'
import { onMounted } from 'vue'

const productStore = useProductStore()

onMounted(() => {
  productStore.fetchProducts({ size: 8 })
})
</script>

<template>
  <SectionWrapper title="Featured Products">
    <div class="grid grid-cols-2 gap-4 md:grid-cols-3 lg:grid-cols-4">
      <ProductCard v-for="product in productStore.products" :key="product.id" :product="product" />
    </div>
  </SectionWrapper>
</template>
