<script setup lang="ts">
import { useCartStore } from '@/stores/cart'
import { Plus, Minus, Trash2 } from '@lucide/vue'

const cart = useCartStore()
</script>

<template>
  <div class="flex flex-col bg-white rounded-md shadow-sm">
    <div class="px-4 pt-4 pb-3 border-b border-neutral-100">
      <h3 class="text-xl font-semibold">
        Cart
        <span class="ml-1 text-sm font-normal text-gray-400">({{ cart.items.length }} items)</span>
      </h3>
    </div>

    <div v-if="cart.items.length > 0">
      <div
        v-for="item in cart.items"
        :key="item.id"
        class="flex gap-4 p-4 border-b border-neutral-100 last:border-0"
      >
        <!-- Image -->
        <RouterLink :to="{ name: 'product-details', params: { id: item.id } }">
          <img :src="item.imageUrl" :alt="`${item.name} image`" class="object-contain w-24 h-24" />
        </RouterLink>

        <!-- Content -->
        <div class="flex flex-col justify-between flex-1">
          <div>
            <span class="block mb-1 text-xs text-gray-500 capitalize"
              >{{ item.category.name }} • {{ item.brand }}</span
            >

            <div class="flex items-start justify-between">
              <RouterLink :to="{ name: 'product-details', params: { id: item.id } }">
                <h3 class="text-lg font-medium hover:underline">{{ item.name }}</h3>
              </RouterLink>
              <Trash2
                @click="cart.removeFromCart(item.id)"
                class="w-5 h-5 text-gray-400 transition-colors duration-300 hover:cursor-pointer hover:text-red-500"
              />
            </div>
          </div>

          <!-- Price & Quantity -->
          <div class="flex items-center justify-between">
            <div>
              <span class="text-lg font-semibold text-primary-800">
                {{ (item.price * item.quantity).toFixed(2) }}€
              </span>

              <span class="block text-xs text-gray-400"
                >{{ item.price }}€ x {{ item.quantity }}</span
              >
            </div>

            <!-- Quantity -->
            <div class="flex items-center gap-3">
              <Minus
                @click="cart.updateQuantity(item.id, item.quantity - 1)"
                :class="[
                  'w-5 h-5 rounded-full',
                  item.quantity === 1
                    ? 'bg-gray-200 text-gray-400 cursor-not-allowed'
                    : 'bg-gray-100 text-gray-500 hover:text-gray-700 cursor-pointer',
                ]"
              />

              <span class="w-4 text-sm font-medium text-center">{{ item.quantity }}</span>
              <Plus
                @click="
                  item.quantity < item.stock && cart.updateQuantity(item.id, item.quantity + 1)
                "
                :class="[
                  'w-5 h-5 rounded-full',
                  item.quantity >= item.stock
                    ? 'bg-gray-200 text-gray-400 cursor-not-allowed'
                    : 'bg-gray-100 text-gray-500 hover:text-gray-700 cursor-pointer',
                ]"
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Empty Cart -->
    <div v-else class="flex flex-col items-center gap-3 py-16 text-center text-gray-400">
      <p class="text-gray-400">Your cart is empty.</p>
      <RouterLink :to="{ name: 'products' }" class="text-sm text-primary-600 hover:underline"
        >Browse products</RouterLink
      >
    </div>
  </div>
</template>
