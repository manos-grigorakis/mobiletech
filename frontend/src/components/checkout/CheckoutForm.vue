<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import { useField, useForm } from 'vee-validate'
import z from 'zod'
import MainButton from '../ui/MainButton.vue'
import { useOrderStore } from '@/stores/order'
import { usePaymentStore } from '@/stores/payment'
import { PaymentProvider } from '@/types/payment-provider'
import type { PaymentRequest } from '@/types/payment-request'
import router from '@/router'

const order = useOrderStore()
const payment = usePaymentStore()

const paymentProviderValues = Object.values(PaymentProvider) as [
  PaymentProvider,
  ...PaymentProvider[],
]
const formSchema = toTypedSchema(
  z.object({
    paymentMethod: z.enum(paymentProviderValues).default(PaymentProvider.CASH_ON_DELIVERY),
  }),
)

const { handleSubmit, isSubmitting } = useForm({
  validationSchema: formSchema,
})

const { value: paymentMethodValue } = useField('paymentMethod')

const onSubmit = handleSubmit(async (data) => {
  if (order.orderId === null) {
    router.push({ name: 'checkout-shipping' })
    return
  }

  const payload: PaymentRequest = {
    orderId: order.orderId,
    paymentProvider: data.paymentMethod,
  }

  await payment.createPayment(payload)
})
</script>

<template>
  <div class="max-w-5xl p-6 mx-auto bg-white rounded-md shadow-sm">
    <form @submit="onSubmit" class="flex flex-col gap-4">
      <!-- Payment -->
      <div>
        <p class="block mb-1"><span class="mr-1 text-xs text-red-500">*</span>Payment</p>

        <!-- Cash on delivery -->
        <div>
          <input
            type="radio"
            id="cash-on-delivery"
            v-model="paymentMethodValue"
            value="cash_on_delivery"
            class="mr-1"
            checked
          />
          <label for="cash-on-delivery">Cash on delivery</label>
        </div>

        <!-- PayPal -->
        <div>
          <input
            type="radio"
            id="paypal"
            v-model="paymentMethodValue"
            value="paypal"
            class="mr-1"
          />
          <label for="paypal">PayPal</label>
        </div>

        <!-- Credit card -->
        <div class="my-1">
          <input
            type="radio"
            id="stripe"
            v-model="paymentMethodValue"
            value="stripe"
            class="mr-1"
            disabled
          />
          <label for="stripe" class="text-gray-400 line-through cursor-not-allowed"
            >Credit Card</label
          >
        </div>
      </div>

      <MainButton
        :disabled="isSubmitting"
        :title="isSubmitting ? 'Processing...' : 'Complete Order'"
      />
    </form>
  </div>
</template>
