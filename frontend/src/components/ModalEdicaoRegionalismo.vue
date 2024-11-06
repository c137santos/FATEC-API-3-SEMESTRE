<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2 class="modal-title">Editando Regionalismo:</h2>
      <form @submit.prevent="salvarEdicao" class="modal-form">
        <label for="regionalismoNome">Nome do Regionalismo:</label>
        <input
          type="text"
          id="regionalismoNome"
          v-model="regionalismoLocal.nome"
          class="modal-input"
          required
          placeholder="Digite o nome do regionalismo"
        />

        <label for="tagSelect">Tag Relacionada:</label>
        <select
          id="tagSelect"
          v-model="regionalismoLocal.tagId"
          class="modal-select"
        >
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

<script lang="ts">
import { defineComponent, ref, watch, type PropType } from 'vue';

export default defineComponent({
  props: {
    regionalismo: {
      type: Object as PropType<{ id: number; nome: string; tagId: number }>,
      required: true,
    },
    tags: {
      type: Array as PropType<Array<{ tagId: number; tagNome: string }>>,
      required: true,
    },
  },
  emits: ['salvar-edicao', 'fechar'],
  setup(props, { emit }) {
    const regionalismoLocal = ref({ ...props.regionalismo });

    // Método para salvar a edição do regionalismo
    const salvarEdicao = () => {
      if (!regionalismoLocal.value.nome || regionalismoLocal.value.nome.trim() === '') {
        alert('O campo Nome do regionalismo é obrigatório.');
        return;
      }

      emit('salvar-edicao', regionalismoLocal.value);
    };

    // Observa mudanças na prop regionalismo para atualizar a cópia local
    watch(
      () => props.regionalismo,
      (newRegionalismo) => {
        regionalismoLocal.value = { ...newRegionalismo };
      },
      { immediate: true }
    );

    return {
      regionalismoLocal,
      salvarEdicao,
    };
  },
});
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

.modal-input,
.modal-select {
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