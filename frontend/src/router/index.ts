import { useCartStore } from '@/stores/cart'
import HomeView from '@/views/HomeView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    {
      path: '/accessories',
      name: 'accessories',
      component: () => import('@/views/AccessoriesView.vue'),
    },

    // Products
    { path: '/products', name: 'products', component: () => import('@/views/ProductsView.vue') },
    {
      path: '/products/:id',
      name: 'product-details',
      component: () => import('@/views/ProductDetailsView.vue'),
    },

    // Cart
    { path: '/cart', name: 'cart', component: () => import('@/views/CartView.vue') },

    // Checkout
    {
      path: '/checkout',
      name: 'checkout',
      component: () => import('@/views/CheckoutView.vue'),
      children: [
        {
          path: '',
          name: 'checkout-shipping',
          component: () => import('@/components/checkout/ShippingForm.vue'),
        },
        // {
        //   path: 'payment',
        //   name: 'checkout-payment',
        //   component: () => import('@/components/checkout/CheckoutForm.vue'),
        // },
        {
          path: 'success',
          name: 'checkout-success',
          component: () => import('@/components/checkout/OrderSuccess.vue'),
        },
      ],
    },
  ],
})

export default router
