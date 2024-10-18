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
        this.tagLocal = { ...newTag } // Atualiza a cópia local quando a tag mudar
      }
    }
  }
}
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

.modal-input {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #f3e5f5;
}

.checkboxEdicao {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 6px;
  margin-left: 10px;
  font-size: 16px;
  background-color: #f3e5f5;
}

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
