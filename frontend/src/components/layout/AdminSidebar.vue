<script setup lang="ts">
import router from '@/router'
import { useAuthStore } from '@/stores/auth'
import { Package, ShoppingBag, Tag, PieChart, type LucideIcon } from '@lucide/vue'

const navLinks: {
  label: string
  routeName: string
  icon: LucideIcon
}[] = [
  { label: 'Dashboard', routeName: 'admin-dashboard', icon: PieChart },
  { label: 'Categories', routeName: 'admin-categories', icon: Tag },
  { label: 'Products', routeName: 'admin-products', icon: Package },
  { label: 'Orders', routeName: 'admin-orders', icon: ShoppingBag },
]

const auth = useAuthStore()
const logout = () => {
  auth.logout()
  router.push({ name: 'home' })
}
</script>

<template>
  <aside class="left-0 z-40 w-64 h-screen text-white bg-primary-800" aria-label="Sidebar">
    <div class="flex flex-col h-full px-3 py-4 overflow-y-auto">
      <RouterLink to="/"
        ><h1 class="text-2xl font-semibold">
          Mobile<span class="text-accent-500">Tech</span>
        </h1></RouterLink
      >

      <!-- Links -->
      <ul class="mt-4 space-y-2 font-medium">
        <li v-for="link in navLinks" :key="link.label">
          <RouterLink
            :to="{ name: link.routeName }"
            class="flex items-center gap-2 px-2 py-1.5 rounded hover:bg-accent-700"
          >
            <component :is="link.icon" :size="20" />

            {{ link.label }}</RouterLink
          >
        </li>
      </ul>

      <div class="px-3 py-4 mt-auto border-t border-primary-700">
        <p class="text-sm font-medium">{{ auth.user?.firstName }} {{ auth.user?.lastName }}</p>
        <p class="text-xs text-gray-400 truncate">{{ auth.user?.email }}</p>
        <button
          @click="logout"
          class="mt-2 text-sm text-red-400 hover:text-red-300 hover:cursor-pointer"
        >
          Logout
        </button>
      </div>
    </div>
  </aside>
</template>
