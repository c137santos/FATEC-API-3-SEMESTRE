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

/* Estilo do conteúdo do modal */
.modal-content {
  background: white;
  padding: 40px; /* Aumentando o padding para mais espaço interno */
  border-radius: 10px;
  width: 60%; /* O modal agora ocupa 60% da largura da tela */
  max-width: 800px; /* Limita o tamanho máximo em telas grandes */
  height: auto; /* Ajusta a altura automaticamente */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  position: relative;
  display: flex;
  flex-direction: column;
}

/* Título */
.modal-title {
  color: #4a148c; /* Cor roxa */
  font-size: 26px; /* Um pouco maior para se destacar */
  margin-bottom: 20px;
}

/* Estilo do formulário */
.modal-form {
  display: flex;
  flex-direction: column;
}

/* Campos de entrada */
.modal-input {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #f3e5f5; /* Fundo suave lilás */
}

.checkboxEdicao {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 6px;
  margin-left: 10px;
  font-size: 16px;
  background-color: #f3e5f5;
}

/* Select para tags relacionadas */
.modal-select {
  border: 1px solid #d1c4e9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #f3e5f5;
}

/* Botões de ação */
.modal-actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
  gap: 15px;
}

/* Botão de salvar */
.salvar-btn {
  background-color: #6a1b9a; /* Roxo */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.salvar-btn:hover {
  background-color: #4a148c; /* Tom mais escuro de roxo */
}

/* Botão de cancelar */
.cancelar-btn {
  background-color: transparent;
  color: #6a1b9a;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  font-size: 16px;
}

.cancelar-btn:hover {
  text-decoration: underline;
}
</style>
