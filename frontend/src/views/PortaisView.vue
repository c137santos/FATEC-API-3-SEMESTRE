<template>
  <div>
    <AdicionarPortal @novo-portal="adicionarPortal" @portal-registrado="(id) => this.newId = id" />
  </div>
  <div class="portais-view">
    <h1>Portais Cadastrados</h1>
    <ListaPortais :key="newId" @editar-portal="abrirModalEdicao" />
    <ModalEdicaoPortal v-if="portalSelecionado" :portal="portalSelecionado" @close="fecharModal" @save="salvarEdicao" @portal-registrado="(id) => this.newId = id"/>
    <ListaPortais @editar-portal="abrirModalEdicao" />
    <ModalEdicaoPortal
      v-if="portalSelecionado"
      :portal="portalSelecionado"
      :tags="tags"
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
      portalSelecionado: null,
      newId: -1,
      tags: null,
    }
  },
  async mounted(){
    await this.carregarTags();
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
      this.portalSelecionado = null
    },
    salvarEdicao(portalEditado) {
      const index = this.portais.findIndex((portal) => portal.id === portalEditado.id)
      if (index !== -1) {
        this.portais.splice(index, 1, portalEditado)
      }
    },
    async carregarTags() {
      try {
        const response = await fetch('http://localhost:8080/tags/listar')
        if (!response.ok) {
          throw new Error('Falha ao carregar tags do backend')
        }
        const tags = await response.json();
        this.tags = tags;
      } catch (error) {
        alert('Erro ao carregar as tags. Tente novamente mais tarde.')
      }
    },
  }
}
</script>

<style scoped>
.portais-view {
  padding: 20px;
  position: relative;
  z-index: 0;
}
</style>
