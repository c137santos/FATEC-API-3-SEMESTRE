<template>
  <div class="modal">
    <h2>Editando Regionalismo:</h2>
    <label for="regionalismoNome">Regionalismo:</label>
    <input v-model="regionalismo.nome" id="regionalismoNome" type="text" />

    <label for="tagRelacionada">Tag relacionada:</label>
    <select v-model="regionalismo.tagId" id="tagRelacionada">
      <option v-for="tag in tags" :key="tag.tagId" :value="tag.tagId">
        {{ tag.tagNome }}
      </option>
    </select>

    <button @click="salvarEdicao">Salvar</button>
    <button @click="fechar">Cancelar</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: ['regionalismo'],
  data() {
    return {
      tags: [] // Lista de tags disponíveis para seleção
    };
  },
  mounted() {
    this.carregarTags(); // Carrega as tags ao abrir o modal
  },
  methods: {
    async carregarTags() {
      try {
        const response = await axios.get('http://localhost:8080/tags/listar');
        this.tags = response.data; // Armazena as tags carregadas
      } catch (error) {
        console.error("Erro ao carregar tags:", error);
      }
    },
    salvarEdicao() {
      // Emite o evento 'salvar-edicao' passando o regionalismo atualizado
      this.$emit('salvar-edicao', this.regionalismo);
    },
    fechar() {
      // Emite o evento 'fechar' para fechar o modal
      this.$emit('fechar');
    }
  }
};
</script>

<style scoped>
.modal {
  /* Estilos para o modal */
  background: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>
