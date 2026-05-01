import HomeView from '@/views/HomeView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/products', component: () => import('@/views/ProductsView.vue') },
    {
      path: '/products/:id',
      name: 'product-details',
      component: () => import('@/views/ProductDetailsView.vue'),
    },
  ],
})

export default router
