<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2 class="modal-title">Editando Portal Notícia:</h2>
      <form @submit.prevent="editarPortal" class="modal-form">
        <label for="nome">Nome:</label>
        <input v-model="portalEmEdit.nome" type="text" id="nome" required class="modal-input" />

        <label for="frequencia">Frequência:</label>
        <div class="select-container">
          <select class="modal-input" id="capture-period" v-model="portalEmEdit.frequencia">
            <option value="diariamente">Diariamente</option>
            <option value="semanalmente">Semanalmente</option>
            <option value="mensalmente">Mensalmente</option>
          </select>
        </div>

        <label for="url">URL:</label>
        <input v-model="portalEmEdit.url" class="modal-input" id="url" />

        <div class="active-group">
          <label for="active">Ativo:</label>
          <input v-model="portalEmEdit.ativo" class="modal-input" type="checkbox" id="active" />
        </div>

        <div>
          <p>Tags:</p>
          <button type="button" @click="abrirModal = true" class="botao-selecionar-tags">Selecionar Tags</button>
          <p>Tags selecionadas: {{ tagsSelecionadasNomes }}</p>
        </div>
        <button class="salvar-btn" type="submit" @click="validadorDadosNovoPortal">Salvar</button>
        <button class="cancelar-btn" @click.prevent="cancelarEdicao">Cancelar</button>

        <div v-if="abrirModal" class="modal-overlay">
          <div class="modal-content">
            <h3>Selecione as Tags</h3>
            <div v-for="tag in tags" :key="tag.tagId">
              <input class="modal-input" type="checkbox" :id="tag.tagId" v-model="tagsSelecionadas[tag.tagId]">
              <label :for="tag.tagId">{{ tag.tagNome }}</label>
            </div>
            <button @click="salvarTags" class="botao-salvar-multiplas-tags">Salvar</button>
            <button @click="fecharModal" class="botao-cancelar-multiplas-tags">Cancelar</button>
          </div>
        </div>

      </form>
    </div>
  </div>
</template>

<script>
import '@/assets/base.css';
export default {
  name: 'modalEdicaoPortal',
  emits: ['close', 'save'],
  props: {
    portal: Object,
    modalAberto: Boolean,
    tags: Array
  },
  data() {
    return {
      portalEmEdit: {
        id: this.portal.id,
        nome: this.portal.nome,
        url: this.portal.url,
        ativo: this.portal.ativo,
        frequencia: this.portal.frequencia,
        tags: this.portal.tags
      },
      abrirModal: false,
      tagsSelecionadas: {},
      tagsSelecionadasNomes: '',
    }
  },
  watch: {
    portal(newPortal) {
      this.portalEmEdit = { ...newPortal }
      this.constroeListaTagsSeleciona();
    }
  },
  mounted() {
    this.constroeListaTagsSeleciona();
  },
  methods: {
    constroeListaTagsSeleciona() {
      const tagsArray = Array.isArray(this.portalEmEdit.tags) 
        ? this.portalEmEdit.tags 
        : Object.keys(this.portalEmEdit.tags || {}).map(Number);
      
      this.tags.forEach(tag => {
        this.tagsSelecionadas[tag.tagId] = tagsArray.includes(tag.tagId);
      });
      this.atualizaTagsSelecionadas();
    },
    atualizaTagsSelecionadas() {
      this.tagsSelecionadasNomes = this.tags
        .filter(tag => this.tagsSelecionadas[tag.tagId])
        .map(tag => tag.tagNome)
        .join(', ');
    },
    fecharModal() {
      this.$emit('close');
    },
    saveChanges() {
      this.$emit('save', this.portalEmEdit);
      this.fecharModal();
    },
    salvarTags() {
      const tagsSelecionadasIds = Object.keys(this.tagsSelecionadas)
        .filter(tagId => this.tagsSelecionadas[tagId])
        .map(Number);

      this.portalEmEdit.tags = tagsSelecionadasIds;
      this.abrirModal = false;
      this.atualizaTagsSelecionadas();
    },
    editarPortal() {
      let { id, nome, url, ativo, frequencia, tags } = this.portalEmEdit;
      const urlPattern = /^(https?:\/\/)?([\w-]+(\.[\w-]+)+)([\w.,@?^=%&:/~+#-]*[\w@?^=%&/~+#-])?$/;
      if (url && !/^https?:\/\//i.test(url)) {
        if (/^www\./i.test(url)) {
          url = 'https://' + url;
        } else {
          url = 'https://www.' + url;
        }
      }
      if (urlPattern.test(url)) {
        const portalAtualizado = { id, nome, url, ativo, frequencia, tags };

        fetch(`http://localhost:8080/portais/editar/${this.portalEmEdit.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(portalAtualizado)
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error('Erro ao atualizar API');
            }
            return response.json();
          })
          .then((data) => {
            location.reload()
            this.$emit('portal-registrado', data);
            this.saveChanges();
          })
          .catch((error) => {
            console.error('Erro ao editar API:', error);
          });
      }
    }
  },
}
</script>
