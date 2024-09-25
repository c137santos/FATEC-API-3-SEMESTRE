<template>
    <div class="apis-cadastradas">
    <h2 class="tituloum">Apis Cadastradas</h2>
    <div v-for="(api, index) in apis" :key="index" class="api-item">
      <div class="api-info">
        <i class="fas fa-info-circle"></i>
        <h3 class="nomeum">{{ api.name }}</h3>
        <p><strong>url:</strong> {{ api.url }}</p>
        <p><strong>Período de captura</strong>: {{ api.frequencia }}</p>
        <p><strong>Descrição</strong>: {{ api.descricao }}</p>
        <p><strong>Status</strong>: {{ api.active ? 'ativa' : 'inativa' }}</p>
        <button class="view-data" @click="viewData(api)">Clique aqui para ver os dados capturados</button>
      </div>
      <button class="edit-api">editar</button>
    </div>

    <div v-if="isModalVisible" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="titulotres">{{ selectedApi.name }}</h3>
          <button class="close-btn" @click="closeModal">X</button>
        </div>
        <div class="modal-body">
          <p><strong>URL:</strong> {{ selectedApi.url }}</p>
          <p><strong>Período de Captura:</strong> {{ selectedApi.frequencia }}</p>
          <p><strong>Dados Capturados:</strong></p>
          <p>{{ selectedApi.data }}</p>
        </div>
        <div class="modal-footer">
          <button class="edit-api" @click="closeModal">Fechar</button>
        </div>
      </div>
    </div>
  </div>

  <div
      v-if="isEditModalVisible"
      class="modal-overlay"
      @click.self="closeEditModal"
  >
  </div>
</template>

<script>
export default {
  data() {
    return {
      apis: [], 
      isModalVisible: false,
      isEditModalVisible: false,
      selectedApi: null,
      editApiData: null,
    };
  },
  mounted() {
    this.fetchApis();
  },
  methods: {

    fetchApis() {
  const requestOptions = {
    method: 'GET',
    headers: { 'Content-Type': 'application/json' }
  };
  fetch('http://localhost:8080/apis/listar', requestOptions)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      this.apis = data;
    })
    .catch(error => {
      console.error('Erro ao buscar as APIs:', error);
    });
},
    viewData(api) {
      this.selectedApi = api;
      this.isModalVisible = true;
    },
    closeModal() {
      this.isModalVisible = false;
      this.selectedApi = null;
    },
  }
};
</script>


<style scoped>

.apis-cadastradas {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.api-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #ccc;
  background-color: #f5f5f5;
  padding: 15px;
  margin-top: 10px;
  margin-bottom: 10px;
  width: 100%;
  border-radius: 8px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.api-info {
  display: flex;
  flex-direction: column;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  max-width: 600px;
  position: relative;
}

.close-btn {
  border: none;
  font-size: 1.5em;
  cursor: pointer;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-body form {
  display: flex;
  flex-direction: column;
  margin: 20px 0;
}

.modal-footer {
  text-align: right;
}

.info-img {
  margin-right: 20px;
}

.tituloum {
  color: #181818;
  font-weight: bold;
}

.nomeum {
  color: #181818;
  font-weight: bold;
}
.titulodois {
  color: #181818;
  font-weight: bold;
}
.titulotres {
  color: #181818;
  font-weight: bold;
}
</style>