<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import MainButton from '../ui/MainButton.vue'
import MainInput from '../ui/MainInput.vue'
import z from 'zod'
import { useForm } from 'vee-validate'
import { useAuthStore } from '@/stores/auth'
import type { LoginRequest } from '@/types/login-request'
import router from '@/router'

const auth = useAuthStore()

const formSchema = toTypedSchema(
  z.object({
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
  const payload: LoginRequest = {
    email: data.email,
    password: data.password,
  }

  await auth.login(payload)
  if (auth.isAuthenticated) await router.push(auth.dashboardRoute)
})
</script>

<template>
  <div class="max-w-md mx-auto bg-white p-8 rounded-md shadow-sm">
    <h2 class="text-lg font-semibold text-center mb-6">Login</h2>
    <form @submit="onSubmit">
      <MainInput
        label-value="Email"
        input-id="email"
        input-type="email"
        placeholder="e.g. john.doe@gmail.com"
      />

      <MainInput
        label-value="Password"
        input-id="password"
        input-type="password"
        class="mt-4 mb-6"
      />
      <MainButton :title="isSubmitting ? 'Processing...' : 'Login'" :disabled="isSubmitting" />
    </form>
  </div>
</template>
