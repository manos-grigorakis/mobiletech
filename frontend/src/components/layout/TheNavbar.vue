<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { ShoppingCartIcon } from '@lucide/vue'

const isNavbarOpen = ref(false)
const navLinks = ['smartphones', 'refurbished', 'accessories', 'deals', 'support']

// Close navbar
const closeNavbar = () => {
  isNavbarOpen.value = false
}

// Toggles navbar
const toggleNavbar = () => {
  isNavbarOpen.value = !isNavbarOpen.value
}

// Make sures navbar will be closed in a certain window size and above
const handleResize = () => {
  if (window.innerWidth >= 1280) {
    isNavbarOpen.value = false
  }
}

// Lifecycle
onMounted(() => {
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})
</script>

<template>
  <nav ref="desktopNav" class="z-50 p-4 text-white bg-primary-800">
    <!-- Desktop -->
    <div class="flex items-center justify-between md:justify-around">
      <!-- Logo -->
      <div>
        <RouterLink to="/"
          ><h1 class="text-2xl font-semibold">
            Mobile<span class="text-accent-500">Tech</span>
          </h1></RouterLink
        >
      </div>

      <!-- Open mobile navbar -->
      <button
        type="button"
        class="-m-2.5 md:hidden inline-flex items-center justify-center hover:cursor-pointer rounded-md p-2.5"
        @click="toggleNavbar"
      >
        <span class="sr-only">Open main menu</span>
        <svg
          class="w-10 h-10"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="white"
          aria-hidden="true"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
          />
        </svg>
      </button>

      <!-- Nav links -->
      <div class="hidden md:flex md:gap-x-20">
        <ul class="flex gap-10 text-sm">
          <li
            v-for="link in navLinks"
            :key="link"
            class="inline-block capitalize cursor-pointer hover:text-accent-500"
          >
            <RouterLink :to="'/' + link" @click="closeNavbar">{{ link }}</RouterLink>
          </li>
        </ul>

        <!-- Cart -->
        <RouterLink :to="{ name: 'cart' }">
          <ShoppingCartIcon
            class="transition-colors duration-300 hover:text-accent-500 hover:cursor-pointer"
          />
        </RouterLink>
      </div>
    </div>

    <!-- Mobile -->
    <div v-if="isNavbarOpen" class="fixed inset-0 z-50 px-6 py-6 text-white bg-primary-800">
      <!-- Header -->
      <div class="flex items-center justify-between">
        <RouterLink to="/"
          ><h1 class="text-2xl font-semibold">
            Mobile<span class="text-accent-500">Tech</span>
          </h1></RouterLink
        >

        <!-- Mobile close button navbar -->
        <button
          @click="closeNavbar"
          type="button"
          class="-m-2.5 rounded-md hover:cursor-pointer p-2.5"
        >
          <span class="sr-only">Close menu</span>
          <svg
            class="w-10 h-10"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="white"
            aria-hidden="true"
          >
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>

      <!-- Links -->
      <transition name="mobileNav">
        <ul v-show="isNavbarOpen" class="flex flex-col gap-2 mt-6 text-sm">
          <li
            v-for="link in navLinks"
            :key="link"
            class="px-6 py-2 capitalize cursor-pointer hover:text-accent-500"
          >
            <RouterLink :to="'/' + link" @click="closeNavbar">{{ link }}</RouterLink>
          </li>
        </ul>
      </transition>

      <!-- Cart -->
      <RouterLink :to="{ name: 'cart' }">
        <ShoppingCartIcon
          class="mt-2 ml-6 transition-colors duration-300 hover:text-accent-500 hover:cursor-pointer"
        />
      </RouterLink>
    </div>
  </nav>
</template>

<style scoped>
/* Navbar transition */
.mobileNav-enter-from,
.mobileNav-leave-to {
  transform: translateY(-80px);
  opacity: 0;
}
.mobileNav-enter-active {
  transition: all 0.4s ease-out;
}
.mobileNav-enter-to,
.mobileNav-leave-from {
  transform: translateY(0);
  opacity: 1;
}

.mobileNav-leave-active {
  transition: all 0.4s ease-in;
}
</style>
