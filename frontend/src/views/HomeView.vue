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

        <select v-model="selectedPortal">
          <option value="">Selecionar Portal</option>
          <option v-for="portal in portais" :key="portal.name" :value="portal.name">{{ portal.name }}</option>
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
      tags: [],
      portais: [],
      filteredNoticias: [],
      noticias: [],
    };
  },
  mounted() {
    console.log("Componente montado. Iniciando carregamento de dados...");
    this.fetchFilters();
    this.fetchNoticias();
  },
  methods: {
    // Carrega os filtros de tags e portais
    async fetchFilters() {
      try {
        const [tagsResponse, portaisResponse] = await Promise.all([
          fetch("http://localhost:8080/tags/listar"),
          fetch("http://localhost:8080/portais/listar"),
        ]);

        if (!tagsResponse.ok || !portaisResponse.ok) {
          throw new Error("Falha ao carregar os filtros");
        }

        const tagsData = await tagsResponse.json();
        const portaisData = await portaisResponse.json();

        this.tags = tagsData.map((tag) => ({ name: tag.tagNome }));
        this.portais = portaisData.map((portal) => ({ name: portal.nome }));

        console.log("Tags e Portais carregados:", this.tags, this.portais);
      } catch (error) {
        console.error("Erro ao buscar filtros:", error);
        alert("Erro ao carregar os filtros. Tente novamente mais tarde.");
      }
    },

    // Aplica os filtros selecionados
    applyFilters() {
      console.log("Aplicando filtros selecionados...");

      // Se nenhum valor for selecionado, envia todas as opções disponíveis
      const selectedTag = this.selectedTag || this.tags.map((tag) => tag.name).join(",");
      const selectedPortal = this.selectedPortal || this.portais.map((portal) => portal.name).join(",");

      console.log(`Filtros aplicados: tag=${selectedTag}, portal=${selectedPortal}`);

      this.pageIndex = 0;
      this.fetchNoticias(selectedTag, selectedPortal);
    },

    // Busca notícias filtradas
    async fetchNoticias(selectedTag = "", selectedPortal = "") {
      try {
        const params = new URLSearchParams({
          tag: selectedTag,
          portal: selectedPortal,
          pageIndex: this.pageIndex,
        });

        const response = await fetch(`http://localhost:8080/noticias/listar?${params.toString()}`);
        if (!response.ok) {
          throw new Error("Erro ao buscar notícias");
        }

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
        if (!totalResponse.ok) {
          throw new Error("Erro ao buscar total de notícias");
        }

        const total = await totalResponse.json();
        this.totalPages = Math.ceil(total / 10);

        console.log("Notícias atualizadas:", this.noticias);
      } catch (error) {
        console.error("Erro ao buscar notícias:", error);
        alert("Erro ao carregar as notícias. Tente novamente mais tarde.");
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

    // Define a palavra-chave de busca
    handleSearch(keyword) {
      console.log("Buscando por palavra-chave:", keyword);
      this.selectedTag = keyword;
      this.pageIndex = 0;
      this.fetchNoticias();
    },
  },
};
</script>
