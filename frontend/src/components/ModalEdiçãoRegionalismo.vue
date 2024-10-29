<template>
  <div class="modal">
    <h2>Editando Regionalismo:</h2>
    <label for="regionalismoNome">Regionalismo:</label>
    <input v-model="regionalismoEdit.nome" id="regionalismoNome" type="text" />

    <label for="tagRelacionada">Tag relacionada:</label>
    <select v-model="regionalismoEdit.tagId" id="tagRelacionada">
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
  props: {
    regionalismo: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      regionalismoEdit: { ...this.regionalismo }, // Cópia do objeto para edição
      tags: [],
    };
  },
  mounted() {
    this.carregarTags();
  },
  methods: {
    async carregarTags() {
      try {
        const response = await axios.get('http://localhost:8080/tags/listar');
        this.tags = response.data;
      } catch (error) {
        console.error('Erro ao carregar tags:', error);
      }
    },
    async salvarEdicao() {
      try {
        await axios.put(
          `http://localhost:8080/regionalismos/editar/${this.regionalismoEdit.id}`,
          {
            nome: this.regionalismoEdit.nome,
            tagId: this.regionalismoEdit.tagId,
          }
        );
        this.$emit('salvar-edicao', this.regionalismoEdit); // Emite o evento para o componente pai
      } catch (error) {
        console.error('Erro ao salvar edição:', error);
      }
    },
    fechar() {
      this.$emit('fechar');
    },
  },
  watch: {
    regionalismo(newValue) {
      this.regionalismoEdit = { ...newValue }; // Atualiza o modal ao abrir com novo regionalismo
    },
  },
};
</script>
