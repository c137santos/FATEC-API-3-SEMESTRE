<template>
  <div class="api-views">
    <AdicionarApi @nova-api="adicionarApi" />
  </div>

  <div class="apis-view">
    <h1>APIs Cadastradas</h1>
    <apisListar :apis="apis" @editar-api="abrirModalEdicao"></apisListar>
  </div>

  <!-- Modal para editar API -->
  <div v-if="modalAberto" class="modal-overlay">
    <div class="modal">
      <h2>Editar API</h2>
      <form @submit.prevent="editarApi">
        <div>
          <label for="api_nome">Nome:</label>
          <input v-model="apiSelecionada.api_nome" type="text" id="api_nome" required />
        </div>
        <div>
          <label for="api_frequencia">Frequência:</label>
          <input v-model="apiSelecionada.api_frequencia" type="text" id="api_frequencia" required />
        </div>
        <div>
          <label for="api_descricao">Descrição:</label>
          <textarea v-model="apiSelecionada.api_descricao" id="api_descricao"></textarea>
        </div>
        <div>
          <label for="api_url">URL:</label>
          <input v-model="apiSelecionada.api_url" type="url" id="api_url" />
        </div>
        <div>
          <label for="api_active">Ativo:</label>
          <input v-model="apiSelecionada.api_active" type="checkbox" id="api_active" />
        </div>
        <button type="submit">Salvar</button>
        <button type="button" @click="fecharModal">Cancelar</button>
      </form>
    </div>
  </div>
</template>

<script>
import apisListar from '@/components/apisListar.vue'
import AdicionarApi from '@/components/AdicionarApi.vue'

export default {
  components: {
    AdicionarApi,
    apisListar
  },
  data() {
    return {
      apis: [],
      modalAberto: false,
      apiSelecionada: null // Armazena a API que será editada
    }
  },
  mounted() {
    this.fetchApiData()
  },
  methods: {
    adicionarApi(novaApi) {
      this.apis = [...this.apis, novaApi]
    },
    fetchApiData() {
      fetch('http://localhost:8081/apis/listar')
        .then((response) => {
          if (!response.ok) {
            throw new Error('Network response was not ok')
          }
          return response.json()
        })
        .then((data) => {
          this.apis = data
        })
        .catch((error) => {
          console.error('Erro ao buscar APIs:', error)
        })
    },
    abrirModalEdicao(api) {
      this.apiSelecionada = { ...api } // Clona os dados da API selecionada
      this.modalAberto = true
    },
    fecharModal() {
      this.modalAberto = false
      this.apiSelecionada = null
    },
    editarApi() {
      // Lógica para enviar os dados atualizados para o backend
      fetch(`http://localhost:8081/apis/editar/${this.apiSelecionada.api_id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.apiSelecionada)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Erro ao atualizar API')
        }
        return response.json()
      })
      .then(data => {
        // Atualiza a lista de APIs no frontend
        const index = this.apis.findIndex(api => api.api_id === this.apiSelecionada.api_id)
        if (index !== -1) {
          this.apis.splice(index, 1, data)
        }
        this.fecharModal()
      })
      .catch(error => {
        console.error('Erro ao editar API:', error)
      })
    }
  }
}
</script>

<style scoped>
.apis-view {
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
