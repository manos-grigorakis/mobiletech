<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue'
import confetti from 'canvas-confetti'
import { CheckIcon } from '@lucide/vue'
import { useCartStore } from '@/stores/cart'

const cart = useCartStore()

// TODO: Replace with actual order number
const fakeOrderId = crypto.randomUUID()

onMounted(() => {
  confetti({
    particleCount: 150,
    spread: 80,
    origin: { y: 0.6 },
  })
})

onUnmounted(() => {
  cart.orderPlaced = false
})
</script>

<template>
  <div class="text-center">
    <div class="flex items-center justify-center w-16 h-16 mx-auto mb-4 bg-green-100 rounded-full">
      <CheckIcon class="w-8 h-8 text-green-500" />
    </div>
    <h3 class="mb-4 text-4xl font-bold">Mobile<span class="text-accent-500">Tech</span></h3>
    <p class="text-gray-500">Thank you for your purchase.</p>
    <span class="block mt-3 font-mono text-xs text-gray-400">ORDER NO: #{{ fakeOrderId }}</span>

    <RouterLink
      :to="{ name: 'products' }"
      class="block mt-4 text-sm text-primary-600 hover:underline"
      >Continue Shopping</RouterLink
    >
  </div>
</template>
