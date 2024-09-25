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

        <label for="relacionadas">Tags relacionadas</label>
        <select id="relacionadas" v-model="tagLocal.tagRelacionada" class="modal-select">
          <option value="agricultura">Agricultura</option>
          <option value="política">Política</option>
          <!-- Adicionar outras opções conforme necessário -->
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
export default {
  props: {
    tag: Object
  },
  data() {
    return {
      tagLocal: { ...this.tag }  // Cria uma cópia do objeto tag
    };
  },
  methods: {
    salvarEdicao() {
      // Validação simples para garantir que o nome da tag não esteja vazio
      if (!this.tagLocal.tagNome || this.tagLocal.tagNome.trim() === "") {
        alert("O campo Nome da tag é obrigatório.");
        return;
      }

      // Emitir o evento de salvar com a tag atualizada (tagLocal)
      this.$emit('salvar-edicao', this.tagLocal);
    }
  },
  watch: {
    tag: {
      immediate: true,
      handler(newTag) {
        this.tagLocal = { ...newTag };  // Atualiza a cópia local quando a tag mudar
      }
    }
  }
}
</script>

<style scoped>
/* Estilo para o fundo opaco */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* Fundo semi-transparente */
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
  color: #4A148C; /* Cor roxa */
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
  border: 1px solid #D1C4E9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #F3E5F5; /* Fundo suave lilás */
}

/* Select para tags relacionadas */
.modal-select {
  border: 1px solid #D1C4E9;
  border-radius: 5px;
  padding: 12px;
  margin-bottom: 20px;
  font-size: 16px;
  background-color: #F3E5F5;
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
  background-color: #6A1B9A; /* Roxo */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.salvar-btn:hover {
  background-color: #4A148C; /* Tom mais escuro de roxo */
}

/* Botão de cancelar */
.cancelar-btn {
  background-color: transparent;
  color: #6A1B9A;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  font-size: 16px;
}

.cancelar-btn:hover {
  text-decoration: underline;
}
</style>
