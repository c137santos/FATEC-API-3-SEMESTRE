<template>
    <div class="lista-portais">
      <div v-if="portais.length > 0">
        <div v-for="portal in portais" :key="portal.portalId" class="portal-card">
          <div class="portal-header">
            <img src="@/assets/Logo_padrao.jpeg" alt="Logo do portal" class="info-icon" />
           <h3> {{ portal.nome }}</h3>
          </div>
          <div class="portal-info">
            <p><strong>Url:</strong>{{ portal.url }}</p>
            <p><strong>Tags:</strong> {{ Object.values(portal.tags).join(', ') }}</p>
            <p><strong>Frequência:</strong> {{ portal.frequencia }}</p>
    
          </div>
          <div class="portal-actions">
            <p><strong>Status:</strong>
              <span :class="portal.ativo ? 'status-ativo' : 'status-inativo'">
                {{ portal.ativo ? 'Ativo' : 'Inativo' }}
              </span>
            </p>
            <button @click="editarPortal(portal)" class="editar-btn">Editar</button>
          </div>
          <div class="portal-data">
            <p><strong>Data de cadastro:</strong> {{ new Date(portal.data).toLocaleDateString() }}</p>
          </div>
        </div>
      </div>
      <p v-else>Nenhum portal cadastrado.</p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        portais: [],
      };
    },
    mounted() {
      this.buscarPortais();
    },
    methods: {
      buscarPortais() {
        axios.get('http://localhost:8080/portais/listar')
          .then(response => {
            this.portais = response.data;
            console.log(this.portais[0]);
          })
          .catch(error => {
            console.error('Erro ao buscar portais:', error);
          });
      },
      editarPortal(portal) {
        this.$emit('editar-portal', portal);
      },
    },
  };
  </script>
  
  <style scoped>
  .portal-card {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    background-color: #ffffff;
    padding: 15px;
    margin-top: 10px;
    border-radius: 8px;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
    position: relative;
    z-index: 0;
  }
  
  .portal-header {
    display: flex;
    align-items: center;
  }
  
  .info-icon {
    width: 24px;
    height: 24px;
    margin-right: 10px;
  }
  
  .portal-info {
    margin-top: 10px;
  }
  
  .portal-actions {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-top: 10px;
    position: absolute;
    top: 10px;
    right: 10px;
  }
  
  .editar-btn {
    background-color: #2c2c2c;
    color: white;
    border: none;
    padding: 8px 12px;
    border-radius: 5px;
    cursor: pointer;
    margin-left: 10px;
  }
  
  .editar-btn:hover {
    background-color: #45a049;
  }
  
  .status-ativo {
    color: green;
  }
  
  .status-inativo {
    color: red;
  }
  
  .portal-data {
    text-align: right;
    margin-top: 5px;
  }
  </style>
  