<template>
  <div class="sidebar">
    <!-- Logo da empresa no topo da sidebar -->
    <img src="@/components/icons/logo.png" alt="Logo" class="logo" />

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
import SideBarButton from '@/components/SideBarButton.vue'; 

export default {
  components: {
    SideBarButton 
  },
  data() {
    return {
      menuItems: [
        { title: 'Tags', to: '/tags', icon: '@/components/icons/tags-icon.png' },
        { title: 'Notícias', to: '/noticias', icon: '@/components/icons/news-icon.png' },
        { title: 'APIs', to: '/apis', icon: '@/components/icons/apis-icon.png' },
        { title: 'Portais', to: '/portais', icon: '@/components/icons/portals-icon.png' }
      ]
    };
  }
};
</script>

<style scoped>
.sidebar {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  width: 80px; 
  height: 100vh;
  background: #ECE6F0;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

.logo {
  width: 60px;
  height: 60px;
  margin-bottom: 20px;
}

nav ul {
  list-style-type: none;
  padding: 0;
  width: 100%;
}

nav ul li {
  margin: 20px 0;
}
</style>
