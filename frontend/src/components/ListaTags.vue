<template>
  <div>
    <h2>Tags Cadastradas</h2>
    <div v-if="tags.length > 0">
      <div v-for="tag in tags" :key="tag.id || tag.tagId" class="tag-item">
        <div class="tag-info">
          <h3>{{ tag.tagNome }}</h3> 
          <p>{{ tag.tagDescricao }}</p>
          <p><strong>Ativo:</strong> {{ tag.tagActive ? 'Sim' : 'Não' }}</p> 
        </div>
        <button @click="editarTag(tag)" class="editar-btn">Editar</button>
      </div>
    </div>
    <p v-else>Nenhuma tag cadastrada.</p>
  </div>
</template>

<script>
import '@/assets/base.css';
export default {
  props: {
    tags: Array, // Recebe a lista de tags via prop
  },
  methods: {
    // Emite evento para iniciar a edição de uma tag
    editarTag(tag) {
      this.$emit('editar-tag', tag);
    }
  }
}
</script>