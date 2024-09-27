<template>
    <div class="apis-view">
      <h1>APIs Cadastradas</h1>
      <apisListar></apisListar>
    </div>
  </template>
  
  <script>
  import apisListar from '@/components/apisListar.vue';

  export default {
    name: "ApisView",
    components: {
      apisListar,
    },

  };
  </script>
  
  <style scoped>
  .apis-view {
    padding: 20px;
  }
  </style>
  