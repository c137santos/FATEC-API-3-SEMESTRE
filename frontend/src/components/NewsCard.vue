<template>
  <div class="news-card">
    <img src="@/components/icons/info.png" alt="Info Icon" class="info-icon" />

    <div class="news-wrapper">
      <div class="news-content">
        <p><strong>Portal:</strong> {{ noticia.portal }}</p>
        <p><strong>Jornalista:</strong> {{ noticia.jornalista }}</p>
        <p><strong>Data:</strong> {{ formatDate(noticia.data) }}</p>
        <p><strong>Tags:</strong> {{ noticia.categorias.join(', ') }}</p>
        <button class="read-more-btn" @click="showPopUp = true">Clique aqui para ler a notícia completa</button>
      </div>
    </div>

    <div v-if="showPopUp" class="popup-overlay">
      <div class="popup-content">
        <div class="popup-header">
          <img src="@/components/icons/info.png" alt="Info Icon" class="popup-info-icon" />
          <div class="popup-header-text">
            <h3>{{ noticia.titulo }}</h3>
            <p><strong>Jornalista:</strong> {{ noticia.jornalista }}</p>
          </div>
        </div>

        <div class="popup-body">
          <p class="popup-text">Aqui estará o conteúdo completo da notícia</p>
        </div>

        <button @click="showPopUp = false" class="close-popup-btn">Fechar</button>
      </div>
    </div>
  </div>
</template>

<script>
import '@/assets/base.css';
export default {
  props: {
    noticia: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      showPopUp: false,
    };
  },
  methods: {
    formatDate(dateString) {
      if (!dateString) {
        return "Data não disponível";
      }
      const date = new Date(dateString);
      return date.toLocaleDateString('pt-BR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      });
    },
    noticiaSelecionada(id) {
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
