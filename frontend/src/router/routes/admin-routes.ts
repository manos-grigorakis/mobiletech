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
        name: 'admin-dashboard',
        component: () => import('@/views/admin/AdminDashboardView.vue'),
      },
      {
        path: 'categories',
        name: 'admin-categories',
        component: () => import('@/views/admin/AdminCategoriesView.vue'),
      },
      {
        path: 'categories/create',
        name: 'admin-create-category',
        component: () => import('@/views/admin/AdminCreateCategoryView.vue'),
      },

      // Products
      {
        path: 'products',
        name: 'admin-products',
        component: () => import('@/views/admin/AdminProductsView.vue'),
      },
      {
        path: 'products/create',
        name: 'admin-create-product',
        component: () => import('@/views/admin/AdminCreateProductView.vue'),
      },
      {
        path: 'products/:id/edit',
        name: 'admin-edit-product',
        component: () => import('@/views/admin/AdminEditProductFormView.vue'),
      },
      {
        path: 'orders',
        name: 'admin-orders',
        component: () => import('@/views/admin/AdminOrdersView.vue'),
      },
      {
        path: 'orders/:id',
        name: 'admin-view-order',
        component: () => import('@/views/admin/AdminOrderDetailsView.vue'),
      },

      // Orders

      // Not Found
      {
        path: ':pathMatch(.*)*',
        name: 'admin-not-found',
        component: () => import('@/views/NotFoundView.vue'),
      },
    ],
  },
]
