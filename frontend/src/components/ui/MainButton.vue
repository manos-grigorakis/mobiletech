<script setup lang="ts">
const props = defineProps<{ title: string; disabled?: boolean }>()
</script>

<template>
  <button
    :disabled="props.disabled"
    class="w-full py-3 mt-2 text-sm font-medium tracking-wide text-white transition-colors duration-200 rounded-lg disabled:cursor-not-allowed disabled:bg-gray-500 bg-primary-600 hover:bg-primary-700 hover:cursor-pointer"
  >
    {{ props.title }}
  </button>
</template>
