<template>
    <div class="apis-view">
      <h1>APIs Cadastradas</h1>
      <apisListar></apisListar>
    </div>

  <div class="api-views">

    <AdicionarApi @api-salva="handleApiSalva" />
  </div>
  </template>
  
  <script>
import apisListar from '@/components/apisListar.vue';
import AdicionarApi from '@/components/AdicionarApi.vue';


  export default {
    components: {
      AdicionarApi,
      apisListar,
    },

  };
  </script>
  
  <style scoped>
  .apis-view {
    padding: 20px;
  }
  </style>
  