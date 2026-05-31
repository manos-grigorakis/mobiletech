<script setup lang="ts">
import AdminSidebar from '@/components/layout/AdminSidebar.vue'
import MainToast from '@/components/ui/MainToast.vue'
</script>

<template>
  <div class="flex min-h-screen ml-64">
    <AdminSidebar />
    <main class="flex-1 p-6">
      <MainToast />
      <RouterView />
    </main>
  </div>
</template>
