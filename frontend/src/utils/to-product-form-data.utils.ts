import type { ProductRequest } from '@/types/product-request'

export const toProductFormData = (request: ProductRequest): FormData => {
  const formData = new FormData()
  formData.append('brand', request.brand)
  formData.append('name', request.name)
  formData.append('price', request.price.toString())
  formData.append('stock', request.stock.toString())
  formData.append('categoryId', request.categoryId.toString())

  // Optional fields
  if (request.image) formData.append('image', request.image)
  if (request.description) formData.append('description', request.description)

  return formData
}
