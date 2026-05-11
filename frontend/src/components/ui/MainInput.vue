<script setup lang="ts">
import { useField } from 'vee-validate'

interface Props {
  required?: boolean
  labelValue: string
  inputId: string
  inputType?: 'text' | 'email' | 'password'
  placeholder?: string
}

const props = withDefaults(defineProps<Props>(), {
  required: false,
  inputType: 'text',
})

const { value, errorMessage } = useField(() => props.inputId)
</script>

<template>
  <div>
    <label :for="inputId" class="block mb-1"
      ><span v-if="required" class="mr-1 text-xs text-red-500">*</span>{{ labelValue }}</label
    >
    <input
      v-model="value"
      :type="inputType"
      :id="inputId"
      :placeholder="placeholder"
      class="bg-neutral-100 border border-gray-200 text-sm rounded w-full px-3 py-2.5 shadow-xs focus:outline-none focus:border-primary-500"
    />

    <span v-if="errorMessage" class="block mt-1 text-xs text-red-500">{{ errorMessage }}</span>
  </div>
</template>
