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
    <section v-if="tags.length > 0" class="tags-cadastradas">
      <h2>Tags cadastradas</h2>
      <div v-for="tag in tags" :key="tag.id" class="tag-item">
        <div class="tag-info">
          <h3>{{ tag.nome }}</h3>
          <p>{{ tag.descricao }}</p>
          <p><strong>Tags relacionadas:</strong> {{ tag.tagsRelacionadas.join(', ') }}</p>
        </div>
        <button @click="abrirModal(tag)">Editar</button>
      </div>
    </section>

    <!-- Componente de Modal -->
    <Modal v-if="isModalOpen" :tag="tagSelecionada" @fechar="fecharModal" />
  </div>
</template>

<script>
import Modal from './components/Modal.vue';  // Certifique-se de que o caminho está correto

export default {
  components: {
    Modal,
  },
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
      tags: [], // Armazenará as tags vindas do banco de dados
      isModalOpen: false,  // Estado para controlar a abertura do modal
      tagSelecionada: null // Armazena a tag que está sendo editada
    };
  },
  mounted() {
    this.carregarTags();
  },
  methods: {
    salvarTag() {
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
    abrirModal(tag) {
      this.tagSelecionada = tag; // Armazena a tag que será editada
      this.isModalOpen = true;   // Abre o modal
    },
    fecharModal() {
      this.isModalOpen = false;  // Fecha o modal
      this.tagSelecionada = null; // Reseta a tag selecionada
    },
    async carregarTags() {
      try {
        // Simulação de uma chamada a uma API para buscar as tags do banco
        const response = await fetch('/api/tags'); // Substitua pela sua rota de API real
        const tagsDoBanco = await response.json();
        this.tags = tagsDoBanco;
      } catch (error) {
        console.error('Erro ao carregar tags:', error);
      }
    }
  }
};
</script>

<style scoped>
/* Seu CSS permanece o mesmo */
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
