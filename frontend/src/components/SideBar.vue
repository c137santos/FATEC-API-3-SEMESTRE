<template>
  <div class="sidebar">
    <!-- Logo da empresa no topo da sidebar -->
    <img @click="voltarParaHome" src="@/components/icons/logo.png" alt="Logo" class="logo"  />

    <!-- Navegação com links para outras páginas -->
    <nav>
      <ul>
        <li v-for="item in menuItems" :key="item.title">
          <SideBarButton :title="item.title" :to="item.to" />
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import '@/assets/base.css';
import SideBarButton from '@/components/SideBarButton.vue'; 

export default {
  components: {
    SideBarButton 
  },

  methods: {
    voltarParaHome(){
      this.$router.push({path: '/'});
    },
  },
  data() {
    return {
      menuItems: [
        { title: 'Tags', to: '/tags', icon: '@/components/icons/tags-icon.png' },
        { title: 'Notícias', to: '/noticias', icon: '@/components/icons/news-icon.png' },
        { title: 'APIs', to: '/apis', icon: '@/components/icons/apis-icon.png' },
        { title: 'Portais', to: '/portais', icon: '@/components/icons/portals-icon.png' },
        { title: 'Regionalismo', to: '/regionalismo', icon: '@/components/icons/portals-icon.png' }
      ]
    };
  }
};
</script>