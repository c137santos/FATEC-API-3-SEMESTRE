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

      <!-- Mensagens de Erro -->
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>

      <!-- Filtros -->
      <div class="filters">
        <select v-model="selectedTag">
          <option value="">Selecionar Tag</option>
          <option v-for="tag in tags" :key="tag.name" :value="tag.name">{{ tag.name }}</option>
        </select>

        <select v-model="selectedPortal">
          <option value="">Selecionar Portal</option>
          <option v-for="portal in portais" :key="portal.name" :value="portal.name">{{ portal.name }}</option>
        </select>

        <select v-model="selectedReporter">
          <option value="">Selecionar Jornalista</option>
          <option v-for="reporter in reporters" :key="reporter.name" :value="reporter.name">{{ reporter.name }}</option>
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
          v-for="noticia in noticias"
          :key="noticia.notiId"
          :noticia="{
            ...noticia,
            content: noticia.content.length > 255 ? noticia.content.substring(0, 255) + '...' : noticia.content
          }"
        />
        <div v-if="noticias.length === 0 && !isLoading" class="no-results">
          Nenhuma notícia encontrada.
        </div>
      </div>

      <!-- Indicador de Carregamento -->
      <div v-if="isLoading" class="loading">
        Carregando notícias...
      </div>
    </div>
  </div>
</template>

<script>
import SearchBar from "@/components/SearchBar.vue";
import DataRange from "@/components/DataRange.vue";
import NewsCard from "@/components/NewsCard.vue";
import axios from "axios";

export default {
  components: {
    SearchBar,
    DataRange,
    NewsCard,
  },
  data() {
    return {
      keyword: "",
      pageIndex: 0,
      totalPages: 0,
      selectedTag: "",
      selectedPortal: "",
      selectedReporter: "",
      selectedStartDate: "",
      selectedEndDate: "",
      tags: [],
      portais: [],
      reporters: [],
      noticias: [],
      errorMessage: "",
      isLoading: false,
    };
  },
  mounted() {
    this.fetchFilters();
    this.fetchNoticias();
  },
  methods: {
    async fetchFilters() {
      try {
        const [tagsResponse, portaisResponse, reportersResponse] = await Promise.all([
          axios.get("http://localhost:8080/tags/listar"),
          axios.get("http://localhost:8080/portais/listar"),
          axios.get("http://localhost:8080/reporters/listar"),
        ]);

        this.tags = tagsResponse.data.map((tag) => ({ name: tag.tagNome }));
        this.portais = portaisResponse.data.map((portal) => ({ name: portal.nome }));
        this.reporters = reportersResponse.data.map((reporter) => ({ name: reporter.nome }));
      } catch (error) {
        console.error(error.message);
        this.errorMessage = "Erro ao carregar os filtros.";
      }
    },
    async fetchNoticias() {
      this.isLoading = true;
      this.errorMessage = "";
      try {
        const params = new URLSearchParams({
          keyword: this.keyword || "",
          tag: this.selectedTag || "",
          portal: this.selectedPortal || "",
          reporter: this.selectedReporter || "",
          startDate: this.selectedStartDate || "",
          endDate: this.selectedEndDate || "",
          pageIndex: this.pageIndex,
        });

        const [response, totalResponse] = await Promise.all([
          axios.get(`http://localhost:8080/noticias/listar?${params.toString()}`),
          axios.get(`http://localhost:8080/noticias/total?${params.toString()}`),
        ]);

        this.noticias = response.data.map((n) => ({
          notiId: n.notiId,
          portal: n.portal?.nome || "Portal não encontrado",
          jornalista: n.reporte?.nome || "Jornalista não identificado",
          content: n.notiText || "Conteúdo indisponível", // Mantém o texto completo
          data: n.notiData || "Data não informada",
          categorias: n.tagNoticia ? n.tagNoticia.map((tag) => tag.tagId.tagNome) : [],
        }));

        this.totalPages = Math.ceil(totalResponse.data / 10);
      } catch (error) {
        console.error(error.message);
        this.errorMessage = "Erro ao carregar as notícias.";
      } finally {
        this.isLoading = false;
      }
    },
    handleSearch(keyword) {
      this.keyword = keyword;
      this.pageIndex = 0;
      this.fetchNoticias();
    },
    applyFilters() {
      this.errorMessage = "";
      if (this.selectedStartDate && this.selectedEndDate && this.selectedStartDate > this.selectedEndDate) {
        this.errorMessage = "Intervalo de datas inválido.";
        return;
      }
      this.pageIndex = 0;
      this.fetchNoticias();
    },
    changePage(step) {
      const newPageIndex = this.pageIndex + step;
      if (newPageIndex >= 0 && newPageIndex < this.totalPages) {
        this.pageIndex = newPageIndex;
        this.fetchNoticias();
      }
    },
    setStartDate(date) {
      this.selectedStartDate = date;
    },
    setEndDate(date) {
      this.selectedEndDate = date;
    },
  },
};
</script>

<style scoped>
.date-section {
  margin-top: 10px;
}
.filters {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}
.filters select {
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #d9d9d9;
}
.filters button {
  padding: 8px 16px;
  border: none;
  background-color: #65558F;
  color: #fff;
  border-radius: 8px;
  cursor: pointer;
}
.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
.news-list {
  margin-top: 20px;
}
.loading {
  text-align: center;
  color: #49454F;
  margin-top: 20px;
}
.error-message {
  color: red;
  margin-top: 10px;
  text-align: center;
}
</style>
