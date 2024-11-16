<template>
    <div v-if="noticiaModal" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-actions">
        </div>
        <div class="modal-body">
            <button class="btn-fechar" @click="fecharModal">X</button>
            <h2>{{ noticia.notiText }}</h2>
            <a>{{ noticia.Url }}</a>
            <a>{{ noticia.notiData }}</a>
            <a>Fonte: {{ portal.Nome }}</a>
            <a>Escrito por: {{ reporter.Nome }}</a>
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
      }
    },
    data() {
      return {
        noticiaModal: null,
      };
    },
    methods: {
      abrirModalNoticia(noticia) {
        this.noticiaModal = noticia;
      },
      fecharModal() {
        this.noticiaModal = null;
        this.$emit('fechar');
      },
      handleEscKey(event) {
        if (event.key === 'Escape') {
          this.fecharModal();
        }
      },
    },
    mounted() {
      document.addEventListener('keydown', this.handleEscKey);
    },
    beforeDestroy() {
      document.removeEventListener('keydown', this.handleEscKey);
    },
  };
  </script>
  
<style scoped>

@media (max-width: 1024px) {
  .modal-content {
    width: 70%;
    max-width: 600px;
    padding: 20px; 
  }

  .btn-fechar-noticia {
    padding: 8px 16px;
    font-size: 16px;
  }
}

@media (max-width: 768px) {
  .modal-overlay {
    padding: 10px;
  }

  .modal-content {
    width: 100%; 
    max-width: 400px;
    padding: 15px;
  }
}

  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .modal-content {
    background: white;
    padding: 20px;
    border-radius: 8px;
    width: 80%;
    max-width: 500px;
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
  
  .modal-body {
    margin-top: 15px;
  }
</style>
  