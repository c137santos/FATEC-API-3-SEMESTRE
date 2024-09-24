<template>
  <div>
    <h2>Tags Cadastradas</h2>
    <div v-if="tags.length > 0">
      <div v-for="tag in tags" :key="tag.id" class="tag-item">
        <div class="tag-info">
          <h3>{{ tag.tagNome }}</h3> <!-- Ajustar para `tagNome` -->
          <p>{{ tag.tagDescricao }}</p> <!-- Ajustar para `tagDescricao` -->
          <p><strong>Ativo:</strong> {{ tag.tagActive ? 'Sim' : 'Não' }}</p> <!-- Ajustar para `tagActive` -->
        </div>
        <button @click="editarTag(tag)">Editar</button>
        <button @click="excluirTag(tag.id)">Excluir</button>
      </div>
    </div>
    <p v-else>Nenhuma tag cadastrada.</p>
  </div>
</template>


<script>
export default {
  props: {
    tags: Array,
  },
  methods: {
    editarTag(tag) {
      this.$emit('editar-tag', tag);
    },
    excluirTag(tagId) {
      this.$emit('excluir-tag', tagId);
    }
  }
}
</script>

<style scoped>
/* Estilos para a lista de tags */
.tag-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f5f5f5;
  padding: 10px;
  margin-top: 10px;
  border-radius: 5px;
}
</style>
