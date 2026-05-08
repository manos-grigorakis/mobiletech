import type { OrderItems } from './order-items'

export interface OrderRequest {
  firstName: string
  lastName: string
  email: string
  phone: string
  address: string
  city: string
  postalCode: string
  country: string
  orderItems: OrderItems[]
}
