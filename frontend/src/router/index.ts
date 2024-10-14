import ApisView from '@/views/ApisView.vue';
import HomeView from '@/views/HomeView.vue';
import PortaisView from '@/views/PortaisView.vue';
import RegionalismoView from '@/views/RegionalismoView.vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/noticias' 
    },
    {
      path: '/noticias',
      name: 'noticias',
      component: HomeView 
    },
    {
      path: '/tags',
      name: 'tags',
      component: () => import('../views/TagView.vue')
    },
    {
      path: '/portais',
      name: 'portais',
      component: PortaisView 
    },
    {
      path: '/apis',
      name: 'apis',
      component: ApisView 
    },
    {
      path: '/regionalismo',
      component: RegionalismoView
    }
  ]
});

export default router;
