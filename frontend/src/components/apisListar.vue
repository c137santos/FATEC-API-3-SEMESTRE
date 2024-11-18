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
        <h3>Resultados capturados</h3>
      </div>
      <div class="captured-data">
        <div>
          <div v-for="item in capturedData" :key="item">
            <p> Dados do dia: {{ formatDate(item.resData) }}</p>
            <vue-json-pretty :data="JSON.parse(item.resPayload)" />
          </div>
        </div>
      </div>
      <button class="close-button" @click="closeDialog">Fechar</button>
    </dialog>
  </div>
</template>

<script>
import VueJsonPretty from 'vue-json-pretty'
import 'vue-json-pretty/lib/styles.css'

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
    },
    formatDate(dateArray) {
      console.log(typeof(dateArray))
      console.log(dateArray)
      const [year, month, day] = dateArray;

      const formattedDay = String(day).padStart(2, "0");
      const formattedMonth = String(month).padStart(2, "0");

      return `${formattedDay}/${formattedMonth}/${year}`;
    }
  },
}
</script>

<style scoped>
/* Estilos gerais */
.api-card {
  border: 1px solid #ccc;
  padding: 16px;
  width: 100%;
  margin-top: 16px;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-sizing: border-box;
}

.api-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.api-details {
  display: flex;
  flex-direction: column;
}

.button-black {
  background-color: #333;
  color: white;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
}

.view-data-button {
  background-color: #e0e0e0;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.view-data-button:hover {
  background-color: #ccc;
}

/* Estilos para o dialog/popup */
.data-dialog {
  width: 75%;
  border: none;
  border-radius: 12px;
  padding: 20px;
  background-color: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;

  /* Centraliza o dialog na tela */
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.dialog-header {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 16px;
}

.dialog-header i.info-icon {
  font-size: 32px;
  margin-right: 10px;
}

.dialog-header h3 {
  font-size: 20px;
  margin-right: 10px;
}

.dialog-header p {
  font-size: 16px;
  margin-left: auto;
}

.captured-data {
  margin-bottom: 20px;
}

.data-item {
  margin-bottom: 16px;
}

.close-button {
  background-color: #6f3c91;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.close-button:hover {
  background-color: #5a3277;
}

.data-dialog::backdrop {
  background-color: rgba(0, 0, 0, 0.5);
}
</style>
