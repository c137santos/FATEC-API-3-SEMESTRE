<template>

  <div class="api-views">

    <AdicionarApi @api-salva="handleApiSalva" />
  </div>
  </template>
  
  <script>
import AdicionarApi from '@/components/AdicionarApi.vue';


  export default {
    components: {
      AdicionarApi,
    },

  };
  </script>
  
  <style scoped>
  .apis-view {
    padding: 20px;
  }
  </style>
  