<script setup lang="ts">
import ProductCard from '../ui/ProductCard.vue'
import SectionWrapper from '../ui/SectionWrapper.vue'
import { products } from '@/data/Products'
</script>

<template>
  <SectionWrapper title="Featured Products">
    <div class="grid grid-cols-2 gap-4 md:grid-cols-3 lg:grid-cols-4">
      <ProductCard v-for="product in products" :key="product.id" :product="product" />
    </div>
  </SectionWrapper>
</template>
