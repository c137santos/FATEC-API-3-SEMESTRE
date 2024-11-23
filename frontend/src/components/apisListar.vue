<template>
  <div>
    <div v-if="apis.length" v-for="api in apis" :key="api.id" class="api-card">
      <div class="api-header">
        <div class="api-details">
          <h3>{{ api.nome }}</h3>
          <p>{{ api.url }}</p>
          <p>{{ api.frequencia }}</p>
        </div>
        <button class="button-black" @click="editarApi(api)">editar</button>
      </div>
      <button class="view-data-button" @click="viewCapturedData(api.id)">
        Clique aqui para ver os dados capturados
      </button>
    </div>
    <div v-else>Carregando APIs...</div>

    <dialog ref="dataDialog" class="data-dialog">
      <div class="dialog-header">
        <h3>API</h3>
      </div>
      <div class="captured-data">
        <div>
          <vue-json-pretty :data="capturedData" />
        </div>
      </div>
      <button class="close-button" @click="closeDialog">Fechar</button>
    </dialog>
  </div>
</template>

<script>
import VueJsonPretty from 'vue-json-pretty'
import 'vue-json-pretty/lib/styles.css'
import '@/assets/base.css';

export default {
  props: {
    apis: Array
  },
  data() {
    return {
      capturedData: []
    }
  },
  components: {
    VueJsonPretty
  },
  methods: {
    editarApi(api) {
      this.$emit('editar-api', api)
    },
    viewCapturedData(id) {
      fetch(`http://localhost:8080/apis/resultados?apiId=${id}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error('Network response was not ok')
          }
          return response.json()
        })
        .then((data) => {
          this.capturedData = data
          this.$refs.dataDialog.showModal()
        })
        .catch((error) => {
          console.error('Erro ao buscar dados capturados:', error)
        })
    },
    closeDialog() {
      this.$refs.dataDialog.close()
    },
    formatJson(data) {
      return JSON.stringify(data, null, 2).replace(/\\n/g, '<br>').replace(/ /g, '&nbsp;')
    }
  }
}
</script>