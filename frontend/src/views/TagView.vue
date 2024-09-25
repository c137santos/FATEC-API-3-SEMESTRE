<template>
  <div>
    <!-- Componente para cadastrar uma nova tag -->
    <AdicionarTag @nova-tag="adicionarTag" />

    <!-- Componente para listar as tags -->
    <ListaTags :tags="tags" />
  </div>
</template>

<script>
import AdicionarTag from '@/components/AdicionarTag.vue';
import ListaTags from '@/components/ListaTags.vue';

export default {
  name: 'TagView',
  components: {
    AdicionarTag,
    ListaTags
  },
  data() {
    return {
      tags: []  // Armazena a lista de tags
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
    }
  }
}
</script>
