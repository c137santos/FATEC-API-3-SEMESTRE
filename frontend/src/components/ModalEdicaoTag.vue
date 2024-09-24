<template>
  <div class="modal-overlay" @click.self="fechar">
    <div class="modal-content">
      <h2>Editar Tag</h2>
      <form @submit.prevent="salvarEdicao">
        <label for="nome">Nome:</label>
        <input id="nome" v-model="tagEditada.nome" type="text" required />

        <label for="descricao">Descrição:</label>
        <input id="descricao" v-model="tagEditada.descricao" type="text" required />

        <label for="ativo">Ativo:</label>
        <input id="ativo" type="checkbox" v-model="tagEditada.ativo" />

        <button type="submit">Salvar</button>
        <button @click.prevent="fechar">Cancelar</button>
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
      tagEditada: { ...this.tag }
    };
  },
  methods: {
    salvarEdicao() {
      this.$emit('salvar-edicao', this.tagEditada);
      this.fechar();
    },
    fechar() {
      this.$emit('fechar');
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
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  text-align: center;
}
</style>
