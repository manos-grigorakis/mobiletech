export interface ProductRequest {
  brand: string
  name: string
  price: number
  stock: number
  description: string | null
  image: File
  categoryId: number
}
