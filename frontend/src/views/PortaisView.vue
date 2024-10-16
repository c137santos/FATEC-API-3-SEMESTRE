<template>
  <div>
    <AdicionarPortal @novo-portal="adicionarPortal" />
  </div>
  <div class="portais-view">
    <h1>Portais Cadastrados</h1>
    <ListaPortais @editar-portal="abrirModalEdicao" />
    <ModalEdicaoPortal
      v-if="modalAberto"
      :portal="portalSelecionado"
      :modalAberto="modalAberto"
      @close="fecharModal"
      @save="salvarEdicao"
    >
    </ModalEdicaoPortal>
  </div>
</template>

<script>
import AdicionarPortal from '@/components/AdicionarPortal.vue'
import ListaPortais from '@/components/ListaPortais.vue'
import ModalEdicaoPortal from '@/components/ModalEdicaoPortal.vue'

export default {
  name: 'PortaisView',
  components: {
    AdicionarPortal,
    ListaPortais,
    ModalEdicaoPortal
  },
  data() {
    return {
      portais: [],
      tagPortaisId: [],
      modalAberto: false,
      portalSelecionado: null
    }
  },

  methods: {
    adicionarPortal(novoPortal) {
      this.portais.push(novoPortal)
    },
    adicionarTagPortalId(novoTagPortalId) {
      this.tagPortaisId.push(novoTagPortalId)
    },
    abrirModalEdicao(portal) {
      this.portalSelecionado = portal
      this.modalAberto = true
    },
    fecharModal() {
      this.modalAberto = false
      this.portalSelecionado = null
    },
    salvarEdicao(portalEditado) {
      const index = this.portais.findIndex((portal) => portal.id === portalEditado.id)
      if (index !== -1) {
        this.portais.splice(index, 1, portalEditado)
      }
    }
  }
}
</script>

<style scoped>
.portais-view {
  padding: 20px;
}
</style>
