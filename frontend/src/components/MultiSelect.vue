<template>
  <div class="multi-select">
    <multiselect 
      v-model="internalSelectedOptions" 
      :options="options" 
      :multiple="true" 
      :close-on-select="false"
      :clear-on-select="false"
      :preserve-search="true"
      placeholder="Pesquisar e selecionar opções"
      label="name"
      track-by="name"
      :preselect-first="true"
      :searchable="true" 
      @search-change="onSearchChange"
    >
      <!-- Custom template for the selected values (tags) -->
      <template #selection="{ values }">
        <span v-if="values.length" class="selected-tags">
          {{ values.map(option => option.name).join(', ') }}
        </span>
      </template>

      <!-- Custom message when no results are found -->
      <template #noResult>
        <span>Nenhuma opção encontrada</span>
      </template>
    </multiselect>
  </div>
</template>

<script>
import Multiselect from 'vue-multiselect';

export default {
  components: {
    Multiselect,
  },
  props: {
    options: Array,  // Recebe as opções do componente pai (tags ou portais)
    value: Array,    // v-model que sincroniza com o componente pai
  },
  data() {
    return {
      internalSelectedOptions: this.value || [],  // Armazena as opções selecionadas internamente
      searchQuery: '',  // Armazena a pesquisa
    };
  },
  methods: {
    // Método para atualizar a pesquisa
    onSearchChange(searchQuery) {
      this.searchQuery = searchQuery;
    },
  },
  watch: {
    internalSelectedOptions(val) {
      this.$emit('input', val);  // Emite o valor atualizado de volta para o componente pai
    },
    value(newVal) {
      this.internalSelectedOptions = newVal;  // Sincroniza as opções selecionadas com o valor atualizado do componente pai
    },
  },
};
</script>

<style scoped>
.multi-select {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.multiselect {
  width: 100%;
  padding: 10px;
  border: 1px solid #D9D9D9;
  border-radius: 8px;
}

.selected-tags {
  background-color: #ECE6F0;
  padding: 5px 10px;
  margin: 2px;
  border-radius: 12px;
  display: inline-block;
}

.multiselect__content {
  max-height: 200px; 
  overflow-y: auto;  
}

.multiselect__tag {
  margin-right: 8px;
  padding: 5px 10px;
  background-color: #ECE6F0;
  border-radius: 4px;
  font-size: 14px;
  display: inline-flex;
  align-items: center;
}

.multiselect__tag-icon {
  margin-left: 5px;
  cursor: pointer;
}

.multiselect__option--highlight {
  background-color: #D9D9D9;
}
</style>
