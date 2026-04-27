<script setup lang="ts">
import TheFooter from './components/layout/TheFooter.vue'
import Navbar from './components/layout/TheNavbar.vue'
</script>

<template>
  <Navbar />
  <main>
    <RouterView />
  </main>
  <TheFooter />
</template>

<style scoped></style>
