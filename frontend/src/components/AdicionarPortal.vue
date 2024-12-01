<template>
  <div class="portal-cadastrado">
    <div class="novo-portal">
      <button class="cadastrar-portal-novo" @click="toggleNovoPortalForm">Cadastrar Portal</button>
    </div>

      <div v-if="exibirNovoPortalForm" class="formulario-cadastro-portal">
        <div>
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
              <option value="diariamente">Diariamente</option>
              <option value="semanalmente">Semanalmente</option>
              <option value="mensalmente">Mensalmente</option>
            </select>
          </label>
        </div>
        
        <div>
          <p>Tags:</p>
          <button type="button" @click="abrirModal = true" class="botao-selecionar-tags">
            Selecionar Tags
          </button>
          <p>Tags selecionadas: {{ tagsSelecionadasNomes }}</p>
        </div>
        <button class="botao-salvar-portal" type="submit" @click="validadorDadosNovoPortal">Salvar</button>
        <button class="botao-cancelar-portal" @click.prevent="cancelarCadastro">Cancelar</button>
      </div>
    </div>

    <div v-if="abrirModal" class="modal-overlay-tags">
      <div class="modal-content-tags">
        <h3>Selecione as Tags</h3>
        <div v-for="tag in tags" :key="tag.tagId">
          <input type="checkbox" :id="tag.tagId" :value="tag.tagId" v-model="tagsSelecionadas">
          <label :for="tag.tagId" class="nome-tags-selecionadas">{{ tag.tagNome }}</label>
        </div>
        <button @click="abrirModal = false" class="botao-salvar-multiplcas-tags">salvar</button>
        <button @click="abrirModal = false" class="botao-cancelar-multiplcas-tags">cancelar</button>
      </div>
    </div>
  </div>
</template>

<script>
import '@/assets/base.css';
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
      abrirModal: false,
      tagsSelecionadas: [],
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
      this.novoPortal.tagsSelecionadas = this.tagsSelecionadas;
      const requestOption = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.novoPortal)
      }
      fetch('http://localhost:8080/portais/cadastrar', requestOption).then((response) => {
        return response.json()
      }).then((data) => {
        this.$emit('portal-registrado', data)
      })
    },

    validadorDadosNovoPortal() {
      let url = this.novoPortal.portalUrl;
      const urlPattern = /^(https?:\/\/)?([\w-]+(\.[\w-]+)+)([\w.,@?^=%&:/~+#-]*[\w@?^=%&/~+#-])?$/;

      if (url && !/^https?:\/\//i.test(url)) {
        if (/^www\./i.test(url)) {
          url = 'https://' + url;
        } else {
          url = 'https://www.' + url;
        }
      }
      
      if (urlPattern.test(url)) {
        this.novoPortal.portalUrl = url;
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
      } else {
        console.error('URL inválida');
      }
    },

    cancelarCadastro() {
      this.novoPortal = { portalNome: '', portalUrl: '', portalAtivo: true, portalFrequencia: '' }
      this.tagsSelecionadas = []
      this.exibirNovoPortalForm = false
    },
  },
  computed: {
      tagsSelecionadasNomes() {
        return this.tags
        .filter(tag => this.tagsSelecionadas.includes(tag.tagId))
        .map(tag => tag.tagNome)
        .join(', ')
      }
    }
}
</script>
