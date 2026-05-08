<script setup lang="ts">
import router from '@/router'
import { usePaymentIntent, VueStripePaymentElement } from '@vue-stripe/vue-stripe'

const { confirmPayment } = usePaymentIntent()

const confirm = async (clientSecret: string) => {
  const result = await confirmPayment({
    clientSecret,
    redirect: 'if_required',
    confirmParams: {
      return_url: `${window.location.origin}/checkout/success`,
    },
  })

  if (result.error) {
    console.error(result.error.message)
    emit('error')
  } else if (result.paymentIntent?.status === 'succeeded') {
    router.push({ name: 'checkout-success' })
  }
  return result
}

const emit = defineEmits<{ error: [] }>()
defineExpose({ confirm })
</script>

<template>
  <VueStripePaymentElement />
</template>
