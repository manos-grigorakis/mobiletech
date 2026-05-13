import { useOrderStore } from '@/stores/order'
import router from '..'
import { usePaymentStore } from '@/stores/payment'
import HomeView from '@/views/HomeView.vue'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'

export const publicRoutes = [
  {
    path: '/',
    component: () => import('@/layout/DefaultLayout.vue'),
    children: [
      { path: '', name: 'home', component: HomeView },
      {
        path: '/accessories',
        name: 'accessories',
        component: () => import('@/views/AccessoriesView.vue'),
      },
      // Products
      {
        path: '/products',
        name: 'products',
        component: () => import('@/views/ProductsView.vue'),
      },
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
            beforeEnter: () => {
              const cart = useCartStore()
              if (cart.items.length === 0) return { name: 'cart' }
            },
          },
          {
            path: 'payment',
            name: 'checkout-payment',
            component: () => import('@/components/checkout/CheckoutForm.vue'),
            beforeEnter: () => {
              const order = useOrderStore()
              if (!order.orderId) return { name: 'checkout-shipping' }
            },
          },
          {
            path: 'success',
            name: 'checkout-success',
            component: () => import('@/components/checkout/OrderSuccess.vue'),
            beforeEnter: () => {
              const order = useOrderStore()
              const payment = usePaymentStore()
              if (!order.orderId || !payment.paymentProvider) return { name: 'cart' }
            },
          },
        ],
      },
      {
        path: '/account',
        name: 'account',
        component: () => import('@/views/AccountView.vue'),
        beforeEnter: () => {
          const auth = useAuthStore()
          if (!auth.isAuthenticated) return router.push({ name: 'not-found' })
        },
      },

      // Authentication
      { path: '/login', name: 'login', component: () => import('@/views/auth/LoginView.vue') },
      {
        path: '/register',
        name: 'register',
        component: () => import('@/views/auth/RegisterView.vue'),
      },

      // Not Found
      {
        path: ':pathMatch(.*)*',
        name: 'not-found',
        component: () => import('@/views/NotFoundView.vue'),
      },
    ],
  },
]
