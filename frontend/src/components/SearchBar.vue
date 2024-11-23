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
  data() {
    return {
      keyword: ''
    };
  },
  methods: {
    emitSearch() {
      this.$emit('search', this.keyword);
    }
  },
  watch: {
    keyword(newVal) {
      this.emitSearch();
    }
  }
};
</script>