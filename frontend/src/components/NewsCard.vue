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
       <div class="news-content-wrapper">
          {{ noticia.content }}
       </div>
     </div>

    <!-- Pop-up da notícia (pode ser implementado depois) -->
    <div v-if="showPopUp" class="popup-overlay">
      <div class="popup-content">
        <div class="modal-actions">
          <button class="btn-fechar" @click="fecharModal">X</button>
        <div class="popup-header">
          <img src="@/components/icons/info.png" alt="Info Icon" class="popup-info-icon" />
          <div class="popup-header-text">
            <h3>{{ noticia.titulo }}</h3>
            <p><strong>Jornalista:</strong> {{ noticia.jornalista }}</p>
          </div>
        </div>
        </div>
        
        <div class="popup-body">
          <h2>{{ noticia.notiText }}</h2>
          <a :href="noticia.url" target="_blank">{{ noticia.url }}</a>
          <p>{{ noticia.notiData }}</p>
          <p>Fonte: {{ noticia.portalNome }}</p>
          <p>Escrito por: {{ noticia.reporterNome }}</p>
          <p class="popup-text">Aqui estará o conteúdo completo da notícia</p>
        </div>
        
        <button @click="showPopUp = false" class="close-popup-btn">Fechar</button>
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
  data() {
    return {
      noticiaModal: false, 
    };
  },
  methods: {
    abrirModal() {
      this.noticiaModal = true;
    },
    fecharModal() {
      this.noticiaModal = false;
      this.$emit("fechar");
    },
    handleEscKey(event) {
      if (event.key === "Escape") {
        this.fecharModal();
      }
    },
  },
  mounted() {
    document.addEventListener("keydown", this.handleEscKey);
  },
  beforeDestroy() {
    document.removeEventListener("keydown", this.handleEscKey);
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

/* Estilos do Pop-up */
.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.75);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.popup-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 16px;
  width: 60%;
  max-width: 800px;
  max-height: 70vh;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.popup-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.popup-info-icon {
  width: 25px;
  height: 25px;
  margin-right: 10px;
}

.popup-header-text h3 {
  margin: 0;
  font-size: 24px;
  color: #49454F;
}

.popup-header-text p {
  margin: 0;
  color: #49454F;
  font-size: 16px;
}

.popup-body {
  flex-grow: 1;
  overflow-y: auto;
  margin-bottom: 20px;
}

.popup-text {
  margin: 0;
  font-size: 16px;
  line-height: 1.5;
  color: #49454F;
  white-space: pre-line;
}

.close-popup-btn {
  align-self: flex-end;
  padding: 8px 16px;
  background-color: #65558F;
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.modal-actions {
  text-align: right;
}

.btn-fechar {
position: absolute;
top: 10px;
right: 10px;
background: none;
border: none;
font-size: 24px;
cursor: pointer;
}
</style>
