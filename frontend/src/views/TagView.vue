<template>
  <div>
    <!-- Componente para cadastrar uma nova tag -->
    <AdicionarTag @nova-tag="adicionarTag" />

    <!-- Componente para listar as tags -->
    <ListaTags :tags="tags" @editar-tag="iniciarEdicao" @excluir-tag="excluirTag" />

    <!-- Modal para editar uma tag selecionada -->
    <ModalEdicaoTag v-if="tagEmEdicao" :tag="tagEmEdicao" @salvar-edicao="salvarEdicao" @fechar="fecharModal" />
  </div>
</template>

<script>
import AdicionarTag from '@/components/AdicionarTag.vue';
import ListaTags from '@/components/ListaTags.vue';
import ModalEdicaoTag from '@/components/ModalEdicaoTag.vue';

export default {
  name: 'TagView',
  components: {
    AdicionarTag,
    ListaTags,
    ModalEdicaoTag
  },
  data() {
    return {
      tags: [],  // Armazena a lista de tags
      tagEmEdicao: null  // Armazena a tag que está sendo editada
    };
  },
  mounted() {
    this.carregarTags();  // Carrega as tags do backend assim que o componente é montado
  },
  methods: {
    // Função para carregar as tags do backend
    async carregarTags() {
      try {
        const response = await fetch('http://localhost:8080/tags/listar');
        const tags = await response.json();
        this.tags = tags;  // Armazena as tags carregadas no array `tags`
      } catch (error) {
        console.error('Erro ao carregar tags:', error);
      }
    },

    // Adiciona uma nova tag à lista sem recarregar a página
    adicionarTag(novaTag) {
      this.tags.push(novaTag);
    },

    // Inicia a edição de uma tag
    iniciarEdicao(tag) {
      this.tagEmEdicao = { ...tag };  // Copia a tag para edição
    },

    // Salva as alterações feitas na tag
    salvarEdicao(tagEditada) {
      const index = this.tags.findIndex(tag => tag.id === tagEditada.id);
      if (index !== -1) {
        this.tags.splice(index, 1, tagEditada);  // Atualiza a tag na lista
      }
      this.fecharModal();  // Fecha o modal após salvar
    },

    // Remove uma tag da lista
    excluirTag(tagId) {
      this.tags = this.tags.filter(tag => tag.id !== tagId);  // Remove a tag pela ID
    },

    // Fecha o modal de edição
    fecharModal() {
      this.tagEmEdicao = null;
    }
  }
}
</script>
