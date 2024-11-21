<template>
  <div class="home-view">
    <div class="main-content">
      <div class="search-section">
        <div class="search-bar">
          <SearchBar @search="handleSearch" />
        </div>
        <div class="date-section">
          <DataRange @start-date="setStartDate" @end-date="setEndDate" />
        </div>
      </div>

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

      <div class="pagination-wrapper">
        <div class="pagination-controls">
          <button :disabled="pageIndex <= 0" @click="changePage(-1)">Anterior</button>
          <span>Página {{ pageIndex + 1 }} de {{ totalPages }}</span>
          <button :disabled="pageIndex + 1 >= totalPages" @click="changePage(1)">Próxima</button>
        </div>
      </div>

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
      filteredNoticias: [],
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

        if (!tagsResponse.ok) throw new Error("Erro ao carregar tags");
        if (!portaisResponse.ok) throw new Error("Erro ao carregar portais");
        if (!reportersResponse.ok) throw new Error("Erro ao carregar jornalistas");

        const tagsData = await tagsResponse.json();
        const portaisData = await portaisResponse.json();
        const reportersData = await reportersResponse.json();

        this.tags = tagsData.map((tag) => ({ name: tag.tagNome }));
        this.portais = portaisData.map((portal) => ({ name: portal.nome }));
        this.reporters = reportersData.map((reporter) => ({ name: reporter.nome }));

        console.log("Filtros carregados:", { tags: this.tags, portais: this.portais, reporters: this.reporters });
      } catch (error) {
        console.error(error.message);
        alert("Erro ao carregar os filtros. Verifique o backend.");
      }
    },
    applyFilters() {
      const selectedTag = this.selectedTag || this.tags.map((tag) => tag.name).join(",");
      const selectedPortal = this.selectedPortal || this.portais.map((portal) => portal.name).join(",");
      const selectedReporter = this.selectedReporter || this.reporters.map((reporter) => reporter.name).join(",");

      console.log(`Filtros aplicados: tag=${selectedTag}, portal=${selectedPortal}, reporter=${selectedReporter}`);
      this.pageIndex = 0;
      this.fetchNoticias(selectedTag, selectedPortal, selectedReporter, this.selectedStartDate, this.selectedEndDate);
    },
    async fetchNoticias(selectedTag = "", selectedPortal = "", selectedReporter = "", startDate = "", endDate = "") {
      try {
        const params = new URLSearchParams({
          tag: selectedTag,
          portal: selectedPortal,
          reporter: selectedReporter,
          pageIndex: this.pageIndex,
          startDate: startDate,
          endDate: endDate
        });

        const response = await fetch(`http://localhost:8080/noticias/listar?${params.toString()}`);
        if (!response.ok) throw new Error("Erro ao buscar notícias");

        const noticiaList = await response.json();
        this.noticias = noticiaList.map((n) => ({
          titulo: n.titulo || "Título não encontrado",
          portal: n.portal?.nome || "Portal não encontrado",
          jornalista: n.reporte?.nome || "Jornalista não identificado",  // Corrigido para acessar o nome do jornalista
          content: n.notiText || "Conteúdo indisponível",
          data: n.notiData || "Data não informada",
          categorias: n.tagNoticia ? n.tagNoticia.map(tag => tag.tagId.tagNome) : [],  // Certificando-se de que as tags estão aqui
        }));
        this.filteredNoticias = [...this.noticias];

        const totalResponse = await fetch(`http://localhost:8080/noticias/total?${params.toString()}`);
        if (!totalResponse.ok) throw new Error("Erro ao buscar total de notícias");

        const total = await totalResponse.json();
        this.totalPages = Math.ceil(total / 10);

        console.log("Notícias atualizadas:", this.noticias);
      } catch (error) {
        console.error(error.message);
        alert("Erro ao carregar as notícias.");
      }
    },
    changePage(step) {
      const newPageIndex = this.pageIndex + step;
      if (newPageIndex >= 0 && newPageIndex < this.totalPages) {
        this.pageIndex = newPageIndex;
        this.fetchNoticias();
      }
    },
    handleSearch(keyword) {
      this.selectedTag = keyword;
      this.pageIndex = 0;
      this.fetchNoticias();
    },
    setStartDate(date) {
      this.selectedStartDate = date;
    },
    setEndDate(date) {
      this.selectedEndDate = date;
    }
  },
};
</script>
