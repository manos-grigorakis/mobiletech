export interface OrderPaymentTransaction {
  id: number
  paymentProvider: string
  paymentStatus: string
  grossAmount: number
  providerFeeAmount: number
  netAmount: number
}
