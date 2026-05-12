import { useAuthStore } from '@/stores/auth'
import router from '..'

export const adminRoutes = [
  // Admin
  {
    path: '/admin',
    component: () => import('@/layout/AdminLayout.vue'),
    beforeEnter: () => {
      const auth = useAuthStore()
      if (!auth.isAuthenticated || !auth.isAdminOrManager) return router.push({ name: 'not-found' })
    },
    children: [
      {
        path: '',
        name: 'admin',
        component: () => import('@/views/admin/AdminDashboardView.vue'),
      },
      {
        path: 'categories',
        name: 'admin-categories',
        component: () => import('@/views/admin/AdminCategoriesView.vue'),
      },
      {
        path: 'products',
        name: 'admin-products',
        component: () => import('@/views/admin/AdminProductsView.vue'),
      },
      {
        path: 'orders',
        name: 'admin-orders',
        component: () => import('@/views/admin/AdminOrdersView.vue'),
      },
      // Not Found
      {
        path: ':pathMatch(.*)*',
        name: 'admin-not-found',
        component: () => import('@/views/NotFoundView.vue'),
      },
    ],
  },
]
