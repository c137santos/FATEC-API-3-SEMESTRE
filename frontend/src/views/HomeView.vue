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

        const response = await fetch(`http://localhost:8080/noticias/listar?${params.toString()}`);
        if (!response.ok) throw new Error("Erro ao buscar notícias");

        const noticiaList = await response.json();
        this.noticias = noticiaList.map((n) => ({
          notiId: n.notiId,
          portal: n.portal?.nome || "Portal não encontrado",
          jornalista: n.reporte?.nome || "Jornalista não identificado",
          content: n.notiText || "Conteúdo indisponível",
          data: n.notiData || "Data não informada",
          categorias: n.tagNoticia ? n.tagNoticia.map(tag => tag.tagId.tagNome) : [],
        }));

        const totalResponse = await fetch(`http://localhost:8080/noticias/total?${params.toString()}`);
        if (!totalResponse.ok) throw new Error("Erro ao buscar total de notícias");

        this.totalPages = Math.ceil((await totalResponse.json()) / 10);
      } catch (error) {
        console.error(error.message);
        alert("Erro ao carregar as notícias.");
      }
    },
    handleSearch(keyword) {
      this.keyword = keyword;
      this.pageIndex = 0;
      this.fetchNoticias();
    },
    applyFilters() {
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
