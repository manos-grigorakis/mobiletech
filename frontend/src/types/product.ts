import type { Category } from './category'

export interface Product {
  id: number
  brand: string
  name: string
  price: number
  stock: number
  category: Category
  description: string
  imageUrl: string
}
