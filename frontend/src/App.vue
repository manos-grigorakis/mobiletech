<script setup lang="ts">
import TheFooter from './components/layout/TheFooter.vue'
import Navbar from './components/layout/TheNavbar.vue'
import AnnouncementBar from './components/ui/AnnouncementBar.vue'
</script>

<template>
  <div class="flex flex-col min-h-screen">
    <AnnouncementBar />
    <Navbar />
    <main class="flex-1 mt-24">
      <RouterView />
    </main>
    <TheFooter />
  </div>
</template>

<style scoped></style>
