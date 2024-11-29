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
        <p><strong>Tags:</strong> {{ noticia.categorias.join(', ') }}</p>
        <button class="read-more-btn" @click="showPopUp = true">Clique aqui para ler a notícia completa</button>
      </div>
    </div>

    <!-- Pop-up da notícia -->
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
  props: ['noticia'],  // Recebe a notícia como prop
  data() {
    return {
      showPopUp: false,  // Controla a exibição do pop-up
    };
  },
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('pt-BR', { year: 'numeric', month: 'long', day: 'numeric' });
    },
  },
};
</script>
