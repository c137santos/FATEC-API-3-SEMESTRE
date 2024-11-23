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
  import '@/assets/base.css';
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
  