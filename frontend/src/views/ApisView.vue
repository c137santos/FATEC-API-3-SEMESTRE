<template>
  <div class="api-views">
    <AdicionarApi @nova-api="adicionarApi" />
  </div>

  <div class="apis-view">
    <h1>APIs Cadastradas</h1>
    <apisListar :apis="apis"></apisListar>
  </div>
</template>

<script>
import apisListar from '@/components/apisListar.vue'
import AdicionarApi from '@/components/AdicionarApi.vue'

export default {
  components: {
    AdicionarApi,
    apisListar
  },
  data() {
    return {
      apis: []
    }
  },
  mounted() {
    this.fetchApiData()
  },
  methods: {
    adicionarApi(novaApi) {
      this.apis = [...this.apis, novaApi]
    },
    fetchApiData() {
      fetch('http://localhost:8081/apis/listar')
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
