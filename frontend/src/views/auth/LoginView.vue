<script setup lang="ts">
import LoginForm from '@/components/auth/LoginForm.vue'
</script>

<template>
  <section class="px-4 py-24">
    <LoginForm />

    <!-- Demo Credentials -->
    <div
      class="p-4 mt-4 text-sm border border-primary-500 rounded-md bg-primary-100 max-w-lg mx-auto"
    >
      <p class="mb-2 font-semibold text-primary-700">Test Credentials</p>

      <div class="mb-3">
        <p class="font-medium">Demo Read-Only Admin</p>
        <p class="text-gray-600">
          Email: <span class="font-mono">demo@mobiletech.manosgrigorakis.com</span>
        </p>
        <p class="text-gray-600">Password: <span class="font-mono">!Demo123@</span></p>
      </div>
    </div>
  </section>
</template>
