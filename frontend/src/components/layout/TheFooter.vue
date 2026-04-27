<script setup lang="ts">
const year = new Date().getFullYear()
</script>

<template>
  <footer class="w-full py-8 text-xs text-center text-white bg-primary-900">
    <!-- Content -->
    <p>© {{ year }} MobileTech. All Rights Reserved.</p>
    <span class="block mt-2"
      >Built by
      <a href="https://manosgrigorakis.com" target="_blank" class="hover:text-accent-500"
        >Manos Grigorakis</a
      ></span
    >
  </footer>
</template>
