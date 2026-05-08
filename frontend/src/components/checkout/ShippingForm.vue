<script setup lang="ts">
import z from 'zod'
import MainButton from '../ui/MainButton.vue'
import MainInput from '../ui/MainInput.vue'
import { countries } from '@/data/countries'
import { toTypedSchema } from '@vee-validate/zod'
import { useField, useForm } from 'vee-validate'
import router from '@/router'
import { useCartStore } from '@/stores/cart'
import { useOrderStore } from '@/stores/order'
import type { OrderRequest } from '@/types/order-request'

const cart = useCartStore()
const order = useOrderStore()

const formSchema = toTypedSchema(
  z.object({
    firstName: z.string().min(2, 'First name must be at least 2 characters.'),
    lastName: z.string().min(2, 'Last name must be at least 2 characters.'),
    email: z.string().email('Invalid email address'),
    phone: z
      .string()
      .min(7, 'Phone must be at least 7 characters.')
      .max(15, 'Phone at most 15 characters'),
    address: z.string().min(3, 'Address must be at least 3 characters.'),
    city: z.string().min(2, 'City must be at least 2 characters.'),
    postalCode: z
      .string()
      .min(3, 'Postal Code must be at least 3 characters.')
      .max(10, 'Postal Code at most 10 characters'),
    country: z.string().min(1, 'Country is required'),
  }),
)

const { handleSubmit, isSubmitting } = useForm({
  validationSchema: formSchema,
  initialValues: {
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    address: '',
    city: '',
    postalCode: '',
    country: '',
  },
})

const { value: countryValue, errorMessage: countryError } = useField('country')

const onSubmit = handleSubmit(async (data) => {
  const payload: OrderRequest = {
    firstName: data.firstName,
    lastName: data.lastName,
    email: data.email,
    phone: data.phone,
    address: data.address,
    city: data.city,
    postalCode: data.postalCode,
    country: data.country,
    orderItems: cart.items.map((item) => ({ productId: item.id, quantity: item.quantity })),
  }

  await order.createOrder(payload)
  await router.push({ name: 'checkout-payment' })
})
</script>

<template>
  <div class="max-w-5xl p-6 mx-auto bg-white rounded-md shadow-sm">
    <form @submit="onSubmit" class="flex flex-col gap-4">
      <!-- Names -->
      <div class="flex flex-col gap-4 md:gap-2 md:flex-row">
        <MainInput
          label-value="First Name"
          input-id="firstName"
          class="flex-1"
          :required="true"
          placeholder="e.g. John"
        />
        <MainInput
          label-value="Last Name"
          input-id="lastName"
          class="flex-1"
          :required="true"
          placeholder="e.g. Doe"
        />
      </div>

      <!-- Email -->
      <MainInput
        label-value="Email"
        input-id="email"
        input-type="email"
        :required="true"
        placeholder="e.g. john.doe@gmail.com"
      />

      <!-- Phone -->
      <MainInput
        label-value="Phone"
        input-id="phone"
        :required="true"
        placeholder="e.g. +30 210 1234567"
      />

      <!-- Shipping -->
      <div class="mt-4">
        <div class="flex flex-col gap-4 md:flex-row md:gap-2">
          <!-- Address -->
          <MainInput
            label-value="Street Address"
            input-id="address"
            class="flex-1"
            :required="true"
            placeholder="e.g. Ermou 10"
          />

          <!-- City -->
          <MainInput
            label-value="Town / City"
            input-id="city"
            class="flex-1"
            :required="true"
            placeholder="e.g. Athens"
          />
        </div>

        <div class="flex flex-col gap-4 mt-4 md:gap-2 md:flex-row">
          <!-- Postal Code -->
          <MainInput
            label-value="Postal Code / ZIP"
            input-id="postalCode"
            class="flex-1"
            :required="true"
            placeholder="e.g. 10563"
          />

          <!-- Country -->
          <div class="flex-1">
            <label for="country" class="block mb-1"
              ><span class="mr-1 text-xs text-red-500">*</span>Country</label
            >
            <select
              v-model="countryValue"
              id="country"
              class="bg-neutral-100 border border-gray-200 text-sm rounded w-full px-3 py-2.5 shadow-xs focus:outline-none capitalize focus:border-primary-500"
            >
              <option value="" selected disabled>Select country</option>
              <option v-for="c in countries" :key="c" :value="c">
                {{ c }}
              </option>
            </select>

            <span v-if="countryError" class="block mt-1 text-xs text-red-500">{{
              countryError
            }}</span>
          </div>
        </div>
      </div>

      <MainButton
        :disabled="isSubmitting"
        :title="isSubmitting ? 'Processing...' : 'Continue to Payment'"
      />
    </form>
  </div>
</template>
