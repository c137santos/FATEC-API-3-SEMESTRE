<template>
  <div class="api-views">
    <AdicionarApi @nova-api="adicionarApi" />
  </div>

  <div class="apis-view">
    <h1>APIs Cadastradas</h1>
    <apisListar 
        :apis="apis"
        @editar-api="abrirModalEdicao"
      >
    </apisListar>
    <ModalEdicaoApi
      v-if="apiSelecionada"
      :api="apiSelecionada"
      @close="fecharModal"
      @save="salvarEdicao"
      >
    </ModalEdicaoApi>
  </div>
</template>

<script>
import apisListar from '@/components/apisListar.vue'
import AdicionarApi from '@/components/AdicionarApi.vue'
import ModalEdicaoApi from '@/components/ModalEdicaoApi.vue';

export default {
  components: {
    AdicionarApi,
    apisListar,
    ModalEdicaoApi
  },
  data() {
    return {
      apis: [],
      apiSelecionada: null
    }
  },
  mounted() {
    this.fetchApiData()
  },
  methods: {
    adicionarApi(novaApi) {
      this.apis = [...this.apis, novaApi]
    },
    abrirModalEdicao(api) {
      this.apiSelecionada = api
      this.modalAberto = true
    },
    fecharModal() {
      this.apiSelecionada = null
    },
    salvarEdicao(){
      this.fetchApiData()
    },
    fetchApiData() {
      fetch('http://localhost:8080/apis/listar')
        .then((response) => {
          if (!response.ok) {
            throw new Error('Network response was not ok')
          }
          return response.json()
        })
        .then((data) => {
          this.apis = data
        })
        .catch((error) => {
          console.error('Erro ao buscar APIs:', error)
        })
    }
  }
}
</script>

<style scoped>
.apis-view {
  padding: 20px;
}
</style>
