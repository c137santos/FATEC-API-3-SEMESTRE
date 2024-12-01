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

<style scoped>
/* Título das tags cadastradas */
h2 {
  margin: 0;
  padding: 10px 0;
  height: 52px;
}

/* Container geral para as tags e o botão cadastrar */
.container-tags {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 40px;
}

/* Cada item de tag */
.tag-item {
  width: 1000px;
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  position: relative;
}

/* Checkbox do Ativo da tag */
.checkbox {
  margin-top: 20px;
}

/* Imagem que é o ícone do lado das tags */
.imagem_logo {
  width: 25px;
  margin-right: 35px;
}

/* Seção de informações da tag */
.tag-info {
  margin-bottom: 10px;
}

/* Campos de entrada para o cadastro de nova Tag */
.Campo_nome_cadastro,
.Campo_descricao_cadastro {
  display: block;
  width: 600px;
  padding: 8px;
  margin-bottom: 15px;
  border-radius: 5px;
  background-color: #e4ceff;
}

/* Formulário de cadastro de nova tag */
.formulario-cadastro {
  width: 700px;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  margin-top: 20px;
  background-color: #f9f9f9;
}

/* Botões de salvar, cancelar e cadastrar */
.botao-salvar,
.botao-cancelar,
.cadastrar-tag-nova {
  padding: 8px 16px;
  margin-top: 10px;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

/* Botão cadastrar nova tag */
.cadastrar-tag-nova {
  background-color: black;
  margin-bottom: 20px;
}

/* Botão salvar */
.botao-salvar {
  background-color: rgb(141, 107, 207);
}

/* Botão cancelar */
.botao-cancelar {
  color: mediumpurple;
  border: none;
  background-color: white;
}

/* Ajuste de layout para a seção de tags cadastradas */
.tags-cadastradas {
  margin-top: 10px;
  width: 100%;
}
</style>
