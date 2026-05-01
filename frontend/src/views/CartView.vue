<script setup lang="ts">
import CartList from '@/components/cart/CartList.vue'
import CartSummary from '@/components/cart/CartSummary.vue'
</script>

<template>
  <section class="max-w-6xl px-4 py-8 mx-auto">
    <div class="grid items-start grid-cols-1 gap-6 lg:grid-cols-3">
      <div class="lg:col-span-2">
        <CartList />
      </div>

      <div class="lg:col-span-1 lg:sticky lg:top-6">
        <CartSummary />
      </div>
    </div>
  </section>
</template>
