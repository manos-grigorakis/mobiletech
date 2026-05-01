<script setup lang="ts">
import { useRoute } from 'vue-router'
import { products } from '@/data/Products'
import { useStockStatus } from '@/composables/useStockStatus'
import { computed } from 'vue'
import router from '@/router'
import MainButton from '../ui/MainButton.vue'

const route = useRoute()
const product = products.find((p) => p.id === Number(route.params.id))

if (!product) router.push({ name: 'home' })

const getImageUrl = (image: string) => {
  return new URL(`../../assets/products/${image}`, import.meta.url).href
}

const { stockLabel, stockClasses } = useStockStatus(computed(() => product?.stock ?? 0))
</script>

<template>
  <section v-if="product" class="max-w-4xl px-6 mx-auto mt-24">
    <p class="mb-6 text-xs tracking-widest text-gray-400 uppercase">
      {{ product.category }}
    </p>

    <div class="grid items-center grid-cols-1 gap-10 md:grid-cols-2">
      <!-- Image -->
      <div
        class="flex items-center justify-center overflow-hidden rounded-xl bg-neutral-100 aspect-4/5"
      >
        <img
          :src="getImageUrl(product.image)"
          :alt="`${product.name} image`"
          class="object-contain w-full h-full"
        />
      </div>

      <!-- Content -->
      <div class="flex flex-col gap-4">
        <div>
          <p class="mb-1 text-xs tracking-widest uppercase text-neutral-400">{{ product.brand }}</p>
          <div class="flex justify-between">
            <h2 class="text-2xl font-medium">{{ product.name }}</h2>
            <span class="text-3xl font-medium text-right">€{{ product.price }}</span>
          </div>
        </div>

        <span :class="stockClasses" class="flex items-center gap-2 text-xs font-medium">
          <span class="inline-block w-2 h-2 bg-current rounded-full opacity-70"></span>
          {{ stockLabel }}
        </span>

        <p class="pt-4 text-sm leading-relaxed text-neutral-500 border-neutral-200">
          {{ product.description }}
        </p>

        <!-- Meta -->
        <div class="flex flex-col gap-2 pt-4 text-sm border-t border-neutral-200">
          <div class="flex justify-between">
            <span class="text-neutral-400">Category</span>
            <span class="capitalize">{{ product.category }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-neutral-400">Brand</span>
            <span>{{ product.brand }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-neutral-400">Stock</span>
            <span>{{ product.stock }} units</span>
          </div>
        </div>

        <MainButton title="Add to card" :disabled="product.stock === 0" />
      </div>
    </div>
  </section>
</template>
