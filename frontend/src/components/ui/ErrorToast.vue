<script setup lang="ts">
import { useUiStore } from '@/stores/ui'

const ui = useUiStore()
</script>

<template>
  <Transition name="fade">
    <div
      v-if="ui.error"
      class="fixed z-50 flex items-center justify-between gap-4 px-4 py-3 text-sm text-white bg-red-500 rounded-md shadow-lg bottom-4 right-4"
    >
      <span>{{ ui.error }}</span>
      <button @click="ui.clearError()" class="text-white hover:text-red-100">✕</button>
    </div>
  </Transition>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
