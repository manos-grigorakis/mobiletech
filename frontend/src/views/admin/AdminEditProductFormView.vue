<script setup lang="ts">
import ProductForm from '@/components/admin/ProductForm.vue'
import { useProductStore } from '@/stores/product'
import { type ProductFormValues } from '@/types/product-form-values'
import { computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const productStore = useProductStore()
const route = useRoute()

const initialValues = computed<ProductFormValues | undefined>(() => {
  if (!productStore.product) return undefined

  return {
    brand: productStore.product.brand,
    name: productStore.product.name,
    price: productStore.product.price,
    stock: productStore.product.stock,
    category: productStore.product.category.id,
    description: productStore.product.description,
  }
})

onMounted(() => {
  const id = parseInt(route.params.id as string)
  productStore.fetchProduct(id)
})
</script>

<template>
  <ProductForm v-if="productStore.product" is-edit :initial-values="initialValues" />
</template>
