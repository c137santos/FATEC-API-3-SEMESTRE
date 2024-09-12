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
  content: url('/path/to/lupa-icon.svg');
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
