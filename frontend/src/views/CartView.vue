<script setup lang="ts">
import CartSummary from '@/components/cart/CartSummary.vue'
</script>

<template>
  <section class="max-w-6xl px-4 py-8 mx-auto">
    <div class="grid items-start grid-cols-1 gap-6 lg:grid-cols-3">
      <div class="order-2 lg:col-span-2 lg:order-1">
        <router-view />
      </div>

      <div class="order-1 lg:col-span-1 lg:sticky lg:top-6 lg:order-2">
        <CartSummary />
      </div>
    </div>
  </section>
</template>
