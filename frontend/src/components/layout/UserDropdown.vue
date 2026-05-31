<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { CircleUserIcon } from '@lucide/vue'
import { useAuthStore } from '@/stores/auth'
import router from '@/router'

const auth = useAuthStore()
const isOpen = ref(false)
const wrapperRef = ref<HTMLElement | null>(null)

const toggle = (e: MouseEvent) => {
  e.stopPropagation()
  isOpen.value = !isOpen.value
}

const handleClickOutside = (e: MouseEvent) => {
  if (wrapperRef.value && !wrapperRef.value.contains(e.target as Node)) {
    isOpen.value = false
  }
}

const logout = () => {
  auth.logout()
  isOpen.value = false
  router.push({ name: 'login' })
}

onMounted(() => document.addEventListener('click', handleClickOutside))
onUnmounted(() => document.removeEventListener('click', handleClickOutside))
</script>

<template>
  <div ref="wrapperRef" class="relative">
    <button @click="toggle" class="hover:text-accent-500 cursor-pointer flex items-center">
      <CircleUserIcon />
    </button>

    <div
      v-if="isOpen"
      class="absolute right-0 mt-48 w-48 bg-white rounded-lg border border-gray-100 shadow-sm z-50"
    >
      <div class="px-4 py-3 border-b border-gray-100">
        <p class="text-sm font-medium text-gray-900">
          {{ auth.user?.firstName }} {{ auth.user?.lastName }}
        </p>
        <p class="text-xs text-gray-500 truncate">{{ auth.user?.email }}</p>
      </div>

      <div class="py-1">
        <RouterLink
          :to="auth.dashboardRoute"
          @click="isOpen = false"
          class="flex items-center gap-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50"
        >
          {{ auth.isAdminOrManager ? 'Admin panel' : 'My account' }}
        </RouterLink>

        <div class="border-t border-gray-100 my-1" />

        <button
          @click="logout"
          class="w-full flex items-center gap-2 px-4 py-2 text-sm text-red-600 hover:bg-red-50 cursor-pointer"
        >
          Logout
        </button>
      </div>
    </div>
  </div>
</template>
