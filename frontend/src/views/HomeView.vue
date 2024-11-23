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
import '@/assets/base.css';
import SearchBar from '@/components/SearchBar.vue';
import DataRange from '@/components/DataRange.vue';
import NewsCard from '@/components/NewsCard.vue';

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
      noticias: [
        { 
          titulo: "Notícia Mockada 1", 
          portal: "Portal Exemplo", 
          jornalista: "Jornalista Exemplo", 
          data: "01/01/2023", 
          categorias: ["Soja", "Agricultura"] 
        },
        { 
          titulo: "Notícia Mockada 2", 
          portal: "Portal Exemplo 2", 
          jornalista: "Outro Jornalista", 
          data: "02/01/2023", 
          categorias: ["Política", "Economia"] 
        }
      ]
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
