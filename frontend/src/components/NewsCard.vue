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
        <div class="popup-header">
          <img src="@/components/icons/info.png" alt="Info Icon" class="popup-info-icon" />
          <div class="popup-header-text">
            <p><strong>Fonte: {{ noticia.portal }}</strong></p>
            <p><strong>Escrito por:</strong> {{ noticia.jornalista }}</p>
            <p><strong>{{ formatDate(noticia.data)}}</strong></p>
            <p><strong>Url:</strong><a :href="noticia.url" target="_blank">{{ noticia.url }}</a></p>
          </div>
        </div>
        </div>
        
        <div class="popup-body">
          <h2>{{ noticia.texto }}</h2>
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
      showPopUp: false, 
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
    formatDate(dateString) {
      const options = { year: "numeric", month: "long", day: "numeric" };
      const date = new Date(dateString);
      return date.toLocaleDateString("pt-BR", options);
    }
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
  margin-bottom: 80px;
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
  text-align: left;
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

/* @media para dispositivos móveis */
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
  .popup-content {
    width: 90%;
    max-width: none;
  }
  .popup-header-text h3 {
    font-size: 20px;
  }
  .popup-header-text p {
    font-size: 14px;
  }
  .popup-body {
    margin-bottom: 10px;
  }
}
</style>
