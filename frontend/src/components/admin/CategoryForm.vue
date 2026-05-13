<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import z from 'zod'
import MainInput from '../ui/MainInput.vue'
import { useForm } from 'vee-validate'
import { useCategoryStore } from '@/stores/category'
import type { CategoryRequest } from '@/types/category-request'
import MainButton from '../ui/MainButton.vue'
import router from '@/router'

const categoryStore = useCategoryStore()
const formSchema = toTypedSchema(
  z.object({
    name: z.string().min(1, 'Name is required').max(50, 'Name must be at most 50 characters'),
  }),
)

const { handleSubmit, isSubmitting } = useForm({
  validationSchema: formSchema,
})

const onSubmit = handleSubmit(async (data) => {
  const payload: CategoryRequest = {
    name: data.name,
    slug: createSlug(data.name),
  }

  await categoryStore.createCategory(payload)
  if (!categoryStore.isLoading) await router.push({ name: 'admin-categories' })
})

const createSlug = (name: string) => {
  return name.replace(' ', '-').toLocaleLowerCase()
}
</script>

<template>
  <div class="bg-white shadow-sm px-4 py-6 rounded max-w-lg">
    <form @submit="onSubmit" class="flex flex-col gap-4">
      <h3 class="text-2xl font-medium mb-6">Create Category</h3>

      <MainInput :required="true" label-value="Name" input-id="name" placeholder="e.g. Cases" />

      <MainButton
        :title="isSubmitting ? 'Processing...' : 'Create Category'"
        :disabled="isSubmitting || categoryStore.isLoading"
      />
    </form>
  </div>
</template>
