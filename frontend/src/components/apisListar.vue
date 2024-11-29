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
      <button class="view-data-button" @click="viewCapturedData(api.id, currentPage)">
        Clique aqui para ver os dados capturados
      </button>
    </div>
    <div v-else>Carregando APIs...</div>

    <dialog ref="dataDialog" class="data-dialog">
      <div class="dialog-header">
        <h3>Resultados capturados</h3>
      </div>
      <div class="captured-data">
        <div v-for="item in capturedData.content" :key="item.id">
          <p> Dados do dia: {{ formatDate(item.resData) }}</p>
          <vue-json-pretty :data="JSON.parse(item.resPayload)" />
        </div>
      </div>
      <div v-if="capturedData.totalPages > 1" class="pagination-controls">
        <button :disabled="currentPage === 0" @click="changePage(currentPage - 1)">Anterior</button>
        <span>Página {{ currentPage + 1 }} de {{ capturedData.totalPages }}</span>
        <button :disabled="currentPage === capturedData.totalPages - 1" @click="changePage(currentPage + 1)">Próxima</button>
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
      capturedData: {},
      currentPage: 0,
      pageSize: 1,
      apiId: null
    }
  },
  components: {
    VueJsonPretty
  },
  methods: {
    editarApi(api) {
      this.$emit('editar-api', api)
    },
    viewCapturedData(id, page) {
      this.apiId = id; 
      fetch(`http://localhost:8080/apis/resultados?apiId=${id}&page=${page}&size=${this.pageSize}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error('Network response was not ok')
          }
          return response.json()
        })
        .then((data) => {
          this.capturedData = data
          this.currentPage = page
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
    },
    formatDate(dateArray) {
      const [year, month, day] = dateArray;
      const formattedDay = String(day).padStart(2, "0");
      const formattedMonth = String(month).padStart(2, "0");
      return `${formattedDay}/${formattedMonth}/${year}`;
    },
    changePage(page) {
      if (page >= 0 && page < this.capturedData.totalPages) {
        this.viewCapturedData(this.apiId, page) 
      }
    }
  },
}
