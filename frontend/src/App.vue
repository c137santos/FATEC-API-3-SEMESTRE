<template>
  
  <div class="Nova-Tag">
  <button class="cadastrar-tag-nova" @click="toggleNovaTagForm">Cadastrar Tag</button>

  <div v-if="exibirNovaTagForm" class="formulario-cadastro">
  <form @submit.prevent="salvarNovaTag">
      <img class="imagem" src="c:\Users\yanya\Downloads\WhatsApp Image 2024-09-12 at 21.png">
      <input class="Campo_nome_cadastro" type="text" v-model="novaTag.name" placeholder="Nome da tag">
      <input class="Campo_descricao_cadastro" type="text" v-model="novaTag.description" placeholder="Descrição da tag">
      
      <div>
        <input class="checkAtivo" type="checkbox" id="checkbox-cadastro" v-model="novaTag.ativo">
        <label for="checkbox-edicao"> Ativo</label>
      </div>

      <button class="botao-salvar" type="submit">Salvar</button>
      <button class="botao-cancelar" @click.prevent="cancelarCadastro">Cancelar</button>
  </form>
  </div>
</div>

  <div class="tags-cadastradas">
    <h2>Tags Cadastradas</h2>
    <div v-for="(tag, index) in tags" :key="index" class="tag-item">
      <img class="imagem" src="c:\Users\yanya\Downloads\WhatsApp Image 2024-09-12 at 21.png">
      <div class="tag-info" v-if="!tag.isEditing">
        <i class="fas fa-info-circle"></i>
        <h3>{{ tag.name }}</h3>
        <p>Descrição: {{ tag.description }}</p>
        <p>Data de Criação: {{ tag.dataCriacao }}</p>
      </div>

      <div v-if="tag.isEditing" class="tag-edit-form">
        <form @submit.prevent="salvarTag(tag)">
          <input class="Campo_nome" type="text" id="nome" placeholder="Nome da tag">
          <input class="Campo_descricao" type="text" id="descricao" placeholder="Descrição da tag">
          <div>
            <input class="checkAtivo" type="checkbox" id="checkbox-edicao" v-model="novaTag.ativo">
            <label for="checkbox-cadadstro"> Ativo</label>
          </div>
          <button class="botao-salvar" type="submit">Salvar</button>
          <button class="botao-cancelar" @click.prevent="cancelarEdicao(tag)">Cancelar</button>
        </form>
      </div>

      <button v-if="!tag.isEditing" class="edit-tag" @click="editTag(tag)">Editar</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tags: [
        { name: 'Agricultura', description: 'Notícias sobre agricultura', ativo: true, dataCriacao: '12/09/2024', isEditing: false },
        { name: 'Aviação', description: 'Notícias sobre aviação', ativo: true, dataCriacao: '12/09/2024', isEditing: false },
        { name: 'Tecnologia', description: 'Notícias sobre tecnologia', ativo: false,  dataCriacao: '12/09/2024', isEditing: false }
      ],
      novaTag: {name: '', description: '', dataCriacao: ''},
      exibirNovaTagForm: false,
      selectedTag: ''
    };
  },
  
  methods: {
    // Cadastro das Tags
    toggleNovaTagForm() {
      this.exibirNovaTagForm = !this.exibirNovaTagForm;
    },

    salvarNovaTag() {
      if(this.novaTag.name && this.novaTag.description) {
        const hoje = new Date().toLocaleDateString();
        this.tags.push({...this.novaTag, dataCriacao: hoje, isEditing: false});
        this.novaTag = {name: '', ativo: true, description: ''};
        this.exibirNovaTagForm = false;
      }
    },

    cancelarCadastro() {
      this.novaTag = {name: '', ativo: false, description: ''};
      this.exibirNovaTagForm = false;
    },

    // Edição das Tags
    editTag(tag) {
      this.cancelAllEdits();
      tag.isEditing = true;
    },

    salvarTag(tag) {
      tag.isEditing = false;
      if (!this.tags.includes(tag)) {
        this.tags.push({ ...tag });
      }
      console.log("Tag salva:", tag);
    },

    cancelarEdicao(tag) {
      tag.isEditing = false;
      console.log("Edição cancelada:", tag);
    },

    cancelAllEdits() {
      this.tags.forEach(tag => tag.isEditing = false);
    }
  }
};
</script>


<style scoped>
/* Título das tags cadastradas */
h2 {
  margin: 0;
  padding: 10px 0;
  height: 52px;
}

/* Cada item de tag */
.tag-item {
  margin-left: 40px;
  width: 1000px;
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  position: relative;
}

.checkbox {
  margin-top: 20px;
}

.imagem {
  width: 25px;
  margin-right: 35px;
  
}

/* Seção de informações da tag */
.tag-info {
  margin-bottom: 10px;
}

/* Botão de editar */
.edit-tag {
  position: absolute;
  box-align: center;
  top: 10px;
  right: 10px;
  padding: 10px 15px;
  background-color: black;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
}

/* Formulário de edição da tag */
.tag-edit-form {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
}

/* Campos de entrada */
.Campo_nome, .Campo_descricao {
  display: block;
  width: 900px;
  padding: 8px;
  margin-bottom: 15px;
  border-radius: 5px;
  background-color: #e4ceff;
}

/* Campos de entrada para o cadastro de nova Tag*/
.Campo_nome_cadastro, .Campo_descricao_cadastro {
  display: block;
  width: 600px;
  padding: 8px;
  margin-bottom: 15px;
  border-radius: 5px;
  background-color: #e4ceff;
}

/* Campos de entrada */
.formulario-cadastro {
  width: 700px;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  margin-top: 20px;
  background-color: #f9f9f9;
}


/* Botões de salvar, cancelar e cadastrar */
.botao-salvar, .botao-cancelar, .cadastrar-tag-nova {
  padding: 8px 16px;
  margin-top: 10px;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

/* Botão background do botão cadastrar nova tag */
.cadastrar-tag-nova {
  background-color: black;
}

/* Botão background do botão de salvar */
.botao-salvar {
  background-color: rgb(141, 107, 207);
}

/* Botão background do botão de cancelar */
.botao-cancelar {
  color: mediumpurple;
  border: none;
  background-color: white;
}
</style>