<template>
  <div>
    <!-- Componente para cadastrar uma nova tag -->
    <AdicionarTag @nova-tag="adicionarTag" />

    <!-- Componente para listar as tags -->
    <ListaTags :tags="tags" @editar-tag="iniciarEdicao" />

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
        if (!response.ok) {
          throw new Error('Falha ao carregar tags do backend');
        }
        const tags = await response.json();
        console.log("Tags carregadas (JSON):", JSON.stringify(tags)); // Verifica se o backend está enviando `tagId`
        this.tags = tags;  // Armazena as tags carregadas no array `tags`
      } catch (error) {
        console.error('Erro ao carregar tags:', error);
        alert('Erro ao carregar as tags. Tente novamente mais tarde.');
      }
    },

    // Adiciona uma nova tag à lista sem recarregar a página
    adicionarTag(novaTag) {
      this.tags.push(novaTag);
    },

    // Inicia a edição de uma tag
    iniciarEdicao(tag) {
      const plainTag = JSON.parse(JSON.stringify(tag)); // Converte o Proxy para um objeto plano para inspecionar
      if (!plainTag.tagId) {
        console.error("Tag selecionada não tem ID:", plainTag);
      } else {
        console.log("Tag selecionada para edição:", plainTag);
      }
      this.tagEmEdicao = { ...plainTag };
      console.log("Tag em edição:", this.tagEmEdicao);  // Confirma se `tagEmEdicao` tem o `tagId`
    },

    // Salva as alterações feitas na tag e envia ao backend
    async salvarEdicao(tagEditada) {
      try {
        const tagId = tagEditada.tagId;
        if (!tagId) {
          console.error("Tag ID não definido!", tagEditada);  // Log para verificar o conteúdo de tagEditada
          alert("ID da tag não encontrado.");
          return;
        }

        const response = await fetch(`http://localhost:8080/tags/editar/${tagId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(tagEditada)
        });

        if (response.ok) {
          const tagAtualizada = await response.json();
          console.log('Tag salva com sucesso:', tagAtualizada);

          const index = this.tags.findIndex(tag => tag.tagId === tagAtualizada.tagId);
          if (index !== -1) {
            this.tags.splice(index, 1, tagAtualizada);  // Atualiza a tag na lista
          }
          this.fecharModal();
        } else {
          alert('Erro ao salvar a tag. Tente novamente.');
          console.error('Erro ao salvar a tag:', response.statusText);
        }
      } catch (error) {
        alert('Erro ao salvar a tag. Tente novamente.');
        console.error('Erro ao salvar a tag:', error);
      }
    },

    // Fecha o modal de edição
    fecharModal() {
      this.tagEmEdicao = null;
    }
  }
}
</script>