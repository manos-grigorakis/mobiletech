import type { PaymentProvider } from './payment-provider'

export interface PaymentRequest {
  orderId: number
  paymentProvider: PaymentProvider
}
