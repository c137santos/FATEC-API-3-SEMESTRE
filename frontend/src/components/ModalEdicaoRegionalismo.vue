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
import '@/assets/base.css';

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

    const salvarEdicao = () => {
  console.log("Dados para salvar:", regionalismoLocal.value); 
  if (!regionalismoLocal.value.nome || regionalismoLocal.value.nome.trim() === '') {
    alert('O campo Nome do regionalismo é obrigatório.');
    return;
  }
  emit('salvar-edicao', regionalismoLocal.value);
};


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
