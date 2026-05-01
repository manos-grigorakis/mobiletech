<script setup lang="ts">
import type { Product } from '@/types/product'
import { computed } from 'vue'
import { RouterLink } from 'vue-router'
import { useStockStatus } from '@/composables/useStockStatus'
import MainButton from './MainButton.vue'
import { useCartItem } from '@/composables/useCartItem'

const { product } = defineProps<{ product: Product }>()

const getImageUrl = (image: String) => {
  return new URL(`../../assets/products/${image}`, import.meta.url).href
}

const { stockLabel, stockClasses } = useStockStatus(computed(() => product.stock))
const { isDisabled, isMaxStock, addToCart } = useCartItem(product)
</script>

<template>
  <div class="flex flex-col px-4 py-5 transition rounded-lg shadow-sm bg-white/80 hover:shadow-md">
    <RouterLink :to="{ name: 'product-details', params: { id: product.id } }" class="h-full">
      <!-- Image -->
      <div>
        <img
          :src="getImageUrl(product.image)"
          :alt="`${product.name} image`"
          class="w-auto h-40 mx-auto rounded-md"
        />
      </div>

      <!-- Content -->
      <div class="flex flex-col mt-6 grow">
        <span class="text-sm text-gray-500 capitalize"
          >{{ product.brand }} • {{ product.category }}</span
        >
        <h3 class="mt-1 text-lg font-medium">{{ product.name }}</h3>
        <span :class="stockClasses" class="text-xs font-medium tracking-wide">{{
          stockLabel
        }}</span>
        <p class="mt-2 text-sm font-light grow">{{ product.description }}</p>
        <span class="block mt-3 text-xl font-semibold text-right text-primary-800"
          >{{ product.price }}€</span
        >
      </div>
    </RouterLink>

    <MainButton
      @click="addToCart"
      :title="isMaxStock ? 'Max stock reached' : 'Add to cart'"
      :disabled="isDisabled"
    />
  </div>
</template>
