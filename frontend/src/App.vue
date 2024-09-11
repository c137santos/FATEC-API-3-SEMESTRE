<template>
  <div class="tags-cadastradas">
    <h2>Tags Cadastradas</h2>

    <!-- Exibe cada tag em uma caixinha -->
    <div v-for="(tag, index) in tags" :key="index" class="tag-item">
      <div class="tag-info" v-if="!tag.isEditing">
        <i class="fas fa-info-circle"></i>
        <h3>{{ tag.name }}</h3>
        <p>Descrição: {{ tag.description }}</p>
      </div>

      <!-- Formulário e botões de edição aparecem quando tag.isEditing for true -->
      <div v-if="tag.isEditing" class="tag-edit-form">
        <form @submit.prevent="salvarTag(tag)">
          <label class="Titulo_nome" for="nome">Nome da tag</label>
          <input class="Campo_nome" type="text" id="nome" v-model="tag.name">
          
          <label class="Titulo_descricao" for="descricao">Descrição</label>
          <input class="Campo_descricao" type="text" id="descricao" v-model="tag.description">
          
          <label for="tags">Tags Relacionadas</label>
          <select class="Campo_tags_relacionadas" v-model="selectedTag">
            <option v-for="tagOption in tags" :key="tagOption.name" :value="tagOption.name">{{ tagOption.name }}</option>
          </select>
          
          <button class="botao-salvar" type="submit">Salvar</button>
          <button class="botao-cancelar" @click.prevent="cancelarEdicao(tag)">Cancelar</button>
        </form>
      </div>

      <!-- Botão Editar, mostra o formulário ao ser clicado -->
      <button v-if="!tag.isEditing" class="edit-tag" @click="editTag(tag)">Editar</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tags: [
        {
          name: 'Tag 1',
          description: 'Descrição da Tag 1',
          isEditing: false
        },
        {
          name: 'Tag 2',
          description: 'Descrição da Tag 2',
          isEditing: false
        }
      ],
      selectedTag: 'Tag 1'
    };
  },
  
  methods: {
    // Inicia o modo de edição para a tag clicada
    editTag(tag) {
      this.cancelAllEdits(); // Cancela qualquer outra edição em andamento
      tag.isEditing = true;
    },

    // Salva a edição da tag e encerra o modo de edição
    salvarTag(tag) {
      tag.isEditing = false;
      console.log("Tag salva:", tag);
    },

    // Cancela a edição da tag e retorna ao modo visual
    cancelarEdicao(tag) {
      tag.isEditing = false;
      console.log("Edição cancelada:", tag);
    },

    // Cancela qualquer edição em andamento
    cancelAllEdits() {
      this.tags.forEach(tag => tag.isEditing = false);
    }
  }
};

</script>

<style scoped>
/* Estilos para o título das tags cadastradas */
h2 {
  margin: 0;
  padding: 10px 0;
  height: 52px;
}

/* Estilos gerais para a seção de tags cadastradas */
.tags-cadastradas {
  margin: 20px auto;
  max-width: 1000px;
}

/* Estilo para cada item de tag */
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

/* Estilo para a seção de informações da tag */
.tag-info {
  margin-bottom: 10px;
}

/* Estilo para o botão de editar */
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

/* Estilo para o formulário de edição da tag */
.tag-edit-form {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
}

/* Estilo para o rótulo do campo de nome e descrição */
.Titulo_nome, .Titulo_descricao {
  margin-top: 10px;
  font-weight: bold;
}

/* Estilo para os campos de entrada */
.Campo_nome, .Campo_descricao {
  display: block;
  width: 900px;
  padding: 8px;
  margin-bottom: 15px;
  border-radius: 5px;
  background-color: #e4ceff;
}

/* Estilo específico para o campo de seleção de tags relacionadas */
.Campo_tags_relacionadas {
  display: block;
  width: 20%;
  padding: 8px;
  margin-bottom: 15px;
  border-radius: 5px;
  background-color: white;
}

/* Estilo para os botões de salvar e cancelar */
.botao-salvar, .botao-cancelar {
  padding: 8px 16px;
  margin-top: 10px;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

/* Estilo específico para o botão de salvar */
.botao-salvar {
  background-color: mediumpurple;
}

/* Estilo específico para o botão de cancelar */
.botao-cancelar {
  background-color: black;
}
</style>
