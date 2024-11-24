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
          :noticia="noticia"
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
import "./../utils/HomeView.css";
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
      keyword: "", // Palavra-chave para o filtro de texto livre
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
      errorMessage: "", // Mensagem de erro para validações
      isLoading: false,  // Indicador de carregamento
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
          fetch("http://localhost:8080/tags/listar"),
          fetch("http://localhost:8080/portais/listar"),
          fetch("http://localhost:8080/reporters/listar"),
        ]);

        if (!tagsResponse.ok || !portaisResponse.ok || !reportersResponse.ok) {
          throw new Error("Erro ao carregar os filtros");
        }

        this.tags = (await tagsResponse.json()).map((tag) => ({ name: tag.tagNome }));
        this.portais = (await portaisResponse.json()).map((portal) => ({ name: portal.nome }));
        this.reporters = (await reportersResponse.json()).map((reporter) => ({ name: reporter.nome }));
      } catch (error) {
        console.error(error.message);
        alert("Erro ao carregar os filtros.");
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
          fetch(`http://localhost:8080/noticias/listar?${params.toString()}`),
          fetch(`http://localhost:8080/noticias/total?${params.toString()}`)
        ]);

        if (!response.ok) throw new Error("Erro ao buscar notícias");
        if (!totalResponse.ok) throw new Error("Erro ao buscar total de notícias");

        const noticiaList = await response.json();
        this.noticias = noticiaList.map((n) => ({
          notiId: n.notiId,
          portal: n.portal?.nome || "Portal não encontrado",
          jornalista: n.reporte?.nome || "Jornalista não identificado",
          content: n.notiText || "Conteúdo indisponível",
          data: n.notiData || "Data não informada",
          categorias: n.tagNoticia ? n.tagNoticia.map(tag => tag.tagId.tagNome) : [],
        }));

        const totalNoticias = await totalResponse.json();
        this.totalPages = Math.ceil(totalNoticias / 10);
      } catch (error) {
        console.error(error.message);
        alert(error.message);
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
      // Limpar mensagens de erro anteriores
      this.errorMessage = "";

      // Validação de Datas
      if (this.selectedStartDate && this.selectedEndDate) {
        if (this.selectedStartDate > this.selectedEndDate) {
          this.errorMessage = "Intervalo de datas inválido: a data de início deve ser anterior ou igual à data de fim.";
          return;
        }
      }

      // Formatar as datas para garantir o padrão YYYY-MM-DD
      // O componente de data já fornece nesse formato, mas podemos reforçar
      if (this.selectedStartDate) {
        // Verifica se a data está no formato correto
        const startDateRegex = /^\d{4}-\d{2}-\d{2}$/;
        if (!startDateRegex.test(this.selectedStartDate)) {
          this.errorMessage = "Formato da data de início inválido. Use YYYY-MM-DD.";
          return;
        }
      }

      if (this.selectedEndDate) {
        const endDateRegex = /^\d{4}-\d{2}-\d{2}$/;
        if (!endDateRegex.test(this.selectedEndDate)) {
          this.errorMessage = "Formato da data de fim inválido. Use YYYY-MM-DD.";
          return;
        }
      }

      // Aplicar os filtros e buscar as notícias
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

.pagination-controls {
  display: flex;
  align-items: center;
  gap: 10px;
}

.news-list {
  margin-top: 20px;
}

.no-results {
  text-align: center;
  color: #49454F;
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
