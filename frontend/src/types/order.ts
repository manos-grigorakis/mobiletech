import type { OrderItemResponse } from './order-item-response'
import type { OrderPaymentTransaction } from './order-payment-transaction'

export interface Order {
  id: number
  orderStatus: string
  firstName: string
  lastName: string
  email: string
  phone: string
  address: string
  city: string
  postalCode: string
  country: string
  totalAmount: number
  createdAt: string
  updatedAt: string | null
  orderItems: OrderItemResponse[]
  paymentTransactions: OrderPaymentTransaction[]
}
