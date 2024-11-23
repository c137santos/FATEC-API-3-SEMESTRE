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
      <div class="pagination-wrapper">
        <div v-if="true" class="pagination-controls">
          <button :disabled="pageIndex <= 0" @click="() => {
            this.pageIndex --
            this.fetchNoticias()
          }">Anterior</button>
          <span>Página {{ pageIndex + 1 }}</span>
          <button :disabled="pageIndex + 1 >= totalPages" @click="() => {
            this.pageIndex ++
            this.fetchNoticias()
          }">Próxima</button>
        </div>
      </div>

      <!-- Lista de notícias -->
      <div class="news-list" :key="filteredNoticias">
        <NewsCard 
        v-for="noticia in filteredNoticias" 
        :key="noticia" 
        :noticia="noticia" 
        />
      </div>
      
      <!-- PopUp de notícias -->
      <div class="news-popup">
        <ModalExibirNoticia
        v-if="showPopUp"
        :noticia="noticiaCompleta"
        @fechar="fecharPopUp"
        />

      </div>

    </div>
  </div>
</template>

<script>
import SearchBar from '@/components/SearchBar.vue';
import DataRange from '@/components/DataRange.vue';
import NewsCard from '@/components/NewsCard.vue';
import ModalExibirNoticia from '@/components/ModalExibirNoticia.vue';
import axios from 'axios';

export default {
  components: {
    SearchBar,
    DataRange,
    NewsCard,
  },
  data() {
    return {
      pageIndex: 0,
      totalPages: 0,
      selectedTag: '',  
      selectedPortal: '',  
      tags: [],
      portais: [],
      startDate: '',
      endDate: '',
      filteredNoticias: [],
      noticias: [],
      noticiaCompleta: {}
    };
  },
  mounted() {
    this.fetchNoticias()
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
    },
    async fetchNoticias() {
      const noticiaList = (await axios(`http://localhost:8080/noticias/listar/${this.pageIndex}`)).data
      const mappedNoticiaList = noticiaList.map((n) => ({
        id: n.notiId,
        titulo: n.portal.nome,
        portal: n.portal.nome,
        jornalista: n.reporte.nome ?? '',
        content: n.notiText,
        data: n.notiData ? n.notiData.join('/') : '',
        categorias: n.tagNoticia.map(tagNoticia => tagNoticia.tagId.tagNome)
      }))
      
      const count = (await axios('http://localhost:8080/noticias/total')).data
      
      this.totalPages = Math.ceil(count / 10)
      this.noticias = mappedNoticiaList
      this.filteredNoticias = this.noticias;
      
    },
   async exibirNoticiaInteira(id){
     fetch.get(`http://localhost:8080/noticias/${id}`)
     .then(response => {
       this.noticiaCompleta = response.data;
       this.showPopUp = true;
      }).catch(error => {
        console.error('Erro ao carregar noticia:', error);
      }); 
    },
    fecharPopUp() {
      this.noticiaCompleta = null;
      this.showPopUp = false;
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

.pagination-wrapper {
  display: flex;
  justify-content: flex-end;
}

.pagination-controls {
  display: flex;
  gap: 1rem;
  align-items: center;
  margin-bottom: 16px;
}

.pagination-controls button {
  padding: 8px 16px;
  background-color: #6f3c91;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.pagination-controls button:disabled {
  background-color: #e0e0e0;
  cursor: not-allowed;
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
