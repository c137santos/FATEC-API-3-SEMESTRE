<template>
    <div class="modal">
      <h2>Editando Regionalismo:</h2>
      <label for="regionalismoNome">Regionalismo:</label>
      <input v-model="regionalismo.nome" id="regionalismoNome" type="text" />
  
      <label for="tagRelacionada">Tag relacionada:</label>
      <select v-model="regionalismo.tag_id" id="tagRelacionada">
        <option v-for="tag in tags" :key="tag.tag_id" :value="tag.tag_id">
          {{ tag.tag_nome }}
        </option>
      </select>
  
      <button @click="salvarEdicao">Salvar</button>
      <button @click="fechar">Cancelar</button>
    </div>
  </template>
  
  <script>
  export default {
    props: ['regionalismo'],
    data() {
      return {
        tags: []
      }
    },
    mounted() {
      this.carregarTags()
    },
    methods: {
      carregarTags() {
        
      },
      salvarEdicao() {
        this.$emit('salvar-edicao', this.regionalismo)
      },
      fechar() {
        this.$emit('fechar')
      }
    }
  }
  </script>
  
