<script setup lang="ts">
import type { Pagination } from '@/types/pagination'
import { computed } from 'vue'

const props = defineProps<{
  pagination: Pagination
}>()

const emit = defineEmits<{
  nextPage: [pageNumber: number]
  previousPage: [pageNumber: number]
  goToPage: [page: number]
}>()

const totalPages = computed(() => Math.ceil(props.pagination.totalElements / props.pagination.size))
const visiblePages = computed(() => {
  const current = props.pagination.number + 1
  const total = totalPages.value
  const delta = 2

  const start = Math.max(1, current - delta)
  const end = Math.min(total, current + delta)
  return Array.from({ length: end - start + 1 }, (_, i) => start + i)
})

const previousPage = () => {
  emit('previousPage', props.pagination.number - 1)
}

const nextPage = () => {
  emit('nextPage', props.pagination.number + 1)
}

const goToPage = (page: number) => {
  emit('goToPage', page - 1)
}
</script>

<template>
  <nav class="mx-auto">
    <ul class="flex items-center gap-1 text-sm">
      <li>
        <button
          @click="previousPage"
          :disabled="pagination.number === 0"
          class="px-3 h-9 rounded-md bg-white border hover:cursor-pointer border-gray-200 font-medium hover:bg-gray-50 disabled:opacity-40 disabled:cursor-not-allowed"
        >
          Previous
        </button>
      </li>
      <li v-for="page in visiblePages" :key="page">
        <button
          @click="goToPage(page)"
          :class="
            page === pagination.number + 1
              ? 'bg-primary-500 text-white border-primary-500'
              : 'bg-white border-gray-200 hover:bg-gray-50'
          "
          class="w-9 h-9 rounded-md border font-medium hover:cursor-pointer"
        >
          {{ page }}
        </button>
      </li>

      <li>
        <button
          @click="nextPage"
          :disabled="pagination.last"
          class="px-3 h-9 rounded-md bg-white border hover:cursor-pointer border-gray-200 font-medium hover:bg-gray-50 disabled:opacity-40 disabled:cursor-not-allowed"
        >
          Next
        </button>
      </li>
    </ul>
  </nav>
</template>
