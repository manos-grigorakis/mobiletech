<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
</script>

<template>
  <section class="px-4">
    <h2>Hello {{ auth.user?.firstName }}</h2>
  </section>
</template>
