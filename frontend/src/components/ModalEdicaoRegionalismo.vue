<template>
  <div v-if="show" class="modal-overlay">
    <div class="modal-content">
      <h2 class="modal-title">Editando Regionalismo:</h2>
      <form @submit.prevent="salvarEdicao" class="modal-form">
        <label for="regionalismoNome">Regionalismo:</label>
        <input
          v-model="regionalismoEdit.nome"
          id="regionalismoNome"
          type="text"
          class="modal-input"
          required
          placeholder="Digite o nome do regionalismo"
        />

        <label for="tagRelacionada">Tag relacionada:</label>
        <select v-model="regionalismoEdit.tagId" id="tagRelacionada" class="modal-select">
          <option v-for="tag in tags" :key="tag.tagId" :value="tag.tagId">
            {{ tag.tagNome }}
          </option>
        </select>

        <div class="modal-actions">
          <button type="submit" class="salvar-btn">Salvar</button>
          <button type="button" class="cancelar-btn" @click="$emit('fechar')">Cancelar</button>
        </div>
      </form>
    </div>
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
    show: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      regionalismoEdit: { ...this.regionalismo },
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
      if (!this.regionalismoEdit.nome || this.regionalismoEdit.nome.trim() === '') {
        alert('O campo Nome do regionalismo é obrigatório.');
        return;
      }
      try {
        await axios.put(
          `http://localhost:8080/regionalismos/editar/${this.regionalismoEdit.id}`,
          {
            nome: this.regionalismoEdit.nome,
            tagId: this.regionalismoEdit.tagId,
          }
        );
        this.$emit('salvar-edicao', this.regionalismoEdit);
        this.fechar();
      } catch (error) {
        console.error('Erro ao salvar edição:', error);
        alert('Erro ao salvar a edição. Por favor, tente novamente.');
      }
    },
    fechar() {
      this.$emit('fechar');
    },
  },
  watch: {
    regionalismo(newValue) {
      this.regionalismoEdit = { ...newValue };
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.modal-content {
  background: white;
  padding: 40px;
  border-radius: 10px;
  width: 60%;
  max-width: 800px;
  height: auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  position: relative;
  display: flex;
  flex-direction: column;
}

.modal-title {
  color: #4a148c;
  font-size: 26px;
  margin-bottom: 20px;
}

.modal-form {
  display: flex;
  flex-direction: column;
}

.modal-input, .modal-select {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #f3e5f5;
}

.modal-actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
  gap: 15px;
}

.salvar-btn {
  background-color: #6a1b9a;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.salvar-btn:hover {
  background-color: #4a148c;
}

.cancelar-btn {
  background-color: transparent;
  color: #6a1b9a;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
  border-radius: 5px;
  border: 2px solid black;
}

.cancelar-btn:hover {
  text-decoration: underline;
}
</style>
