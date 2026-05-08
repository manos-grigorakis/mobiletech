<template>
  <section class="flex flex-col items-center justify-center text-center px-20 pt-10">
    <span class="text-8xl font-bold text-accent-500 leading-none"> 404 </span>
    <h2 class="mt-3 text-2xl font-semibold text-foreground uppercase tracking-wide">
      Page not found
    </h2>
    <h3 class="mt-3 text-sm text-foreground/50 leading-relaxed">
      The page you're looking for doesn't exist.
    </h3>
    <RouterLink :to="{ name: 'products' }" class="mt-4 hover:underline hover:text-primary-500"
      >Explore Products</RouterLink
    >
  </section>
</template>
