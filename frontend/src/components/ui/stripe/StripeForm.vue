<script setup lang="ts">
import { VueStripeElements, VueStripeProvider } from '@vue-stripe/vue-stripe'
import StripeInner from './StripeInner.vue'
import { ref } from 'vue'

defineProps<{ clientSecret: string }>()

const publishableKey = import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY
const innerRef = ref<InstanceType<typeof StripeInner> | null>(null)

const elementOptions = {
  terms: { card: 'never' },
  wallets: { applePay: 'never', googlePay: 'never' },
  fields: {
    billingDetails: 'never',
  },
}

const emit = defineEmits<{ error: [] }>()
const confirm = async (clientSecret: string) => innerRef.value?.confirm(clientSecret)

defineExpose({ confirm, emit })
</script>

<template>
  <VueStripeProvider :publishable-key="publishableKey">
    <VueStripeElements :client-secret="clientSecret" :options="elementOptions">
      <StripeInner ref="innerRef" @error="emit('error')" />
    </VueStripeElements>
  </VueStripeProvider>
</template>
