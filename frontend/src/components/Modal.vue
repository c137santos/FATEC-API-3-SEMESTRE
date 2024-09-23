<template>
  <div class="modal-overlay" @click.self="fechar">
    <div class="modal-content">
      <h2>Editar Tag</h2>
      <p><strong>Nome:</strong> {{ tag.nome }}</p>
      <p><strong>Descrição:</strong> {{ tag.descricao }}</p>
      <button @click="fechar">Fechar</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    tag: Object,  // A tag selecionada passada pelo App.vue
  },
  methods: {
    fechar() {
      this.$emit('fechar'); // Emite o evento fechar para o App.vue
    }
  }
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
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  text-align: center;
}
</style>
