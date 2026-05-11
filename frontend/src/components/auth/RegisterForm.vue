<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import { useForm } from 'vee-validate'
import z from 'zod'
import MainButton from '../ui/MainButton.vue'
import MainInput from '../ui/MainInput.vue'
import type { RegisterRequest } from '@/types/register-request'
import { useAuthStore } from '@/stores/auth'
import router from '@/router'

const auth = useAuthStore()

const formSchema = toTypedSchema(
  z.object({
    firstName: z
      .string()
      .min(2, 'First name must be at least 2 characters.')
      .max(100, 'First name too long'),
    lastName: z
      .string()
      .min(2, 'Last name must be at least 2 characters.')
      .max(100, 'Last name too long'),
    email: z.string().email('Invalid email address').max(320),
    password: z
      .string()
      .min(8, 'Password must be at least 8 characters')
      .max(100, 'Password too long'),
  }),
)

const { handleSubmit, isSubmitting } = useForm({
  validationSchema: formSchema,
})

const onSubmit = handleSubmit(async (data) => {
  const payload: RegisterRequest = {
    firstName: data.firstName,
    lastName: data.lastName,
    email: data.email,
    password: data.password,
  }

  const success = await auth.register(payload)
  if (success) await router.push({ name: 'login' })
})
</script>

<template>
  <div class="max-w-lg mx-auto bg-white p-8 rounded-md shadow-sm">
    <h2 class="text-lg font-semibold text-center mb-6">Register</h2>
    <form @submit="onSubmit" class="flex flex-col gap-4">
      <div class="flex flex-col sm:flex-row gap-4">
        <MainInput
          label-value="First Name"
          input-id="firstName"
          input-type="text"
          placeholder="e.g. John"
          class="flex-1"
        />

        <MainInput
          label-value="Last Name"
          input-id="lastName"
          input-type="text"
          placeholder="e.g. Doe"
          class="flex-1"
        />
      </div>

      <MainInput
        label-value="Email"
        input-id="email"
        input-type="email"
        placeholder="e.g. john.doe@gmail.com"
      />

      <MainInput label-value="Password" input-id="password" input-type="password" />

      <MainButton
        :title="isSubmitting ? 'Processing...' : 'Register'"
        :disabled="isSubmitting"
        class="mt-4"
      />
    </form>

    <p class="text-sm text-center mt-4">
      Already have an
      <RouterLink :to="{ name: 'login' }" class="underline hover:text-primary-600"
        >account?</RouterLink
      >
    </p>
  </div>
</template>
