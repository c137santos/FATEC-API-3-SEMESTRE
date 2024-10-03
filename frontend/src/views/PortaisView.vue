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
    data() {
      return {
        portais: [],
        tagPortaisId: []
      }
    },

    methods: {
      adicionarPortal(novoPortal) {
        this.portais.push(novoPortal)
      },
      adicionarTagPortalId(novoTagPortalId) {
        this.tagPortaisId.push(novoTagPortalId)
      }
    }
    
  };
  </script>
  
  <style scoped>
  .portais-view {
    padding: 20px;
  }
  </style>
  