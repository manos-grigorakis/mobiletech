<script setup lang="ts">
import ProductDetail from '@/components/products/ProductDetail.vue'
import ProductCard from '@/components/ui/ProductCard.vue'
import SectionWrapper from '@/components/ui/SectionWrapper.vue'
import { products } from '@/data/Products'
</script>

<template>
  <ProductDetail />
  <SectionWrapper title="Featured Products">
    <div class="grid grid-cols-2 gap-4 lg:grid-cols-4">
      <ProductCard v-for="product in products.slice(0, 4)" :key="product.id" :product="product" />
    </div>
  </SectionWrapper>
</template>
