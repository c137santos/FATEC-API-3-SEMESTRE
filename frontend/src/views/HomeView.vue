<template>
  <div class="home-view">
    <div class="main-content">
      <!-- Seção de busca -->
      <div class="search-section">
        <div class="search-bar">
          <SearchBar @search="handleSearch" />
        </div>
        <div class="date-section">
          <DataRange @start-date="setStartDate" @end-date="setEndDate" />
        </div>
      </div>

      <!-- Seção de filtros -->
      <div class="filters">
        <select v-model="selectedTag">
          <option value="">Selecionar Tag</option>
          <option v-for="tag in tags" :key="tag.name" :value="tag.name">{{ tag.name }}</option>
        </select>
        <button @click="applyFilters">Aplicar Filtros</button>
      </div>

      <!-- Paginação -->
      <div class="pagination-wrapper">
        <div class="pagination-controls">
          <button :disabled="pageIndex <= 0" @click="changePage(-1)">Anterior</button>
          <span>Página {{ pageIndex + 1 }} de {{ totalPages }}</span>
          <button :disabled="pageIndex + 1 >= totalPages" @click="changePage(1)">Próxima</button>
        </div>
      </div>

      <!-- Lista de notícias -->
      <div class="news-list">
        <NewsCard
          v-for="noticia in filteredNoticias"
          :key="noticia.titulo + noticia.portal"
          :noticia="noticia"
        />
      </div>
    </div>
  </div>
</template>

<script>
import "./../utils/HomeView.css"; // Importando o CSS específico para HomeView
import SearchBar from "@/components/SearchBar.vue";
import DataRange from "@/components/DataRange.vue";
import NewsCard from "@/components/NewsCard.vue";

export default {
  components: {
    SearchBar,
    DataRange,
    NewsCard,
  },
  data() {
    return {
      pageIndex: 0, // Índice da página atual
      totalPages: 0, // Total de páginas
      selectedTag: "", // Tag selecionada
      tags: [], // Lista de tags disponíveis
      filteredNoticias: [], // Notícias filtradas para exibição
      noticias: [], // Notícias completas da API
    };
  },
  mounted() {
    console.log("Componente montado. Iniciando carregamento de dados...");
    this.fetchFilters(); // Carrega os filtros disponíveis
    this.fetchNoticias(); // Carrega todas as notícias inicialmente
  },
  methods: {
    // Carrega os filtros de tags
    async fetchFilters() {
      try {
        const response = await fetch("http://localhost:8080/tags/listar");
        const data = await response.json();
        this.tags = data.map((tag) => ({ name: tag.tagNome }));
        console.log("Tags carregadas:", this.tags);
      } catch (error) {
        console.error("Erro ao buscar tags:", error);
      }
    },

    // Aplica os filtros selecionados
    applyFilters() {
      console.log("Aplicando filtros selecionados...");
      this.pageIndex = 0; // Reinicia a página para a primeira
      this.fetchNoticias();
    },

    // Busca notícias filtradas
    async fetchNoticias() {
      try {
        const params = new URLSearchParams({
          tag: this.selectedTag || "",
          pageIndex: this.pageIndex,
        });

        const response = await fetch(`http://localhost:8080/noticias/listar?${params.toString()}`);
        const noticiaList = await response.json();

        this.noticias = noticiaList.map((n) => ({
          titulo: n.titulo || "Título não encontrado",
          portal: n.portal?.nome || "Portal não encontrado",
          jornalista: n.jornalista || "Jornalista não identificado",
          content: n.notiText || "Conteúdo indisponível",
          data: n.notiData || "Data não informada",
          categorias: n.categorias || [],
        }));

        this.filteredNoticias = [...this.noticias];

        const totalResponse = await fetch(`http://localhost:8080/noticias/total?${params.toString()}`);
        const total = await totalResponse.json();
        this.totalPages = Math.ceil(total / 10);

        console.log("Notícias atualizadas:", this.noticias);
      } catch (error) {
        console.error("Erro ao buscar notícias:", error);
      }
    },

    // Altera a página atual
    changePage(step) {
      const newPageIndex = this.pageIndex + step;
      if (newPageIndex >= 0 && newPageIndex < this.totalPages) {
        this.pageIndex = newPageIndex;
        this.fetchNoticias();
      }
    },
  },
};
</script>
