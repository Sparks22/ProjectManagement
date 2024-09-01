import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('@/views/login/index.vue'),
      children: [
          {
            path: '',
            name: 'loginComponents',
            component: () => import('@/components/login/Login.vue')
          }
      ]
    },
    {
      path: '/index',
      name: 'index',
      component: () => import('@/views/index.vue'),
    }
  ]
})

export default router
