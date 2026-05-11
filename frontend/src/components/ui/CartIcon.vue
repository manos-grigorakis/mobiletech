<script setup lang="ts">
import { ShoppingCartIcon } from '@lucide/vue'
import { useCartStore } from '@/stores/cart'

const cart = useCartStore()
</script>

<template>
  <RouterLink :to="{ name: 'cart' }" class="relative">
    <ShoppingCartIcon
      :size="24"
      class="transition-colors duration-300 hover:text-accent-500 hover:cursor-pointer"
    />

    <span
      v-if="cart.totalItems > 0"
      class="absolute -top-2 -right-2 flex items-center justify-center w-4 h-4 text-[10px] font-bold text-white bg-accent-600 rounded-full"
    >
      {{ cart.totalItems > 99 ? '99+' : cart.totalItems }}
    </span>
  </RouterLink>
</template>
