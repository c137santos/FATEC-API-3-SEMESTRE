<script setup lang="ts">
import { RouterView } from 'vue-router'
</script>

<template>
  <div id="app">
  
    <RouterView />
  </div>
</template>

<style scoped>
#app {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow-x: hidden;
}

body, html {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden; 
}
</style>
