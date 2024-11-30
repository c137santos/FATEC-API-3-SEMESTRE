<!-- NewsCard.vue -->
<template>
  <div class="news-card">
    <!-- Ícone de informação -->
    <img src="@/components/icons/info.png" alt="Info Icon" class="info-icon" />

    <!-- Conteúdo da notícia -->
    <div class="news-wrapper">
      <div class="news-content">
        <p><strong>Portal:</strong> {{ noticia.portal }}</p>
        <p><strong>Jornalista:</strong> {{ noticia.jornalista }}</p>
        <p><strong>Data:</strong> {{ formatDate(noticia.data) }}</p>
        <p><strong>Tags:</strong>
          <!-- Exibe as tags associadas à notícia -->
          {{ noticia.categorias && noticia.categorias.length ? noticia.categorias.join(', ') : 'Sem tags' }}
        </p>
        <button class="read-more-btn" @click="noticiaSelecionada(noticia.notiId || noticia.id)">
          Clique aqui para ler a notícia completa
        </button>
      </div>
      <div class="news-content-wrapper">
        {{ conteudoTruncado }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    noticia: {
      type: Object,
      required: true,
    },
  },
  methods: {
    formatDate(dateString) {
      if (!dateString) return 'Data não disponível';
      const date = new Date(dateString);
      return date.toLocaleDateString('pt-BR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      });
    },
    noticiaSelecionada(id) {
      // Emite o evento para o componente pai
      this.$emit('exibir-noticia', id);
    },
  },
  computed: {
    conteudoTruncado() {
      const texto = this.noticia.content || this.noticia.notiText || '';
      return texto.length > 255 ? texto.substring(0, 255) + '...' : texto;
    },
  },
};
</script>

<style scoped>
.news-wrapper {
  width: 100%;
  display: flex;
  gap: 8rem;
}
.news-content-wrapper {
  flex-grow: 0;
  max-width: 30rem;
  overflow: hidden;
  text-overflow: ellipsis;
}
.news-card {
  display: flex;
  align-items: center;
  padding: 16px;
  background-color: #fff;
  border: 1px solid #D9D9D9;
  border-radius: 16px;
  margin-bottom: 20px;
  position: relative;
}

.info-icon {
  position: absolute;
  top: 10px;
  left: 10px;
  width: 25px;
  height: 25px;
}

.news-content {
  margin-left: 50px;
  flex-grow: 1;
  width: max-content;
  text-wrap: nowrap;
}

.news-content h3 {
  margin: 0;
  font-size: 20px;
}

.news-content p {
  margin: 4px 0;
  color: #49454F;
}

.read-more-btn {
  margin-top: 16px;
  padding: 8px 16px;
  border: none;
  background-color: #65558F;
  color: #fff;
  border-radius: 8px;
  cursor: pointer;
}

/* Ajustes para dispositivos móveis */
@media (max-width: 768px) {
  .news-wrapper {
    flex-direction: column;
    gap: 1rem;
  }
  .news-content-wrapper {
    max-width: 100%;
  }
  .news-card {
    flex-direction: column;
    align-items: flex-start;
    padding: 12px;
  }
  .news-content {
    margin-left: 0;
    width: 100%;
    text-wrap: wrap;
  }
  .news-content h3 {
    font-size: 18px;
  }
  .news-content p {
    font-size: 14px;
  }
  .read-more-btn {
    margin-top: 8px;
    padding: 6px 12px;
  }
}
</style>
