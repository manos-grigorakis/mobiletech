<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import { useField, useForm } from 'vee-validate'
import { useOrderStore } from '@/stores/order'
import { usePaymentStore } from '@/stores/payment'
import { PaymentProvider } from '@/types/payment-provider'
import type { PaymentRequest } from '@/types/payment-request'
import { computed, onMounted, ref } from 'vue'
import router from '@/router'
import StripeForm from '../ui/stripe/StripeForm.vue'
import z from 'zod'
import MainButton from '../ui/MainButton.vue'

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

const { handleSubmit } = useForm({
  validationSchema: formSchema,
})

const stripeButtonTitle = computed(() => {
  if (paymentMethodValue.value === PaymentProvider.STRIPE && !payment.stripeClientKey) {
    return 'Continue'
  }
  return 'Complete Order'
})

const { value: paymentMethodValue } = useField<PaymentProvider>('paymentMethod')
const stripeFormRef = ref<InstanceType<typeof StripeForm> | null>(null)
const isProcessing = ref<boolean>(false)

const onSubmit = handleSubmit(async (data) => {
  isProcessing.value = true

  try {
    if (order.orderId === null) {
      router.push({ name: 'checkout-shipping' })
      return
    }

    const payload: PaymentRequest = {
      orderId: order.orderId,
      paymentProvider: data.paymentMethod,
    }

    if (data.paymentMethod === PaymentProvider.STRIPE) {
      if (!payment.stripeClientKey) {
        // Create payment to show Stripe form
        await payment.createPayment(payload)
        isProcessing.value = false
      } else {
        await stripeFormRef.value?.confirm(payment.stripeClientKey!)
      }
    } else {
      await payment.createPayment(payload)
      if (payment.hasError) isProcessing.value = false
    }
  } catch {
    isProcessing.value = false
  }
})

onMounted(() => {
  payment.stripeClientKey = null
})
</script>

<template>
  <div class="max-w-5xl p-6 mx-auto bg-white rounded-md shadow-sm">
    <form @submit="onSubmit" class="flex flex-col gap-4">
      <!-- Payment -->
      <div>
        <p class="mb-2"><span class="mr-1 text-xs text-red-500">*</span>Payment</p>

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
        <div class="my-1">
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
        <div>
          <input
            type="radio"
            id="stripe"
            v-model="paymentMethodValue"
            value="stripe"
            class="mr-1"
          />
          <label for="stripe">Credit Card</label>
        </div>
      </div>

      <!-- Stripe form -->
      <div v-if="paymentMethodValue === PaymentProvider.STRIPE">
        <StripeForm
          v-if="payment.stripeClientKey"
          ref="stripeFormRef"
          :client-secret="payment.stripeClientKey"
          @error="isProcessing = false"
        />
        <p v-else class="text-sm text-gray-400">Select Credit Card and click continue to proceed</p>
      </div>

      <MainButton
        :disabled="isProcessing || payment.isLoading"
        :title="isProcessing || payment.isLoading ? 'Processing...' : stripeButtonTitle"
      />
    </form>
  </div>
</template>
