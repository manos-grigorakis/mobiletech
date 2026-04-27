<script setup lang="ts">
import SectionWrapper from '../ui/SectionWrapper.vue'
import { Truck, ShieldCheck, BadgeCheck, type LucideIcon } from '@lucide/vue'
const features: { title: string; icon: LucideIcon }[] = [
  {
    title: 'Fast Delivery',
    icon: Truck,
  },
  {
    title: 'Secure Payments',
    icon: ShieldCheck,
  },
  {
    title: 'Warranty',
    icon: BadgeCheck,
  },
]
</script>

<template>
  <SectionWrapper title="Why Choose Us">
    <div class="flex flex-col-reverse items-center justify-between gap-8 md:flex-row">
      <!-- Image -->
      <div class="flex-1">
        <img
          src="@/assets/home/woman-shopping.webp"
          alt="Woman shopping online"
          width="600"
          height="400"
          loading="lazy"
          class="w-full rounded-sm shadow-sm drop-shadow-sm"
        />
      </div>

      <!-- Content -->

      <div class="flex-1">
        <h3 class="mb-2 text-lg font-medium">Who We Are</h3>
        <p class="leading-loose text-gray-600">
          MobileTech brings together the latest smartphones and essential accessories in one place.
          With a focus on quality, fair pricing, and fast delivery, we aim to make your shopping
          experience simple, reliable, and hassle-free.
        </p>

        <ul class="mt-6 space-y-3">
          <li v-for="feature in features" :key="feature.title" class="flex items-center gap-3">
            <component :is="feature.icon" class="w-6 h-6 text-primary-500" />
            <span class="font-medium text-gray-600">{{ feature.title }}</span>
          </li>
        </ul>
      </div>
    </div>
  </SectionWrapper>
</template>
