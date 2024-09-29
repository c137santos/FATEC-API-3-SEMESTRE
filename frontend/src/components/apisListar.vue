<template>
    <div>
      <div v-if="apis.length" v-for="api in apis" :key="api.id" class="api-card">
        <div class="api-header">
          <div class="api-details">
            <h3>{{ api.nome }}</h3>
            <p>{{ api.url }}</p>
            <p>{{ api.frequencia }}</p>
          </div>
          <button class="edit-button" @click="editApi(api.id)">editar</button>
        </div>
        <button class="view-data-button" @click="viewCapturedData(api.id)">
          Clique aqui para ver os dados capturados
        </button>
      </div>
      <div v-else>
        Carregando APIs...
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        apis: []
      };
    },
    created() {
      this.fetchApiData();
    },
    methods: {
      fetchApiData() {
        axios
          .get('http://localhost:8081/apis/listar')
          .then(response => {
            this.apis = response.data;
          })
          .catch(error => {
            console.error("Erro ao buscar APIs:", error);
          });
      },
      editApi(id) {
        // TO-DO - parte de editar api
        console.log("Editar API com id:", id);
      },
      viewCapturedData(id) {
        console.log("Ver dados capturados para API com id:", id);
      }
    }
  };
  </script>
  
  <style scoped>
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
  
  .info-icon {
    font-size: 24px;
    margin-right: 8px;
  }
  
  .edit-button {
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
  </style>