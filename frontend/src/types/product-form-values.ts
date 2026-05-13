export interface ProductFormValues {
  brand: string
  name: string
  price: number
  stock: number
  category: number | null
  description?: string
  image?: File
}
