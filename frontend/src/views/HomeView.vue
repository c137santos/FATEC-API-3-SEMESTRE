<!-- HomeView.vue -->
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
          :key="noticia.notiId || noticia.id"
          :noticia="noticia"
          @exibir-noticia="exibirNoticiaInteira"
        />
        <div v-if="noticias.length === 0 && !isLoading" class="no-results">
          Nenhuma notícia encontrada.
        </div>
      </div>
    </div>

    <!-- Pop-up de notícias -->
    <div v-if="noticiaCompleta" class="popup-overlay">
      <div class="popup-content">
        <div class="popup-header">
          <img src="@/components/icons/info.png" alt="Info Icon" class="popup-info-icon" />
          <div class="popup-header-text">
            <h3>{{ noticiaCompleta.portal.nome }}</h3>
            <p><strong>Jornalista:</strong> {{ noticiaCompleta.reporte?.nome || 'Jornalista não identificado' }}</p>
          </div>
        </div>
        <div class="popup-body">
          <p class="popup-text">{{ noticiaCompleta.notiText }}</p>
        </div>
        <button @click="fecharPopUp" class="close-popup-btn">Fechar</button>
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
      noticiaCompleta: null,
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
          content: n.notiText || "Conteúdo indisponível",
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
    async exibirNoticiaInteira(id) {
      try {
        const response = await axios.get(`http://localhost:8080/noticias/${id}`);
        if (response.status === 200) {
          this.noticiaCompleta = response.data;
        } else {
          throw new Error(`Erro HTTP! ${response.status}`);
        }
      } catch (error) {
        console.error('Erro ao carregar notícia:', error);
      }
    },
    fecharPopUp() {
      this.noticiaCompleta = null;
    },
  },
};
</script>

<style scoped>
/* Estilos do HomeView.vue */

/* ... Seus estilos existentes ... */

.home-view {
  display: flex;
  height: 100vh;
}

.home-view .main-content {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  padding: 20px;
  margin-top: 15px;
}

.home-view .search-section {
  display: flex;
  gap: 10px;
  width: 100%;
  margin-bottom: 10px;
}

.home-view .search-bar {
  flex: 2;
}

.home-view .date-section {
  flex: 1;
  display: flex;
  justify-content: space-between;
  width: 50%;
}

.home-view .filters {
  display: flex;
  gap: 10px;
  width: 100%;
  margin-bottom: 20px;
}

.home-view .filters select {
  flex: 1;
  width: 50%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
  outline: none;
}

.home-view .news-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.home-view .pagination-wrapper {
  display: flex;
  justify-content: flex-end;
}

.home-view .pagination-controls {
  display: flex;
  gap: 1rem;
  align-items: center;
  margin-bottom: 16px;
}

.home-view .pagination-controls button {
  padding: 8px 16px;
  background-color: #6f3c91;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.home-view .pagination-controls button:disabled {
  background-color: #e0e0e0;
  cursor: not-allowed;
}

/* Estilos do Modal */

/* Pop-up da notícia */
.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.75);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.popup-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 16px;
  width: 80%;
  max-width: 1000px;
  max-height: 85vh;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.popup-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.popup-info-icon {
  width: 25px;
  height: 25px;
  margin-right: 10px;
  margin-bottom: 80px;
}

.popup-header-text h3 {
  margin: 0;
  font-size: 24px;
  color: #49454F;
}

.popup-header-text p {
  margin: 0;
  color: #49454F;
  font-size: 16px;
  text-align: left;
}

.popup-body {
  flex-grow: 1;
  overflow-y: auto;
  margin-bottom: 20px;
}

.popup-text {
  margin: 0;
  font-size: 16px;
  line-height: 1.5;
  color: #49454F;
  white-space: pre-line;
}

.close-popup-btn {
  align-self: flex-end;
  padding: 8px 16px;
  background-color: #65558F;
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

/* Ajustes para dispositivos móveis */
@media (max-width: 768px) {
  .home-view .main-content {
    padding: 10px;
    margin-left: 0;
  }

  .home-view .search-section,
  .home-view .filters {
    flex-direction: column;
  }

  .home-view .filters select {
    width: 100%;
  }

  .popup-content {
    width: 90%;
    max-width: none;
  }

  .popup-header-text h3 {
    font-size: 20px;
  }

  .popup-header-text p {
    font-size: 14px;
  }

  .popup-body {
    margin-bottom: 10px;
  }
}
</style>
