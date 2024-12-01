<template>
  <div class="search-bar">
    <i class="search-icon"></i>
    <input
      class="search-input"
      type="text"
      v-model="keyword"
      @input="emitSearch"
      placeholder="Buscar por palavras-chave..."
    />
  </div>
</template>

<script>
export default {
  props: {
    initialKeyword: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      keyword: this.initialKeyword, // Inicializa com a palavra-chave passada via props
    };
  },
  methods: {
    emitSearch() {
      this.$emit("search", this.keyword); // Emite o evento com o valor atualizado
    },
  },
  watch: {
    keyword(newVal) {
      this.emitSearch(); // Monitora alterações na palavra-chave para disparar automaticamente
    },
    initialKeyword(newVal) {
      this.keyword = newVal; // Atualiza o campo caso a prop inicialKeyword seja alterada
    },
  },
};
</script>

<style scoped>
.search-bar {
  display: flex;
  align-items: center;
  padding: 0 16px;
  background-color: #ECE6F0;
  border-radius: 28px;
  height: 56px;
  width: 100%;
}

.search-icon {
  content: url('@/icons/logo.png'); 
  margin-right: 12px;
  width: 24px;
  height: 24px;
}

.search-input {
  border: none;
  outline: none;
  flex-grow: 1;
  background-color: transparent;
  font-size: 16px;
  line-height: 24px;
  color: #49454F;
}
</style>
