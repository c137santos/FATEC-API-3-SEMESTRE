<template>
    <div class="modal-overlay">
      <div class="modal-content">
        <h2 class="modal-title">Editando Api:</h2>
        <form @submit.prevent="editarApi" class="modal-form">
          <label for="nome">Nome:</label>
          <input 
            v-model="apiEmEdit.nome"
            type="text"
            id="nome"
            required 
            class="modal-input"
          />
  
          <label for="frequencia">Frequência:</label>
          <div class="select-container">
            <select class="modal-input" id="capture-period" v-model="apiEmEdit.frequencia">
              <option value="diariamente">Diariamente</option>
              <option value="semanalmente">Semanalmente</option>
              <option value="mensalmente">Mensalmente</option>
            </select>
          </div>
  
          <label for="url">URL:</label>
          <input v-model="apiEmEdit.url" class="modal-input" type="url" id="url" />

          <label for="descricao">Descrição:</label>
          <input v-model="apiEmEdit.descricao" class="modal-input" type="text" id="url" />
  
          <div class="active-group">
            <label for="active">Ativo:</label>
            <input v-model="apiEmEdit.active" class="modal-input" type="checkbox" id="active" />
          </div>        
  
          <div class="modal-actions">
            <button type="submit" class="salvar-btn">Salvar</button>
            <button type="button" class="cancelar-btn" @click="fecharModal">Cancelar</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import '@/assets/base.css';
  export default {
    emits: ['close', 'save'],
    props: {
      api: Object,
      modalAberto: Boolean
    },
    data() {
      return {
        apiEmEdit: {
          id: this.api.id,
          nome: this.api.nome,
          url: this.api.url,
          active: this.api.active,
          frequencia: this.api.frequencia,
          descricao: this.api.descricao
        }
      }
    },
    methods: {
      fecharModal() {
        this.$emit('close')
      },
      saveChanges() {
        this.$emit('save')
        this.fecharModal()
      },
      editarApi() {
        const apiAtualizado = this.apiEmEdit;
        fetch(`http://localhost:8080/apis/editar/${apiAtualizado.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(apiAtualizado)
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error('Erro ao atualizar API')
            }
            return response.json()
          })
          .then((data) => {
            this.saveChanges()
          })
          .catch((error) => {
            console.error('Erro ao editar API:', error)
          })
      }
    },
  watch: {
    api(api) {
      this.apiEmEdit = { ...api }
    }
  }
  }
  </script>
  