<script setup lang="ts">
import { ref } from 'vue'
import { LucideCheck } from '@lucide/vue'

const email = ref('')
const subscribed = ref(false)

const handleSubscribe = () => {
  if (!email.value) return
  subscribed.value = true
}
</script>

<template>
  <section class="text-center bg-accent-500 py-24 px-4">
    <div class="flex flex-col justify-center">
      <h4 class="font-medium text-2xl mb-2">Get mobile deals & updates</h4>
      <p>Subscribe to receive future product updates and seasonal offers.</p>

      <div class="mx-auto text-center max-w-lg mt-6">
        <div v-if="subscribed" class="flex flex-col items-center gap-2">
          <div
            class="bg-primary-900 w-8 h-8 rounded-full p-1 text-center flex items-center justify-center"
          >
            <LucideCheck class="text-white w-5 h-5" />
          </div>
          <p>You're subscribed! Check your inbox soon.</p>
        </div>

        <div v-else class="flex flex-col gap-4 sm:flex-row sm:gap-2 justify-center items-center">
          <input
            v-model="email"
            type="email"
            class="bg-neutral-100 border border-gray-200 text-sm rounded w-full px-3 py-2.5 shadow-xs focus:outline-none focus:border-primary-500"
            placeholder="e.g. john.doe@gmail.com"
          />
          <button
            @click="handleSubscribe"
            class="px-4 py-2 font-medium text-white transition-colors duration-300 rounded bg-primary-900 hover:bg-primary-800 hover:cursor-pointer"
          >
            Subscribe
          </button>
        </div>
      </div>
    </div>
  </section>
</template>
