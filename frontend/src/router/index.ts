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
    {
      path: '/cart',
      name: 'cart',
      component: () => import('@/views/CartView.vue'),
      children: [
        {
          path: '',
          name: 'cart-list',
          component: () => import('@/components/cart/CartList.vue'),
        },
        {
          path: 'checkout',
          name: 'cart-checkout',
          component: () => import('@/components/cart/CartCheckoutForm.vue'),
          beforeEnter: () => {
            const cart = useCartStore()
            if (cart.totalItems === 0) return { name: 'cart-list' }
          },
        },
        {
          path: 'success',
          name: 'order-success',
          component: () => import('@/components/cart/OrderSuccess.vue'),
          beforeEnter: () => {
            const cart = useCartStore()
            if (!cart.orderPlaced) return { name: 'cart-list' }
          },
        },
      ],
    },
  ],
})

export default router
