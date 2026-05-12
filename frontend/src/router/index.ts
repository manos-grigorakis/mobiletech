import { createRouter, createWebHistory } from 'vue-router'
import { adminRoutes } from './routes/admin-routes'
import { publicRoutes } from './routes/public-routes'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  scrollBehavior() {
    return { top: 0 }
  },
  routes: [...publicRoutes, ...adminRoutes],
})

export default router
