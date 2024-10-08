<template>
  <div>
    <AdicionarPortal @novo-portal="adicionarPortal" />
  </div>
  <div class="portais-view">
    <h1>Portais Cadastrados</h1>
    <ListaPortais />
  </div>
</template>

<script>
import AdicionarPortal from '@/components/AdicionarPortal.vue'
import ListaPortais from '@/components/ListaPortais.vue';


export default {
  name: 'PortaisView',
  components: {
    AdicionarPortal,
    ListaPortais
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
}
</script>

<style scoped>
.portais-view {
  padding: 20px;
}
</style>
