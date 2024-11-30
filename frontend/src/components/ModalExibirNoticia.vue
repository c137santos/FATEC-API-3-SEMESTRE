<template>
  <div class="popup-overlay">
  <div class="popup-content">
    <div class="modal-actions">
    <div class="popup-header">
      <img src="@/components/icons/info.png" alt="Info Icon" class="popup-info-icon" />
      <div class="popup-header-text">
        <p><strong>Fonte: {{ noticia.portal.nome }}</strong></p>
        <p><strong> {{ formatDate(noticia.notiData)}}</strong></p>
        <p><strong>Url:</strong><a :href="noticia.url" target="_blank">{{ noticia.url }}</a></p>          
      </div>
      </div>
    </div>
    
    <div class="popup-body">
      <h2>{{ noticia.notiText }}</h2>        
    </div>
    <button @click="$emit('fechar')" class="close-popup-btn">Fechar</button>
  </div>
</div>
</template>

<script>
export default {
  props: {
    noticiaCompleta: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      noticia: this.noticiaCompleta,
    }
  },
  methods: {
    handleEscKey(event) {
      if (event.key === "Escape") {
        this.$emit('fechar')
      }
    },

    formatDate(dateString) {
      if (!dateString) 
        return "Data não disponível";
  
      const date = new Date(dateString);
      
      return date.toLocaleDateString('pt-BR', { year: 'numeric', month: 'long', day: 'numeric' });
    }
  },
    mounted() {
      document.addEventListener("keydown", this.handleEscKey);
    },
    beforeDestroy() {
      document.removeEventListener("keydown", this.handleEscKey);
    },

    fecharPopUp() {
      this.$emit('fechar');
    }
};
</script>

<style scoped>
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
  width: 80%;
  max-width: 1000px;
  max-height: 85vh;
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