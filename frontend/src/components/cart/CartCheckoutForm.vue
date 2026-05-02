<script setup lang="ts">
import z from 'zod'
import MainButton from '../ui/MainButton.vue'
import MainInput from '../ui/MainInput.vue'
import { countries } from '@/data/countries'
import { toTypedSchema } from '@vee-validate/zod'
import { useField, useForm } from 'vee-validate'
import router from '@/router'
import { useCartStore } from '@/stores/cart'

const cart = useCartStore()

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
    paymentMethod: z.enum(['cash', 'stripe']).default('cash'),
  }),
)

const { handleSubmit } = useForm({
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
    paymentMethod: 'cash',
  },
})

const { value: countryValue, errorMessage: countryError } = useField('country')
const { value: paymentMethodValue } = useField('paymentMethod')

const onSubmit = handleSubmit((data) => {
  cart.clearCart()
  router.push({ name: 'order-success' })
})
</script>

<template>
  <div class="max-w-4xl p-6 mx-auto bg-white rounded-md shadow-sm">
    <form @submit="onSubmit" class="flex flex-col gap-4">
      <!-- Names -->
      <div class="flex gap-2">
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
        placeholder="e.g. john.doe@example.com"
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
        <div class="flex gap-2">
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

        <div class="flex gap-2 mt-4">
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

      <!-- Payment -->
      <div>
        <p class="block mb-1"><span class="mr-1 text-xs text-red-500">*</span>Payment</p>

        <!-- Cash on delivery -->
        <div>
          <input
            type="radio"
            id="cash-on-delivery"
            v-model="paymentMethodValue"
            value="cash"
            class="mr-1"
            checked
          />
          <label for="cash-on-delivery">Cash on delivery</label>
        </div>

        <!-- Credit card -->
        <div>
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
      <MainButton title="Complete Order" />
    </form>
  </div>
</template>
