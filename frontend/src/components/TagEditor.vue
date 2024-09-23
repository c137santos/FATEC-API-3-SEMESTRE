<template>
  <div>
    <!-- Botão de Editar -->
    <div v-for="(tag, index) in tagsCadastradas" :key="index">
      <div>
        <h3>{{ tag.nome }}</h3>
        <p>{{ tag.descricao }}</p>
        <button @click="openEditModal(tag)">Editar</button>
      </div>
    </div>

    <!-- Modal de Edição -->
    <div v-if="isModalOpen" class="modal">
      <div class="modal-content">
        <h2>Editar Tag: {{ selectedTag.nome }}</h2>
        <p>Descrição: {{ selectedTag.descricao }}</p>

        <h3>Tags Existentes no Banco de Dados:</h3>
        <ul>
          <li v-for="(tag, index) in tagsExistentes" :key="index">{{ tag.nome }}</li>
        </ul>

        <button @click="closeModal">Fechar</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tagsCadastradas: [
        { nome: "Tag 1", descricao: "Descrição da Tag 1" },
        { nome: "Tag 2", descricao: "Descrição da Tag 2" },
      ],
      tagsExistentes: [],
      selectedTag: null,
      isModalOpen: false
    };
  },
  methods: {
    openEditModal(tag) {
      this.selectedTag = tag;
      this.fetchTagsFromDatabase(); // Busca as tags do banco
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
      this.selectedTag = null;
    },
    fetchTagsFromDatabase() {
      // Simulação de uma chamada para uma API para obter tags do banco
      // Isso pode ser feito com axios ou outro método de requisição HTTP
      this.tagsExistentes = [
        { nome: "Agricultura" },
        { nome: "Tecnologia" },
        { nome: "Economia" }
      ];
    }
  }
};
</script>

<style>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
