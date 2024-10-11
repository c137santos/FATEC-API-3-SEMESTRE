<template>
  <div class="portais-view">
    <h1>Portais</h1>
    <p>Aqui você verá os portais disponíveis.</p>
    <button @click="abrirModalEdicao(portal)">Editar</button>

    <div v-if="modalAberto" class="modal-overlay">
      <div class="modal">
        <h2>Editar Portal</h2>
        <form @submit.prevent="editarPortal">
          <div>
            <label for="portal_nome">Nome do Portal:</label>
            <input v-model="portalSelecionado.nome" type="text" id="portal_nome" required />
          </div>
          <div>
            <label for="portal_url">URL do Portal:</label>
            <input v-model="portalSelecionado.url" type="url" id="portal_url" required />
          </div>
          <div>
            <label for="portal_active">Status Ativo/Inativo:</label>
            <input v-model="portalSelecionado.active" type="checkbox" id="portal_active" />
          </div>
          <div>
            <label for="portal_frequencia">Frequência:</label>
            <select v-model="portalSelecionado.frequencia" id="portal_frequencia">
              <option value="diariamente">Diariamente</option>
              <option value="semanalmente">Semanalmente</option>
              <option value="mensalmente">Mensalmente</option>
            </select>
          </div>
          <button type="submit">Salvar</button>
          <button type="button" @click="fecharModal">Cancelar</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      modalAberto: false,
      portalSelecionado: {
        id: null,
        nome: '',
        url: '',
        active: false,
        frequencia: ''
      }
    }
  },
  methods: {
    abrirModalEdicao(portal) {
      this.portalSelecionado = { ...portal }; // Clona os dados do portal selecionado
      this.modalAberto = true;
    },
    fecharModal() {
      this.modalAberto = false;
      this.portalSelecionado = { id: null, nome: '', url: '', active: false, frequencia: '' }; // Reseta os dados do portal selecionado
    },
    editarPortal() {
      fetch(`http://localhost:8081/portais/editar/${this.portalSelecionado.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.portalSelecionado)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Erro ao atualizar portal');
        }
        return response.json();
      })
      .then(data => {
        // Aqui você pode atualizar a lista de portais no frontend, se necessário
        this.fecharModal();
      })
      .catch(error => {
        console.error('Erro ao editar portal:', error);
      });
    }
  }
}
</script>

<style scoped>
.portais-view {
  padding: 20px;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 500px;
}
</style>
