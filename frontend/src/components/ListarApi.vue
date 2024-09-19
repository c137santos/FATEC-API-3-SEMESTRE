<template>
  <div class="apis-cadastradas">
    <h2 class="tituloum">Apis Cadastradas</h2>
    <div v-for="(api, index) in apis" :key="index" class="api-item">
      <div class="api-info">
        <i class="fas fa-info-circle"></i>
        <h3 class="nomeum">{{ api.name }}</h3>
        <p>url: {{ api.url }}</p>
        <p>período de captura: {{ api.capturePeriod }}</p>
        <button class="view-data" @click="viewData(api)">Clique aqui para ver os dados capturados</button>
      </div>
      <button class="edit-api" @click="editApi(api)">editar</button>
    </div>

    <div v-if="isModalVisible" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="titulotres">{{ selectedApi.name }}</h3>
          <button class="close-btn" @click="closeModal">X</button>
        </div>
        <div class="modal-body">
          <p><strong>URL:</strong> {{ selectedApi.url }}</p>
          <p><strong>Período de Captura:</strong> {{ selectedApi.capturePeriod }}</p>
          <p><strong>Dados Capturados:</strong></p>
          <p>{{ selectedApi.data }}</p>
        </div>
        <div class="modal-footer">
          <button @click="closeModal">Fechar</button>
        </div>
      </div>
    </div>
  </div>

  <div
      v-if="isEditModalVisible"
      class="modal-overlay"
      @click.self="closeEditModal"
  >

    <div class="modal-contentdois">
      <div class="info-img" img src = "WhatsApp Image 2024-09-12 at 15.03.18.jpeg">

      </div>
      <div class="modal-edit-content">
        <div class="modalEdit-header">
          <h3 class="titulodois">Editando API:</h3>
          <button class="close-btn" @click="closeEditModal">X</button>
        </div>

        <div class="modalEdit-body">
          <form @submit.prevent="saveChanges">

            <div class="modalEdit-form-input">
              <label>
                Nome da API:
              </label>
              <input v-model="editApiData.name" type="text" required />
            </div>

            <div class="modalEdit-form-input">
              <label>
                URL API:
              </label>
              <input v-model="editApiData.url" type="url" required />
            </div>

            <div>
              <input v-model="editApiData.url" type="checkbox"/>
              Ativo
            </div>

            <b>Período de captura</b>
            <select name="captura" required="required">
              <option value="">Selecione</option>
              <option value="Diariamente">Diariamente</option>
              <option value="Semanalmente">Semanalmente</option>
              <option value="Mensalmente">Mensalmente</option>

            </select>
            <div class="modalEdit-footer">
              <button class="submit-button" type="submit">Salvar</button>
              <button class="cancel-button" @click="closeEditModal">Cancelar</button>
            </div>

          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      apis: [
        {
          name: 'Nome Api',
          url: 'https://example.com',
          capturePeriod: '2023-03-01',
          data: [
            // Data from the API
          ]
        },
        {
          name: 'Nome Api',
          url: 'https://example2.com',
          capturePeriod: '2023-03-15',
          data: [
            // Data from the API
          ]
        },
      ],
      isModalVisible: false,
      isEditModalVisible: false,
      selectedApi: null,
      editApiData: null

    };
  },
  methods: {
    viewData(api) {
      this.selectedApi = api;
      this.isModalVisible = true;
    },
    closeModal() {
      this.isModalVisible = false;
      this.selectedApi = null;
    },
    editApi(api) {
      this.editApiData = { ...api }; // Faz uma cópia dos dados da API para edição
      this.isEditModalVisible = true;
    },
    closeEditModal() {
      this.isEditModalVisible = false;
      this.editApiData = null;
    },
    saveChanges() {
      const index = this.apis.findIndex(api => api.url === this.editApiData.url);
      if (index !== -1) {
        this.apis[index] = { ...this.editApiData };
      }
      this.closeEditModal();
    }
  }
};

</script>

<style>

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
  padding: 10px;
  margin-bottom: 10px;
  width: 100%;
  border-radius: 8px;
}

.api-info {
  display: flex;
  flex-direction: column;
}

.edit-api {
  padding: 8px 16px;
  background-color: #4a4848;
  color: white;
  border: 2px solid transparent;
  cursor: pointer;
  border-radius: 8px;
  margin-top: -70px;
}

.view-data {
  padding: 8px 16px;
  background-color: #eaeaea;
  color: black;
  border: 1px solid black;
  cursor: pointer;
  border-radius: 8px;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
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
  background: none;
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

.modal-contentdois {
  display: flex;
  flex-direction: row;
  row-gap: 20px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 100%;
  max-width: 800px;
  position: relative;
}

.info-img {
  margin-right: 20px;
}

.modal-edit-content {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.modalEdit-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0;
}

.modalEdit-body form {
  display: flex;
  flex-direction: column;
  margin: 20px 0;
  row-gap: 10px;
}

.modalEdit-form-input {
  display: flex;
  flex-direction: column;
  padding: 10px;
  background-color: #E6E0E8;
  border-bottom: 1px solid #7f7b83;
}

.modalEdit-body select {
  background-color: transparent;
  width: 200px;
  padding: 5px;
  border-radius: 5px;
}

.modalEdit-form-input label {
  font-size: 8pt;
  color: gray;
}

.modalEdit-form-input input {
  border: none;
  background-color: transparent;
  font-size: 12pt;
  color: dimgray;
}

.modalEdit-footer {
  display: flex;
  flex-direction: row;
  column-gap: 10px;
  text-align: right;
}

.submit-button {
  padding: 10px;
  background-color: #64548d;
  color: white;
  border: 2px solid transparent;
  border-radius: 10px;
}

.cancel-button {
  padding: 10px;
  background-color: transparent;
  color: #64548d;
  border: none;
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