<script setup lang="ts">
import SideBar from '@/components/SideBar.vue'; // Importando a Sidebar
import { RouterView } from 'vue-router';
</script>

<template>
  <div id="app">
    <!-- Barra de navegação lateral (Sidebar) -->
    <SideBar />

    <!-- Conteúdo principal -->
    <div class="main-content">
      <!-- Título do aplicativo -->
      <header class="header">
        <h1 class="cerberus-title">Cérberus</h1>
      </header>

      <!-- O conteúdo da rota atual será carregado aqui -->
      <RouterView />
    </div>
  </div>
</template>

<style scoped>
#app {
  display: flex;
  height: 100vh;
  overflow-x: hidden;
  background-color: #ffffff;
}

.header {
  top: 10;
  flex-grow: 1;
  padding-left: 80px;
  background-color: #ffffff;
}


.cerberus-title {
  font-size: 32px;
  color: #65558F;
  margin: 0;
}

.main-content {
  padding-left: 80px; /* Mantém o espaçamento da sidebar */
  flex-grow: 1;
  background-color: #ffffff; /* Cor de fundo uniforme */
} 

.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 80px;
  background-color: #ECE6F0; /* Cor de fundo uniforme */
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

body, html {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
}
</style>