<template>
  <div class="container">
    <!-- Barra lateral -->
    <aside class="sidebar">
      <div class="logo-container">
        <img src="/home/Pedro/app/frontend/src/logo.png" alt="Logo" class="logo" />
      </div>
      <nav class="nav-links">
        <div class="nav-item">
          <div class="icon-container selected">
            <img src="/home/Pedro/app/frontend/src/icon.png" allowfullscreen alt="Tags" class="icon"/>
          </div>
          <span>Tags</span>
        </div>

        <div class="nav-item">
          <div class="icon-container">
            <img src="/home/Pedro/app/frontend/src/icon.png" allowfullscreen alt="Notícias" class="icon"/>
          </div>
          <span>Notícias</span>
        </div>

        <div class="nav-item">
          <div class="icon-container active">
            <img src="/home/Pedro/app/frontend/src/icon.png" allowfullscreen alt="APIs" class="icon"/>
          </div>
          <span class="active-link">APIs</span>
        </div>

        <div class="nav-item">
          <div class="icon-container">
            <img src="/home/Pedro/app/frontend/src/icon.png" allowfullscreen alt="Portais" class="icon"/>
          </div>
          <span>Portais</span>
        </div>
      </nav>
    </aside>

    <!-- Conteúdo principal -->
    <main class="content">
      <h1>Cérberus</h1>
      <section v-if="mostrarFormulario" class="form-section">
        <div class="form-header">
          <span class="info-icon">ⓘ</span>
          <h2>Cadastrar nova API</h2>
        </div>
        <form @submit.prevent="salvarApi">
          <div class="form-group">
            <label for="api-name">Nome da API</label>
            <input
              type="text"
              id="api-name"
              v-model="api.nome"
              placeholder="Digite o nome da API"
            />
            <span v-if="erros.nome" class="error">{{ erros.nome }}</span>
          </div>

          <div class="form-group">
            <label for="api-url">Url Api</label>
            <input
              type="text"
              id="api-url"
              v-model="api.url"
              placeholder="Digite a URL da API"
            />
            <span v-if="erros.url" class="error">{{ erros.url }}</span>
          </div>

          <div class="form-group">
            <label>
              <input type="checkbox" v-model="api.ativa" /> ativa
            </label>
          </div>

          <div class="form-group">
            <label for="capture-period">Período de captura</label>
            <select id="capture-period" v-model="api.periodoCaptura">
              <option value="diariamente">Diariamente</option>
              <option value="semanalmente">Semanalmente</option>
              <option value="mensalmente">Mensalmente</option>
            </select>
          </div>

          <button type="submit" class="btn-salvar">Salvar</button>
          <button type="button" class="btn-cancelar" @click="cancelar">
            Cancelar
          </button>
        </form>
      </section>
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      mostrarFormulario: true,
      api: {
        nome: "",
        url: "",
        ativa: true, // Checkbox por padrão ativo
        periodoCaptura: "diariamente",
      },
      erros: {
        nome: "",
        url: "",
      },
    };
  },
  methods: {
    validarFormulario() {
      let valido = true;

      // Validação do nome (obrigatório, letras e números apenas)
      if (!this.api.nome) {
        this.erros.nome = "O nome da API é obrigatório.";
        valido = false;
      } else if (!/^[a-zA-Z0-9]+$/.test(this.api.nome)) {
        this.erros.nome = "O nome deve conter apenas letras e números.";
        valido = false;
      } else {
        this.erros.nome = "";
      }

      // Validação da URL (obrigatória, deve ser uma URL válida)
      const urlPattern =
        /^(https?:\/\/)?([\w-]+(\.[\w-]+)+)([\w.,@?^=%&:/~+#-]*[\w@?^=%&/~+#-])?$/;
      if (!this.api.url) {
        this.erros.url = "A URL da API é obrigatória.";
        valido = false;
      } else if (!urlPattern.test(this.api.url)) {
        this.erros.url = "A URL da API não é válida.";
        valido = false;
      } else {
        this.erros.url = "";
      }

      return valido;
    },
    salvarApi() {
      if (this.validarFormulario()) {
        console.log("API salva com sucesso:", this.api);
        alert("API salva com sucesso!");
        this.cancelar();
      }
    },
    cancelar() {
      this.api = {
        nome: "",
        url: "",
        ativa: true,
        periodoCaptura: "diariamente",
      };
      this.erros = {
        nome: "",
        url: "",
      };
      this.mostrarFormulario = false;
    },
  },
};
</script>

<style scoped>
/* Container principal da barra lateral e do conteúdo */
.container {
  display: flex;
  height: 100vh;
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
}

/* Barra lateral */
.sidebar {
  width: 100px;
  background-color: #faf1ff;
  flex-direction: initial;
  align-items: center;
  padding-top: 20px;
  height: 100vh;
  position: fixed;
  left: 0;
}

/* Logo no topo */
.logo-container {
  margin-bottom: 20px;
  margin-left: 20px;
}

.logo {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: #ffd700; /* Cor de fundo amarela */
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Navegação da barra lateral */
.nav-links {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

.icon-container {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: background-color 0.3s ease; /* Suave transição para o hover */
}

.icon-container img {
  width: 20px;
  height: 20px;
}

.selected .icon-container {
  background-color: #f9f9f9;
}

.icon-container:hover {
  background-color: #ddd; /* Cor de fundo quando o mouse estiver sobre o ícone */
}

/* Efeito de hover para o texto ao passar o mouse */
.nav-item:hover span {
  color: #6e56d8; /* Cor do texto quando o mouse estiver sobre o ícone */
}

/* Estilo do texto abaixo dos ícones */
.nav-item span {
  font-size: 12px;
  color: #333;
  margin-top: 5px;
  font-weight: bold;
  
}

/* Conteúdo principal */
.content {
  width: 85%;
  padding: 20px;
}

h1 {
  font-size: 24px;
  font-weight: 700;
}

.form-section {
  background-color: #fff;
  padding: 20px;
  margin: 0 auto;
  margin-top: 25px;
  width: 1000px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.form-header h2 {
  margin-left: 10px;
  font-size: 20px;
  font-weight: 500;
}

.info-icon {
  font-size: 24px;
  color: black;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-size: 14px;
  font-weight: bold;
}

.form-group input[type="text"],
.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

.form-group input[type="checkbox"] {
  margin-right: 5px;
}

.btn-salvar,
.btn-cancelar {
  padding: 10px 20px;
  border: none;
  background-color: #6e56d8;
  color: #fff;
  cursor: pointer;
  margin-right: 10px;
  border-radius: 4px;
  font-size: 16px;
}

.btn-cancelar {
  background-color: #999;
}

.error {
  color: red;
  font-size: 12px;
}
</style>
