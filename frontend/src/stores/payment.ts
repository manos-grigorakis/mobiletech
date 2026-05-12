import router from '@/router'
import { PaymentProvider } from '@/types/payment-provider'
import type { PaymentRequest } from '@/types/payment-request'
import { defineStore } from 'pinia'
import { useUiStore } from './ui'
import api from '@/api/api'

export const usePaymentStore = defineStore('payment', {
  state: () => ({
    paymentProvider: null as PaymentProvider | null,
    isLoading: false as boolean,
    stripeClientKey: null as string | null,
    hasError: false as boolean,
  }),

  persist: {
    pick: ['paymentProvider'],
  },

  actions: {
    async createPayment(payload: PaymentRequest) {
      this.isLoading = true
      this.hasError = false

      try {
        const res = await api.post('/payments/create', payload)
        this.paymentProvider = payload.paymentProvider

        switch (this.paymentProvider) {
          case PaymentProvider.CASH_ON_DELIVERY:
            router.push({ name: 'checkout-success' })
            break
          case PaymentProvider.PAYPAL:
            window.location.href = res.data.data.approveUrl
            break
          case PaymentProvider.STRIPE:
            this.stripeClientKey = res.data.data.clientSecret
            break
        }
      } catch (e) {
        const ui = useUiStore()
        ui.setError('Failed to create payment. Please try again')
        this.hasError = true
      } finally {
        this.isLoading = false
      }
    },
  },
})
