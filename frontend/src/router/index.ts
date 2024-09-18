import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue'; 
import TagsView from '@/views/TagsView.vue'; 
import PortaisView from '@/views/PortaisView.vue';
import ApisView from '@/views/ApisView.vue';

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
      component: TagsView 
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
    }
  ]
});

export default router;
