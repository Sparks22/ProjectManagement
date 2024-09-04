import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('@/views/beforeLogin/index.vue'),
      children: [
          {
            path: '',
            name: 'loginComponents',
            component: () => import('@/components/BeforeLogin/login.vue')
          },
        {
          path: '/register',
          name: 'registerComponents',
          component: () => import('@/components/BeforeLogin/register.vue')
        }
      ]
    },
    {
      path: '/index',
      name: 'index',
      component: () => import('@/views/index.vue'),
      children: [
        {
          path: '',
          name: 'MenuComponents',
          component: () => import('@/components/Menu/menu.vue')
        }
      ]
    }
  ]
})

export default router
