<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import CartIcon from '../ui/CartIcon.vue'

const scrolled = ref(false)
const isNavbarOpen = ref(false)
const navLinks: { label: string; path: string; query?: Record<string, string> }[] = [
  { label: 'All', path: '/products' },
  { label: 'smartphones', path: '/products', query: { category: 'smartphones' } },
  { label: 'refurbished', path: '/products', query: { category: 'refurbished-phones' } },
  { label: 'accessories', path: '/accessories' },
]

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

const onScroll = () => {
  scrolled.value = window.scrollY > 40
}

// Lifecycle
onMounted(() => {
  onScroll()
  window.addEventListener('resize', handleResize)
  window.addEventListener('scroll', onScroll)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  window.removeEventListener('scroll', onScroll)
})
</script>

<template>
  <nav
    :class="scrolled ? 'mt-0' : 'mt-9'"
    class="fixed z-40 w-full p-4 text-white bg-primary-800 transition-all duration-300"
  >
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

      <div class="flex items-center gap-4">
        <!-- Cart -->
        <CartIcon @click="closeNavbar" class="md:hidden" />

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
      </div>

      <!-- Nav links -->
      <div class="hidden md:flex md:gap-x-10">
        <ul class="flex gap-10 text-sm">
          <li
            v-for="link in navLinks"
            :key="link.label"
            class="inline-block capitalize cursor-pointer hover:text-accent-500"
          >
            <RouterLink :to="{ path: link.path, query: link.query }" @click="closeNavbar">{{
              link.label
            }}</RouterLink>
          </li>
        </ul>

        <!-- Cart -->
        <CartIcon @click="closeNavbar" />
      </div>
    </div>

    <!-- Mobile -->
    <div
      v-if="isNavbarOpen"
      :class="scrolled ? 'mt-0' : 'mt-9'"
      class="fixed inset-0 z-50 px-4 py-6 text-white bg-primary-800"
    >
      <!-- Header -->
      <div class="flex items-center justify-between">
        <RouterLink to="/"
          ><h1 class="text-2xl font-semibold">
            Mobile<span class="text-accent-500">Tech</span>
          </h1></RouterLink
        >

        <div class="flex items-center gap-4">
          <!-- Cart -->
          <CartIcon @click="closeNavbar" />

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
      </div>

      <!-- Links -->
      <transition name="mobileNav">
        <ul v-show="isNavbarOpen" class="flex flex-col gap-2 mt-6 text-sm">
          <li
            v-for="link in navLinks"
            :key="link.label"
            class="py-2 capitalize cursor-pointer hover:text-accent-500"
          >
            <RouterLink :to="{ path: link.path, query: link.query }" @click="closeNavbar">{{
              link.label
            }}</RouterLink>
          </li>
        </ul>
      </transition>
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
