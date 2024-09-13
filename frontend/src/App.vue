<template>
  <div class="container">
    <h1>Cérberus</h1>

    <!-- Seção de Cadastro de Nova Tag -->
    <section class="cadastrar-tag">
      <h2>Cadastrar nova Tag</h2>
      <div class="form-group">
        <label for="nome-tag">Nome da tag</label>
        <input type="text" v-model="novaTag.nome" id="nome-tag" placeholder="Digite o nome da tag" />

        <label for="descricao-tag">Descrição</label>
        <input type="text" v-model="novaTag.descricao" id="descricao-tag" placeholder="Digite a descrição" />

        <label for="tags-relacionadas">Tags relacionadas</label>
        <select v-model="novaTag.tagRelacionada" id="tags-relacionadas">
          <option v-for="tag in tagsRelacionadas" :key="tag.id" :value="tag.nome">{{ tag.nome }}</option>
        </select>

        <button @click="salvarTag">Salvar</button>
      </div>
    </section>

    <!-- Seção de Listagem de Tags Cadastradas -->
    <section class="tags-cadastradas">
      <h2>Tags cadastradas</h2>
      <div v-for="tag in tags" :key="tag.id" class="tag-item">
        <div class="tag-info">
          <h3>{{ tag.nome }}</h3>
          <p>{{ tag.descricao }}</p>
          <p><strong>Tags relacionadas:</strong> {{ tag.tagsRelacionadas.join(', ') }}</p>
        </div>
        <button @click="editarTag(tag)">Editar</button>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      novaTag: {
        nome: '',
        descricao: '',
        tagRelacionada: ''
      },
      tagsRelacionadas: [
        { id: 1, nome: 'Agricultura' },
        { id: 2, nome: 'Tecnologia' },
        { id: 3, nome: 'Economia' }
      ],
      tags: [
        {
          id: 1,
          nome: 'Tag 1',
          descricao: 'Descrição da Tag 1',
          tagsRelacionadas: ['Agricultura', 'Tecnologia']
        },
        {
          id: 2,
          nome: 'Tag 2',
          descricao: 'Descrição da Tag 2',
          tagsRelacionadas: ['Economia']
        }
      ]
    };
  },
  methods: {
    salvarTag() {
      // Simulando a criação de uma nova tag e adicionando ao array de tags
      const novaTag = {
        id: this.tags.length + 1,
        nome: this.novaTag.nome,
        descricao: this.novaTag.descricao,
        tagsRelacionadas: [this.novaTag.tagRelacionada]
      };

      this.tags.push(novaTag);

      // Limpar o formulário
      this.novaTag.nome = '';
      this.novaTag.descricao = '';
      this.novaTag.tagRelacionada = '';
    },
    editarTag(tag) {
      // Lógica de edição da tag (aqui você pode abrir um modal ou redirecionar para outra página)
      alert(Editando a tag: ${tag.nome});
    }
  }
};
</script>

<style scoped>
.container {
  width: 80%;
  margin: auto;
  padding: 20px;
}

h1 {
  font-size: 2rem;
  margin-bottom: 20px;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 15px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  margin-top: 10px;
}

input,
select {
  padding: 8px;
  margin-top: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button {
  background-color: #7e57c2;
  color: white;
  border: none;
  padding: 10px;
  margin-top: 15px;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #5e3ea1;
}

.tag-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f5f5f5;
  padding: 10px;
  margin-top: 10px;
  border-radius: 5px;
}

.tag-info h3 {
  margin: 0;
}

.tag-info p {
  margin: 5px 0;
}
</style>
