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
import '@/assets/base.css';
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
      this.keyword = newVal; // Atualiza o campo caso a prop initialKeyword seja alterada
    },
  },
};
</script>
