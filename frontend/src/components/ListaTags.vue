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
export default {
  props: {
    tags: Array, 
  },
  methods: {
    editarTag(tag) {
      this.$emit('editar-tag', tag);
    }
  }
}
</script>

<style scoped>
.tag-item {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #f5f5f5;
  padding: 15px;
  margin-top: 10px;
  border-radius: 8px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.editar-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: #2c2c2c;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
}

.editar-btn:hover {
  background-color: #45a049;
}

.tag-info h3 {
  margin: 0;
  font-size: 1.2rem;
}

.tag-info p {
  margin: 5px 0;
  font-size: 0.9rem;
}
</style>
