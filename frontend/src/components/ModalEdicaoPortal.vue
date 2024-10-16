<template>
  <div v-if="modalAberto" class="modal-overlay"></div>
  <div class="modal-content">
    <h2 class="modal-title">Editando Portal Notícia:</h2>
    <form @submit.prevent="editarPortal" class="modal-form">
      <div>
        <label for="nome">Nome:</label>
        <input v-model="portalLocal.nome" type="text" id="nome" required />
      </div>
      <div>
        <label for="frequencia">Frequência:</label>
        <input v-model="portalLocal.frequencia" type="text" id="frequencia" required />
      </div>
      <div>
        <label for="url">URL:</label>
        <input v-model="portalLocal.url" type="url" id="url" />
      </div>
      <div>
        <label for="active">Ativo:</label>
        <input v-model="portalLocal.ativo" type="checkbox" id="active" />
      </div>
      <div class="modal-actions">
        <button type="submit" class="salvar-btn">Salvar</button>
        <button type="button" class="cancelar-btn" @click="fecharModal">Cancelar</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'modalEdicaoPortal',
  emits: ['close', 'save'],
  props: {
    portal: Object,
    modalAberto: Boolean
  },
  data() {
    return {
      portalEmEdit: { ...this.portal }
    }
  },
  methods: {
    fecharModal() {
      this.$emit('close')
    },
    saveChanges() {
      this.$emit('save', this.portal)
      fecharModal()
    },
    editarPortal() {
      fetch(`http://localhost:8081/apis/editar/${this.portalEmEdit.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.portalEmEdit)
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
  }
}
</script>

<style scoped>
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
  z-index: 9999;
}

.modal-content {
  background: white;
  padding: 40px;
  border-radius: 10px;
  width: 60%;
  max-width: 800px;
  height: auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  position: relative;
  display: flex;
  flex-direction: column;
}

.modal-title {
  color: #4a148c;
  font-size: 26px;
  margin-bottom: 20px;
}

.modal-form {
  display: flex;
  flex-direction: column;
}

.modal-input {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #f3e5f5;
}

.checkboxEdicao {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 6px;
  margin-left: 10px;
  font-size: 16px;
  background-color: #f3e5f5;
}

.modal-select {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #f3e5f5;
}

.modal-actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
  gap: 15px;
}

.salvar-btn {
  background-color: #6a1b9a;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.salvar-btn:hover {
  background-color: #4a148c;
}

.cancelar-btn {
  background-color: transparent;
  color: #6a1b9a;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  font-size: 16px;
}

.cancelar-btn:hover {
  text-decoration: underline;
}
</style>
