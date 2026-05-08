import router from '@/router'
import { PaymentProvider } from '@/types/payment-provider'
import type { PaymentRequest } from '@/types/payment-request'
import axios from 'axios'
import { defineStore } from 'pinia'

const API_URL = import.meta.env.VITE_API_URL

export const usePaymentStore = defineStore('payment', {
  state: () => ({
    paymentProvider: null as PaymentProvider | null,
    isLoading: false as boolean,
    stripeClientKey: null as string | null,
    error: null as string | null,
  }),

  persist: {
    pick: ['paymentProvider'],
  },

  actions: {
    async createPayment(payload: PaymentRequest) {
      this.isLoading = true

      try {
        const res = await axios.post(`${API_URL}/payments/create`, payload)
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
        console.error('Failed to create payment', e)
        this.error = 'Failed to create payment. Please try again'
      } finally {
        this.isLoading = false
      }
    },
  },
})
