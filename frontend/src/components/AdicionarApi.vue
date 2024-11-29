<template>
  <div class="api-cadastrar">
    <div class="Nova-api">
      <button class="btn-show-form" @click="toggleNovaApiForm">Cadastrar Nova Api</button>

      <div v-if="mostrarFormulario" class="form-section">
        <div class="form-header">
          <img src="@/components/icons/info.png" alt="icon" class="info-icon" />
          <h2>Cadastrar nova API</h2>
        </div>

        <form @submit.prevent="salvarApi">
          <div class="form-group">
            <input type="text" id="api-name" v-model="api.nome" placeholder="Nome da API" />
            <img
              src="@/components/icons/excluir.png"
              alt="Clear Icon"
              class="clear-icon"
              @click="clearInput('nome')"
            />
            <span v-if="erros.nome" class="error">{{ erros.nome }}</span>
          </div>

          <div class="form-group">
            <input type="text" id="api-url" v-model="api.url" placeholder="URL da API" />
            <img
              src="@/components/icons/excluir.png"
              alt="Clear Icon"
              class="clear-icon"
              @click="clearInput('descricao')"
            />
            <span v-if="erros.url" class="error">{{ erros.url }}</span>
          </div>

          <div class="form-group">
            <input
              type="text"
              id="api-descricao"
              v-model="api.descricao"
              placeholder="Descrição da API"
            />
            <img
              src="@/components/icons/excluir.png"
              alt="Clear Icon"
              class="clear-icon"
              @click="clearInput('url')"
            />
            <span v-if="erros.url" class="error">{{ erros.url }}</span>
          </div>

          <div class="form-group">
            <label> <input type="checkbox" v-model="api.active" /> Ativa </label>
          </div>

          <div class="form-group">
            <label for="capture-period" id="form-label">Período de captura</label>
            <div class="select-container">
              <select id="capture-period" v-model="api.frequencia">
                <option value="diariamente">Diariamente</option>
                <option value="semanalmente">Semanalmente</option>
                <option value="mensalmente">Mensalmente</option>
              </select>
            </div>
          </div>

          <button type="submit" class="bnt-salvar">Salvar</button>
          <button type="button" class="bnt-cancelar" @click="cancelar">Cancelar</button>
        </form>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      mostrarFormulario: false,
      api: {
        nome: '',
        descricao: '',
        ativo: true
      },
      erros: {
        nome: '',
        descricao: ''
      },
      tags: [], // Lista de tags cadastradas
    };
  },
  methods: {
    // Toggle para mostrar/ocultar o formulário
    toggleNovaApiForm() {
      this.mostrarFormulario = !this.mostrarFormulario;
    },

    // Salvar a nova API
    salvarApi() {
      if (this.validarFormulario()) {
        // Aqui você pode fazer a chamada para salvar no backend
        this.tags.push({ nome: this.api.nome });
        console.log('API salva com sucesso:', this.api);
        this.cancelar(); // Fecha o formulário após salvar
      }
    },

    // Validar os campos do formulário
    validarFormulario() {
      this.erros = { nome: '', descricao: '' };
      let valido = true;

      if (!this.api.nome) {
        this.erros.nome = 'O nome da API é obrigatório.';
        valido = false;
      }

      if (!this.api.descricao) {
        this.erros.descricao = 'A descrição da API é obrigatória.';
        valido = false;
      }

      return valido;
    },

    // Cancelar o formulário e limpar os dados
    cancelar() {
      this.mostrarFormulario = false;
      this.api = { nome: '', descricao: '', ativo: true };
      this.erros = { nome: '', descricao: '' };
    }
  }
};
</script>

<style src="@/assets/base.css"></style> <!-- CSS externo -->
