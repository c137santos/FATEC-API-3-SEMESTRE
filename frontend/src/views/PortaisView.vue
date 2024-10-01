<template>
    <div>
      <AdicionarPortal @novo-portal="adicionarPortal"/>
    </div>
  </template>
  
  <script>
  import AdicionarPortal from '@/components/AdicionarPortal.vue';
  
  export default {
    name: "PortaisView",
    components: {
      AdicionarPortal
    },

    methods: {
      adicionarPortal(novoPortal) {
        this.portais.push(novoPortal)
      }
    }
    
  };
  </script>
  
  <style scoped>
  .portais-view {
    padding: 20px;
  }
  </style>
  