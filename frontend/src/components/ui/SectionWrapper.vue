<script setup lang="ts">
const props = defineProps({ title: String })
</script>

<template>
  <section class="px-4 py-24">
    <div class="max-w-6xl mx-auto">
      <div class="mb-12">
        <h2 class="text-2xl font-semibold text-black/80">{{ props.title }}</h2>
      </div>

      <slot />
    </div>
  </section>
</template>
