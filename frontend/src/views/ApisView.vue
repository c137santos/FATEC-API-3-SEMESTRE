<template>
  <div class="container">
    <main class="content">
      <section v-if="mostrarFormulario" class="form-section">
        <div class="form-header">
          <img src="frontend/src/components/icons/info.png" alt="icon" class="info-icon" />
          <h2>Cadastrar nova API</h2>
        </div>
        <form @submit.prevent="salvarApi">
          <div class="form-group">
            <input
              type="text"
              id="api-name"
              v-model="api.nome"
              placeholder="Nome da API"
            />
            <img src="frontend/src/components/icons/excluir.png" alt="Clear Icon" class="clear-icon" @click="clearInput('nome')" />
            <span v-if="erros.nome" class="error">{{ erros.nome }}</span>
          </div>

          <div class="form-group">
            <input
              type="text"
              id="api-url"
              v-model="api.url"
              placeholder="URL da API"
            />
            <img src="frontend/src/components/icons/excluir.png" alt="Clear Icon" class="clear-icon" @click="clearInput('url')" />
            <span v-if="erros.url" class="error">{{ erros.url }}</span>
          </div>

          <div class="form-group">
            <label>
              <input type="checkbox" v-model="api.ativa" /> ativa
            </label>
          </div>

          <div class="form-group">
            <label for="capture-period" id="form-label">Período de captura</label>
            <div class="select-container">
            <select id="capture-period" v-model="api.periodoCaptura">
              <option value="diariamente">Diariamente</option>
              <option value="semanalmente">Semanalmente</option>
              <option value="mensalmente">Mensalmente</option>
            </select>
          </div>
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
      } else if (!/^[a-zA-Z0-9\s]+$/.test(this.api.nome)) {
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
    clearInput(campo) {
      if (campo === 'nome') {
        this.api.nome = '';
        this.erros.nome = '';
      } else if (campo === 'url') {
        this.api.url = '';
        this.erros.url = '';
      }
    }
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'SF Pro';
  src: url('frontend/src/components/fonts/SFPRODISPLAYREGULAR.OTF') format('truetype'); /* Adapte o caminho se necessário */
  font-weight: 500;
}


/* Container principal*/
.container {
  display: flex;
  height: 100vh;
  font-family: 'SF Pro', sans-serif;
  font-size: 18px;
  color: black;
}

.content {
  width: 85%;
  padding: 20px;
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



.info-icon {
  width: 25px;
  height: 25px;
  margin-right: 17px;
}

.clear-icon {
  width: 25px;
  height: 25px;
  position: absolute;
  right: 10px; /* Distância do lado direito */
  top: 50%; /* Centraliza verticalmente */
  transform: translateY(-50%); /* Ajusta para centralização */
  cursor: pointer;

  
}

.form-group {
  position: relative;
  margin-bottom: 20px;
  flex-direction: column;
}

.form-group input[type="text"] {
  width: 100%;
  padding: 25px 10px 20px; /* Adiciona mais espaço no topo para o placeholder */
  background-color: #e6e0e9;
  border: 0px;
  border-bottom: 2px solid black;
  border-radius: 5px;
  font-size: 16px;
  box-sizing: border-box;
}

.form-group input::placeholder {
  color: black; 
  font-size: 11.4px;
  position: relative;
  top: -19px;
}
.form-label {
font-size: 16px;
  font-weight: 600;
}

.select-container {
  margin-top: 2px; /* Ajuste conforme necessário */
}
.form-group select {
  font-size: 16px;
  border-radius: 10px;
  text-align: left;
  padding-right: 90px;
  padding-top: 7px;
  padding-bottom: 7px;
  border-color: #d9d9d9;

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
  background-color: #fff;
  color:#6e56d8
}

.error {
  color: red;
  font-size: 12px;
}
</style>
  