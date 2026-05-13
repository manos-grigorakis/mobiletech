<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const isAdmin = computed(() => route.path.startsWith('/admin'))
</script>

<template>
  <section class="flex flex-col items-center justify-center px-20 pt-10 text-center">
    <span class="font-bold leading-none text-8xl text-accent-500"> 404 </span>
    <h2 class="mt-3 text-2xl font-semibold tracking-wide uppercase text-foreground">
      Page not found
    </h2>
    <h3 class="mt-3 text-sm leading-relaxed text-foreground/50">
      The page you're looking for doesn't exist.
    </h3>
    <RouterLink
      :to="isAdmin ? { name: 'admin' } : { name: 'products' }"
      class="mt-4 hover:underline hover:text-primary-500"
    >
      {{ isAdmin ? 'Back to Dashboard' : 'Explore Products' }}
    </RouterLink>
  </section>
</template>
