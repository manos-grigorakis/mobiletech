<script setup lang="ts">
import ProductDetail from '@/components/products/ProductDetail.vue'
import ProductCard from '@/components/ui/ProductCard.vue'
import SectionWrapper from '@/components/ui/SectionWrapper.vue'
import { useProductStore } from '@/stores/product'
import { onMounted } from 'vue'

const productStore = useProductStore()

onMounted(() => productStore.fetchProducts({ size: 4 }))
</script>

<template>
  <ProductDetail />
  <SectionWrapper title="Featured Products">
    <div class="grid grid-cols-2 gap-4 lg:grid-cols-4">
      <ProductCard v-for="product in productStore.products" :key="product.id" :product="product" />
    </div>
  </SectionWrapper>
</template>
