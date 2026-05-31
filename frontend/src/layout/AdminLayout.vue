<script setup lang="ts">
import AdminSidebar from '@/components/layout/AdminSidebar.vue'
import MainToast from '@/components/ui/MainToast.vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
</script>

<template>
  <div class="flex min-h-screen ml-64">
    <AdminSidebar />
    <main class="flex-1 p-6">
      <!-- Demo User Banner -->
      <div
        v-if="authStore.isDemoUser"
        class="w-full bg-primary-50 border-b border-primary-200 px-6 py-2 text-sm text-primary-700 text-center mb-6"
      >
        You are viewing a read-only demo. Create, edit and delete actions are disabled.
      </div>

      <MainToast />
      <RouterView />
    </main>
  </div>
</template>
