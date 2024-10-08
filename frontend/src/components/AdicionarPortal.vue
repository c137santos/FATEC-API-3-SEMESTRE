<template>
  <div class="portal-cadastrado">
    <div class="novo-portal">
      <button class="cadastrar-portal-novo" @click="toggleNovoPortalForm">Cadastrar Portal</button>

      <div v-if="exibirNovoPortalForm" class="formulario-cadastro-portal">
        <form @submit.prevent="validadorDadosNovoPortal">
          <img class="imagem-logo" src="@/assets/Logo_padrao.jpeg" />
          <input
            class="campo-cadastro-nome"
            type="text"
            v-model="novoPortal.portalNome"
            placeholder="Nome do Portal"
          />
          <input
            class="campo-cadastro-url"
            type="url"
            v-model="novoPortal.portalUrl"
            placeholder="Url do Portal"
          />
        </form>
        <div>
          <label class="checkbox-portal">Ativo</label>
          <input class="checkAtivoPortal" type="checkbox" v-model="novoPortal.portalAtivo" />
        </div>
        <div>
          <label>
            <select v-model="novoPortal.portalFrequencia" class="seletorFrequencia">
              <option value="diaria">Diária</option>
              <option value="semanal">Semanal</option>
              <option value="mensal">Mensal</option>
            </select>
          </label>
        </div>
        <div>
          <p>Tags:</p>
          <select v-model="novoPortal.tagId" class="seletorTags">
            <option v-for="tag in tags" :key="tag.tagId" :value="tag.tagId">
              {{ tag.tagNome }}
            </option>
          </select>
        </div>
        <button class="botao-salvar-portal" type="submit" @click="validadorDadosNovoPortal">
          Salvar
        </button>
        <button class="botao-cancelar-portal" @click.prevent="cancelarCadastro">Cancelar</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      seletorTags: '',
      portais: [],
      tags: [],
      novoPortal: {
        portalNome: '',
        portalUrl: '',
        portalAtivo: true,
        portalFrequencia: '',
        tagId: ''
      },
      exibirNovoPortalForm: false,
      selectPortal: ''
    }
  },

  mounted() {
    this.carregarTags()
  },

  methods: {
    toggleNovoPortalForm() {
      this.exibirNovoPortalForm = !this.exibirNovoPortalForm
    },

    async carregarTags() {
      try {
        const response = await fetch('http://localhost:8080/tags/listar')
        if (!response.ok) {
          throw new Error('Falha ao carregar tags do backend')
        }
        const tags = await response.json()
        this.tags = tags
      } catch (error) {
        alert('Erro ao carregar as tags. Tente novamente mais tarde.')
      }
    },

    salvarPortal() {
      const requestOption = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.novoPortal)
      }
      fetch('http://localhost:8080/portais/cadastrar', requestOption).then((response) =>
        response.json()
      )
    },

    validadorDadosNovoPortal() {
      if (this.novoPortal.portalNome && this.novoPortal.portalUrl) {
        this.salvarPortal()
        let portalSerSalvo = { ...this.novoPortal }
        this.$emit('novo-portal', portalSerSalvo)
        this.novoPortal.portalUrl = ''
        this.novoPortal.portalNome = ''
        this.novoPortal.portalAtivo = true
        this.novoPortal.portalFrequencia = ''
        this.exibirNovoPortalForm = false
      }
    },

    cancelarCadastro() {
      this.novoPortal = { portalNome: '', portalUrl: '', portalAtivo: true, portalFrequencia: '' }
      this.exibirNovoPortalForm = false
    }
  }
}
</script>

<style scoped>
.portal-cadastrado {
  margin-top: 10px;
  width: 100%;
}

.formulario-cadastro-portal {
  width: 700px;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  margin-top: 20px;
  background-color: #f9f9f9;
}

.imagem-logo {
  width: 25px;
  margin-right: 35px;
}

.campo-cadastro-nome,
.campo-cadastro-url {
  display: block;
  width: 600px;
  padding: 8px;
  margin-bottom: 15px;
  border-radius: 5px;
  background-color: #e4ceff;
}

.checkbox-portal {
  margin-top: 20px;
}

.seletorTags, .seletorFrequencia {
  width: 300px;
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #f3e5f5;
}

/* Botões de salvar, cancelar e cadastrar */
.botao-salvar-portal,
.botao-cancelar-portal,
.cadastrar-portal-novo {
  padding: 8px 16px;
  margin-top: 10px;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

/* Botão cadastrar nova tag */
.cadastrar-portal-novo {
  background-color: black;
  margin-bottom: 20px;
}

/* Botão salvar */
.botao-salvar-portal {
  background-color: rgb(141, 107, 207);
}

/* Botão cancelar */
.botao-cancelar-portal {
  color: mediumpurple;
  border: none;
  background-color: white;
}
</style>
