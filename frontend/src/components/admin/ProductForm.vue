<script setup lang="ts">
import { useCategoryStore } from '@/stores/category'
import MainInput from '../ui/MainInput.vue'
import { onMounted } from 'vue'
import MainButton from '../ui/MainButton.vue'
import { toTypedSchema } from '@vee-validate/zod'
import z from 'zod'
import { useField, useForm } from 'vee-validate'
import type { ProductRequest } from '@/types/product-request'
import { useProductStore } from '@/stores/product'
import router from '@/router'

const categoryStore = useCategoryStore()
const productStore = useProductStore()

const formSchema = toTypedSchema(
  z.object({
    brand: z.string().min(1, 'Brand is required').max(50, 'Brand must be at most 50 characters'),
    name: z.string().min(1, 'Name is required').max(150, 'Name must be at most 150 characters'),
    price: z.coerce.number().positive('Price must be greater than 0'),
    stock: z.coerce
      .number({ invalid_type_error: 'Stock is required' })
      .int('Stock must be a whole number')
      .nonnegative('Stock cannot be negative'),
    category: z
      .number({ invalid_type_error: 'Category is required' })
      .nullable()
      .refine((val) => val !== null, { message: 'Category is required' }),
    description: z.string().max(1000, 'Description must be at most 1000 characters').optional(),
    image: z.instanceof(File, { message: 'Image is required' }),
  }),
)

const { handleSubmit, isSubmitting, setFieldValue } = useForm({
  validationSchema: formSchema,
  initialValues: {
    category: null as number | null,
  },
})

const { value: categoryValue, errorMessage: categoryError } = useField('category')
const { errorMessage: imageError } = useField('image')

const onSubmit = handleSubmit(async (data) => {
  const payload: ProductRequest = {
    brand: data.brand,
    name: data.name,
    price: data.price,
    stock: data.stock,
    description: data.description ? data.description : null,
    image: data.image,
    categoryId: data.category,
  }

  await productStore.createProduct(payload)
  if (productStore.hasError) return
  await router.push({ name: 'admin-products' })
})

onMounted(() => {
  categoryStore.fetchCategories()
})
</script>

<template>
  <div class="bg-white shadow-sm px-4 py-6 rounded max-w-6xl">
    <h3 class="text-2xl font-medium mb-6">Create a Product</h3>

    <form @submit="onSubmit" class="flex flex-col gap-4">
      <div class="flex flex-col sm:flex-row gap-4">
        <MainInput
          :required="true"
          label-value="Brand"
          input-id="brand"
          placeholder="e.g. Apple"
          class="flex-1"
        />
        <MainInput
          :required="true"
          label-value="Name"
          input-id="name"
          placeholder="e.g. iPhone 15 128GB Black"
          class="flex-1"
        />
      </div>

      <div class="flex flex-col sm:flex-row gap-4">
        <MainInput
          :required="true"
          label-value="Price"
          input-id="price"
          placeholder="e.g. 800.00"
          class="flex-1"
        />

        <MainInput
          :required="true"
          label-value="Stock"
          input-id="stock"
          placeholder="e.g. 1000"
          class="flex-1"
        />
      </div>

      <!-- Category -->
      <div>
        <label for="category" class="block mb-1"
          ><span class="mr-1 text-xs text-red-500">*</span>Category</label
        >
        <select
          v-model="categoryValue"
          id="category"
          class="bg-neutral-100 border border-gray-200 text-sm rounded w-full px-3 py-2.5 shadow-xs focus:outline-none focus:border-primary-500"
        >
          <option :value="null" selected disabled>Select category</option>
          <option v-for="c in categoryStore.categories" :key="c.id" :value="c.id">
            {{ c.name }}
          </option>
        </select>

        <span v-if="categoryError" class="block mt-1 text-xs text-red-500">{{
          categoryError
        }}</span>
      </div>

      <!-- Description -->
      <div>
        <label for="description" class="block mb-1">Description</label>
        <textarea
          name=""
          id="description"
          rows="5"
          placeholder="e.g. Apple iPhone 15 128GB with A16 Bionic and OLED display"
          class="bg-neutral-100 border border-gray-200 text-sm rounded w-full px-3 py-2.5 shadow-xs resize-none focus:outline-none focus:border-primary-500"
        ></textarea>
      </div>

      <!-- Image upload -->
      <div>
        <label class="block mb-1" for="image"
          ><span class="mr-1 text-xs text-red-500">*</span>Upload Image</label
        >
        <input
          @change="(e) => setFieldValue('image', (e.target as HTMLInputElement).files?.[0])"
          class="bg-neutral-100 border border-gray-200 text-sm rounded w-full px-3 py-2.5 shadow-xs focus:outline-none focus:border-primary-500"
          id="image"
          type="file"
          accept=".webp,image/webp"
        />
        <span class="text-gray-400 text-xs">Supported file types: .webp</span>
        <span v-if="imageError" class="block mt-1 text-xs text-red-500">{{ imageError }}</span>
      </div>

      <MainButton :title="isSubmitting ? 'Processing...' : 'Create'" :disabled="isSubmitting" />
    </form>
  </div>
</template>
