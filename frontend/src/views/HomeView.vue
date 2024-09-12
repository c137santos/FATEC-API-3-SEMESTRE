<template>
  <div class="home-view">
    <header class="header">
      <h1 class="cerberus-title">Cérberus</h1>
    </header>

    <SideBar />

    <div class="main-content">
      <!-- Barra de pesquisa e datas -->
      <div class="search-section">
        <div class="search-bar">
          <SearchBar @search="handleSearch" />
        </div>

        <!-- Reintroduzindo DataRange -->
        <div class="date-section">
          <DataRange @start-date="setStartDate" @end-date="setEndDate" />
        </div>
      </div>

      <!-- Filtros de Tags e Portais com MultiSelect -->
      <div class="filters">
        <MultiSelect 
          v-model="selectedTags" 
          :options="tags" 
          placeholder="Selecionar Tags" 
        />
        <MultiSelect 
          v-model="selectedPortais" 
          :options="portais" 
          placeholder="Selecionar Portais" 
        />
      </div>

      <!-- Lista de notícias -->
      <div class="news-list">
        <NewsCard v-for="noticia in filteredNoticias" :key="noticia.titulo" :noticia="noticia" />
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import SideBar from '@/components/SideBar.vue';
import SearchBar from '@/components/SearchBar.vue';
import DataRange from '@/components/DataRange.vue';
import MultiSelect from '@/components/MultiSelect.vue';
import NewsCard from '@/components/NewsCard.vue';

export default {
  components: {
    SideBar,
    SearchBar,
    DataRange,
    MultiSelect,
    NewsCard,
  },
  data() {
    return {
      selectedTags: [],
      selectedPortais: [],
      tags: [
        { name: "Soja" },
        { name: "Agricultura" },
        { name: "Política" },
        { name: "Economia" }
      ],
      portais: [
        { name: "Portal Exemplo" },
        { name: "Portal Exemplo 2" },
        { name: "Portal 3" },
        { name: "Portal 4" }
      ],
      startDate: '',
      endDate: '',
      filteredNoticias: [],
      noticias: [],
      useMockData: true // Altere para false quando quiser usar o backend
    };
  },
  mounted() {
    this.fetchNoticias();
  },
  methods: {
    async fetchNoticias() {
      if (this.useMockData) {
        // Mockando notícias diretamente no frontend
        this.noticias = [
          { 
            titulo: "Notícia Mockada 1", 
            portal: "Portal Exemplo", 
            jornalista: "Jornalista Exemplo", 
            data: "01/01/2023", 
            categorias: ["soja", "agricultura"] 
          },
          { 
            titulo: "Notícia Mockada 2", 
            portal: "Portal Exemplo 2", 
            jornalista: "Outro Jornalista", 
            data: "02/01/2023", 
            categorias: ["política", "economia"] 
          }
        ];
        this.filteredNoticias = this.noticias;
      } else {
        // Busca de notícias do backend
        try {
          const response = await axios.get('http://localhost:8080/noticias');
          this.noticias = response.data;
          this.filteredNoticias = this.noticias;
        } catch (error) {
          console.error("Erro ao buscar notícias do backend:", error);
        }
      }
    },
    handleSearch(keyword) {
      this.filterNoticias(keyword);
    },
    setStartDate(date) {
      this.startDate = date;
      this.filterNoticias();
    },
    setEndDate(date) {
      this.endDate = date;
      this.filterNoticias();
    },
    filterNoticias(keyword = '') {
      this.filteredNoticias = this.noticias.filter(noticia => {
        const keywordMatch = noticia.titulo.toLowerCase().includes(keyword.toLowerCase());
        const tagMatch = this.selectedTags.length
          ? this.selectedTags.some(tag => noticia.categorias.includes(tag.name))
          : true;
        const portalMatch = this.selectedPortais.length
          ? this.selectedPortais.some(portal => portal.name === noticia.portal)
          : true;
        const dateMatch = this.startDate && this.endDate
          ? new Date(noticia.data) >= new Date(this.startDate) && new Date(noticia.data) <= new Date(this.endDate)
          : true;

        return keywordMatch && tagMatch && portalMatch && dateMatch;
      });
    }
  }
};
</script>

<style scoped>
.home-view {
  display: flex;
  height: 100vh;
}

.header {
  width: calc(100% - 130px);
  text-align: left;
  position: fixed;
  top: 0;
  left: 130px;
  z-index: 1000;
  background-color: #FFFFFF;
  padding: 10px 0;
}

.cerberus-title {
  font-size: 32px;
  color: #65558F;
  margin: 0;
}

.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 80px;
  background-color: #ECE6F0;
  z-index: 1000;
}

.main-content {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  padding: 20px;
  margin-left: 100px;
  margin-top: 80px;
}

.search-section {
  display: flex;
  gap: 10px;
  width: 100%;
  margin-bottom: 10px;
}

.search-bar {
  flex: 2;
}

.date-section {
  flex: 1;
  display: flex;
  justify-content: space-between;
  width: 50%;
}

.filters {
  display: flex;
  gap: 10px;
  width: 100%;
  margin-bottom: 20px;
}

.news-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

@media (max-width: 768px) {
  .main-content {
    padding: 10px;
    margin-left: 0;
  }

  .search-section, .filters {
    flex-direction: column;
  }
}
</style>
