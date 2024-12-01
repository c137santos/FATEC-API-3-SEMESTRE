<template>
  <div class="tags-cadastradas">
    <div class="Nova-Tag">
      <button class="cadastrar-tag-nova" @click="toggleNovaTagForm">Cadastrar Tag</button>

      <div v-if="exibirNovaTagForm" class="formulario-cadastro">
        <form @submit.prevent="validadorDadosNovaTag">
          <img class="imagem_logo" src="@/assets/Logo_padrao.jpeg" />
          <input
            class="Campo_nome_cadastro"
            type="text"
            v-model="novaTag.tagNome"
            placeholder="Nome da tag"
          />
          <input
            class="Campo_descricao_cadastro"
            type="text"
            v-model="novaTag.tagDescricao"
            placeholder="Descrição da tag"
          />
          <div>
            <label for="checkbox-cadastro"> Ativo</label>
            <input
              class="checkAtivo"
              type="checkbox"
              id="checkbox-cadastro"
              v-model="novaTag.tagActive"
            />
          </div>
          <button class="botao-salvar" type="submit">Salvar</button>
          <button class="botao-cancelar" @click.prevent="cancelarCadastro">Cancelar</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import '@/assets/base.css';
export default {
  data() {
    return {
      tags: [],
      novaTag: { tagNome: '', tagDescricao: '', tagActive: true },
      exibirNovaTagForm: false,
      selectedTag: ''
    }
  },

  methods: {
    toggleNovaTagForm() {
      this.exibirNovaTagForm = !this.exibirNovaTagForm
    },

    async salvarTag() {
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.novaTag)
      }
      const response = await fetch('http://localhost:8080/tags/cadastrar', requestOptions)
      
      const data = await response.json()
      this.$emit('nova-tag', data)
    },

    validadorDadosNovaTag() {
      if (this.novaTag.tagNome && this.novaTag.tagDescricao) {
        this.salvarTag()
        let tagSerSalva = { ...this.novaTag }
        this.novaTag.tagDescricao = ''
        this.novaTag.tagNome = ''
        this.novaTag.tagActive = true
        this.exibirNovaTagForm = false
      }
    },

    cancelarCadastro() {
      this.novaTag = { tagNome: '', tagActive: true, tagDescricao: '' }
      this.exibirNovaTagForm = false
    }
  }
}
</script>