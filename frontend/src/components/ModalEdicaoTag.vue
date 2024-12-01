<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2 class="modal-title">Editando Tag:</h2>
      <form @submit.prevent="salvarEdicao" class="modal-form">
        <label for="nome">Nome da tag</label>
        <input
          type="text"
          id="nome"
          v-model="tagLocal.tagNome"
          class="modal-input"
          required
          placeholder="Digite o nome da tag"
        />

        <label for="descricao">Descrição</label>
        <input
          type="text"
          id="descricao"
          v-model="tagLocal.tagDescricao"
          class="modal-input"
          placeholder="Digite a descrição da tag"
        />

        <div>
          <label for="checkbox-edicao">Ativo</label>
          <input
            type="checkbox"
            id="checkbox-edicao"
            v-model="tagLocal.tagActive"
            class="checkboxEdicao"
          />
        </div>

        <div class="modal-actions">
          <button type="submit" class="salvar-btn">Salvar</button>
          <button type="button" class="cancelar-btn" @click="$emit('fechar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import '@/assets/base.css';
export default {
  props: {
    tag: Object
  },
  data() {
    return {
      tagLocal: { ...this.tag }
    }
  },
  methods: {
    salvarEdicao() {
      if (!this.tagLocal.tagNome || this.tagLocal.tagNome.trim() === '') {
        alert('O campo Nome da tag é obrigatório.')
        return
      }

      this.$emit('salvar-edicao', this.tagLocal)
    }
  },
  watch: {
    tag: {
      immediate: true,
      handler(newTag) {
        this.tagLocal = { ...newTag }
      }
    }
  }
}
</script>