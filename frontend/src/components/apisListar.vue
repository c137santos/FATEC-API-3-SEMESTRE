<template>
  <div>
    <div v-if="apis.length" v-for="api in apis" :key="api.id" class="api-card">
      <div class="api-header">
        <div class="api-details">
          <h3>{{ api.nome }}</h3>
          <p>{{ api.url }}</p>
          <p>{{ api.frequencia }}</p>
        </div>
        <button class="edit-button" @click="editApi(api.id)">editar</button>
      </div>
      <button class="view-data-button" @click="viewCapturedData(api.id)">
        Clique aqui para ver os dados capturados
      </button>
    </div>
    <div v-else>
      Carregando APIs...
    </div>

    <dialog ref="dataDialog" class="data-dialog">
      <div class="dialog-header">
        <h3>API</h3>
      </div>
      <div class="captured-data">
        <div>
          <vue-json-pretty :data="capturedData" />
        </div>
      </div>
      <button @click="closeDialog">Fechar</button>
    </dialog>
  </div>
</template>

<script>
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';


export default {
  data() {
    return {
      apis: [],
      capturedData: [],
    };
  },
  components: {
    VueJsonPretty,
  },
  created() {
    this.fetchApiData();
  },
  methods: {
    fetchApiData() {
    fetch('http://localhost:8081/apis/listar')
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        this.apis = data;
      })
      .catch(error => {
        console.error("Erro ao buscar APIs:", error);
      });
  },

    editApi(id) {
      // TO-DO - parte de editar api
      console.log("Editar API com id:", id);
    },
    viewCapturedData(id) {
  fetch(`http://localhost:8081/apis/resultados?apiId=${id}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      this.capturedData = data;
      this.$refs.dataDialog.showModal();
    })
    .catch(error => {
      console.error("Erro ao buscar dados capturados:", error);
    });
    },
    closeDialog() {
      this.$refs.dataDialog.close();
    },
    formatJson(data) {
      console.log(this.capturedData[0])
      return JSON.stringify(data, null, 2)
        .replace(/\\n/g, '<br>')
        .replace(/ /g, '&nbsp;');
    }
  }
};
</script>

<style scoped>
/* Estilos gerais */
.api-card {
  border: 1px solid #ccc;
  padding: 16px;
  width: 100%;
  margin-top: 16px;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-sizing: border-box;
}

.api-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.api-details {
  display: flex;
  flex-direction: column;
}

.edit-button {
  background-color: #333;
  color: white;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
}

.view-data-button {
  background-color: #e0e0e0;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.view-data-button:hover {
  background-color: #ccc;
}

/* Estilos para o dialog/popup */
.data-dialog {
  width: 75%;
  border: none;
  border-radius: 12px;
  padding: 20px;
  background-color: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;

  /* Centraliza o dialog na tela */
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.dialog-header {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 16px;
}

.dialog-header i.info-icon {
  font-size: 32px;
  margin-right: 10px;
}

.dialog-header h3 {
  font-size: 20px;
  margin-right: 10px;
}

.dialog-header p {
  font-size: 16px;
  margin-left: auto;
}

.captured-data {
  margin-bottom: 20px;
}

.data-item {
  margin-bottom: 16px; /* Espaçamento entre os itens */
}

/* Estilo do botão de fechar */
.close-button {
  background-color: #6f3c91; /* Cor roxa */
  color: white; /* Texto branco */
  border: none; /* Sem borda */
  padding: 10px 20px; /* Padding para aumentar o tamanho */
  border-radius: 8px; /* Borda arredondada */
  cursor: pointer; /* Cursor de ponteiro */
  font-size: 16px; /* Tamanho da fonte */
  transition: background-color 0.3s; /* Transição suave */
}

/* Efeito de hover para o botão de fechar */
.close-button:hover {
  background-color: #5a3277; /* Cor mais escura ao passar o mouse */
}

/* Personaliza o backdrop do dialog */
.data-dialog::backdrop {
  background-color: rgba(0, 0, 0, 0.5); /* Fundo semi-transparente */
}
</style>

