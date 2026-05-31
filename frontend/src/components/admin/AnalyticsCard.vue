<script setup lang="ts">
const props = defineProps<{
  label: string
  value: string | number
}>()
</script>

<template>
  <div class="bg-white rounded-md shadow-sm border border-gray-100 px-4 py-3 w-full">
    <p class="text-sm font-light text-gray-500">{{ props.label }}</p>
    <p class="text-2xl font-bold mt-1">{{ props.value }}</p>
  </div>
</template>
