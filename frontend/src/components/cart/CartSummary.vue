<script setup lang="ts">
import { useCartStore } from '@/stores/cart'
import MainButton from '../ui/MainButton.vue'

const cart = useCartStore()
</script>

<template>
  <div class="p-5 bg-white rounded-md shadow-sm">
    <h3 class="mb-4 text-xl font-semibold">Cart Summary</h3>

    <!-- Items -->
    <div
      v-for="item in cart.items"
      :key="item.id"
      class="flex items-center justify-between mb-1.5 text-sm"
    >
      <span class="text-gray-600">{{ item.quantity }} x {{ item.name }}</span>
      <span class="font-medium">{{ (item.quantity * item.price).toFixed(2) }}€</span>
    </div>

    <!-- Totals -->
    <div class="flex flex-col gap-1 mt-4 mb-6">
      <div class="flex flex-col gap-1.5 py-4 border-t border-gray-200">
        <div class="flex justify-between text-sm">
          <p class="text-gray-600">Subtotal:</p>
          <p>{{ cart.totalPrice.toFixed(2) }}€</p>
        </div>

        <div class="flex justify-between text-sm text-gray-500">
          <p>Shipping:</p>
          <p class="font-medium text-green-600">Free</p>
        </div>
      </div>

      <div class="flex justify-between py-2 text-lg font-semibold border-t border-gray-200">
        <p>Total:</p>
        <p>{{ cart.totalPrice.toFixed(2) }}€</p>
      </div>
    </div>

    <MainButton title="Proceed to Checkout" />
  </div>
</template>
