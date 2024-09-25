<template>
  <div>
    <AdicionarTag @nova-tag="adicionarTag" />
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
      tags: [] 
    };
  },
  mounted() {
    this.carregarTags();
  },
  methods: {
    async carregarTags() {
      try {
        const response = await fetch('http://localhost:8080/tags/listar');
        const tags = await response.json();
        this.tags = tags; 
      } catch (error) {
        console.error('Erro ao carregar tags:', error);
      }
    },

    adicionarTag(novaTag) {
      this.tags.push(novaTag);
    }
  }
}
</script>
