<template>
  <div class="p-4 mt-4 text-sm border border-primary-500 rounded-md bg-primary-100">
    <p class="mb-2 font-semibold text-primary-700">Test Credentials</p>

    <!-- PayPal -->
    <div class="mb-3">
      <p class="font-medium">PayPal</p>
      <p class="text-gray-600">Email: <span class="font-mono">john.doe.buyer@sandbox.com</span></p>
      <p class="text-gray-600">Password: <span class="font-mono">kDPa3PoTXVOR0jZH</span></p>
    </div>

    <!-- Stripe -->
    <div class="mb-3">
      <p class="font-medium">Credit Card (Stripe)</p>
      <p class="text-gray-600">Success: <span class="font-mono">4242 4242 4242 4242</span></p>
      <p class="text-gray-600">Decline: <span class="font-mono">4000 0000 0000 0002</span></p>
      <p class="text-gray-600">Expiry/CVC: any future date / any 3 digits</p>
    </div>
  </div>
</template>
