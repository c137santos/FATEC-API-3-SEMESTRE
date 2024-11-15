<template>
  <div class="home-view">
    <div class="main-content">
      <!-- Barra de pesquisa e datas -->
      <div class="search-section">
        <div class="search-bar">
          <SearchBar @search="handleSearch" />
        </div>

        <!-- DataRange -->
        <div class="date-section">
          <DataRange @start-date="setStartDate" @end-date="setEndDate" />
        </div>
      </div>

      <!-- Filtros de Tags e Portais -->
      <div class="filters">
        <select v-model="selectedTag">
          <option value="">Selecionar Tag</option>
          <option v-for="tag in tags" :key="tag.name" :value="tag.name">{{ tag.name }}</option>
        </select>

        <select v-model="selectedPortal">
          <option value="">Selecionar Portal</option>
          <option v-for="portal in portais" :key="portal.name" :value="portal.name">{{ portal.name }}</option>
        </select>
      </div>

      <!-- Lista de notícias -->
      <div class="news-list">
        <NewsCard v-for="noticia in filteredNoticias" :key="noticia.titulo" :noticia="noticia" />
      </div>
    </div>
  </div>
</template>

<script>
import SearchBar from '@/components/SearchBar.vue';
import DataRange from '@/components/DataRange.vue';
import NewsCard from '@/components/NewsCard.vue';
import ListaNoticias from '@/components/ListaNoticias.vue';

export default {
  components: {
    SearchBar,
    DataRange,
    NewsCard,
  },
  data() {
    return {
      selectedTag: '',  
      selectedPortal: '',  
      tags: []
    };
  },
  mounted() {
    this.filteredNoticias = this.noticias;
  },
  methods: {
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
    filterByKeyword(noticia, keyword) {
      return noticia.titulo.toLowerCase().includes(keyword.toLowerCase());
    },
    filterByTag(noticia) {
      return this.selectedTag 
        ? noticia.categorias.includes(this.selectedTag)
        : true;
    },
    filterByPortal(noticia) {
      return this.selectedPortal
        ? noticia.portal === this.selectedPortal
        : true;
    },
    filterByDate(noticia) {
      if (this.startDate && this.endDate) {
        const noticiaDate = new Date(noticia.data);
        return noticiaDate >= new Date(this.startDate) && noticiaDate <= new Date(this.endDate);
      }
      return true;
    },
    filterNoticias(keyword = '') {
      this.filteredNoticias = this.noticias.filter(noticia => {
        return this.filterByKeyword(noticia, keyword) &&
               this.filterByTag(noticia) &&
               this.filterByPortal(noticia) &&
               this.filterByDate(noticia);
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

.main-content {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  padding: 20px;
  margin-top: 15px; /* Removida a margem da esquerda */
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

.filters select {
  flex: 1;
  width: 50%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc; 
  outline: none; 
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

  .filters select {
    width: 100%;
  }
}
</style>
