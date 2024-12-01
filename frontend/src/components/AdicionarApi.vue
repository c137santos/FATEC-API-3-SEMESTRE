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
              @click="clearInput('url')"
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
              @click="clearInput('descricao')"
            />
            <span v-if="erros.descricao" class="error">{{ erros.descricao }}</span>
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
        url: '',
        descricao: '',
        active: true,
        frequencia: 'diariamente',
      },
      erros: {
        nome: '',
        url: '',
        descricao: '',
      },
    };
  },
  methods: {
    toggleNovaApiForm() {
      this.mostrarFormulario = !this.mostrarFormulario;
    },

    validarFormulario() {
      let valido = true;

      if (!this.api.nome) {
        this.erros.nome = 'O nome da API é obrigatório.';
        valido = false;
      } else if (!/^[a-zA-Z0-9\s]+$/.test(this.api.nome)) {
        this.erros.nome = 'O nome deve conter apenas letras e números.';
        valido = false;
      } else {
        this.erros.nome = '';
      }

      let url = this.api.url;
      const urlPattern = /^(https?:\/\/)?([\w-]+(\.[\w-]+)+)([\w.,@?^=%&:/~+#-]*[\w@?^=%&/~+#-])?$/;

      if (url && !/^https?:\/\//i.test(url)) {
        if (/^www\./i.test(url)) {
          url = 'https://' + url;
        } else {
          url = 'https://www.' + url;
        }
      }

      if (!this.api.url) {
        this.erros.url = 'A URL da API é obrigatória.';
        valido = false;
      } else if (!urlPattern.test(url)) {
        this.erros.url = 'A URL da API não é válida.';
        valido = false;
      } else {
        this.erros.url = '';
        this.api.url = url;
      }

      if (!this.api.descricao) {
        this.erros.descricao = 'A descrição da API é obrigatória.';
        valido = false;
      } else {
        this.erros.descricao = '';
      }

      return valido;
    },

    salvarApi() {
      if (this.validarFormulario()) {
        const requestOptions = {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.api),
        };
        fetch('http://localhost:8080/apis/cadastrar', requestOptions)
          .then((response) => response.json())
          .then((data) => {
            console.log('API salva com sucesso no backend:', data);
          })
          .catch((error) => {
            console.error('Erro ao salvar API:', error);
          });
        this.cancelar();
      }
    },

    cancelar() {
      this.mostrarFormulario = false;
      this.clearAllInput();
    },

    clearInput(campo) {
      this.api[campo] = '';
      this.erros[campo] = '';
    },

    clearAllInput() {
      this.api = {
        nome: '',
        url: '',
        descricao: '',
        active: true,
        frequencia: 'diariamente',
      };
      this.erros = { nome: '', url: '', descricao: '' };
    },
  },
};
</script>

<style src="@/assets/base.css"></style>
