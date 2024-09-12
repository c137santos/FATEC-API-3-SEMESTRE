<template>
  <div class="api-form-container">
    <div class="header">
      <h2>Cadastrar nova API</h2>
    </div>
    <form @submit.prevent="saveApi">
      <div class="form-group">
        <label for="name">Nome da API:</label>
        <input
          type="text"
          id="name"
          v-model="apiName"
          required
          placeholder="Digite o nome da API"
        />
      </div>

      <div class="form-group">
        <label for="url">URL da API:</label>
        <input
          type="url"
          id="url"
          v-model="apiUrl"
          required
          placeholder="Digite a URL da API"
        />
      </div>

      <div class="form-group form-checkbox">
        <label for="active">Ativa:</label>
        <input type="checkbox" id="active" v-model="apiActive" checked />
      </div>

      <div class="form-group">
        <label for="capturePeriod">Período de Captura:</label>
        <select v-model="capturePeriod" id="capturePeriod">
          <option value="diario">Diariamente</option>
          <option value="semanal">Semanalmente</option>
          <option value="mensal">Mensalmente</option>
        </select>
      </div>

      <div class="button-group">
        <button type="submit" class="btn save">Salvar</button>
        <button type="button" class="btn cancel" @click="cancel">Cancelar</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      apiName: '',
      apiUrl: '',
      apiActive: true,
      capturePeriod: 'diario',
    };
  },
  methods: {
    saveApi() {
      const apiData = {
        name: this.apiName,
        url: this.apiUrl,
        active: this.apiActive,
        capturePeriod: this.capturePeriod,
      };
      console.log('API salva:', apiData);
    },
    cancel() {
      this.$router.push('/api-list'); // Volta para a listagem de APIs
    },
  },
};
</script>

<style scoped>
.api-form-container {
  background-color: #f4f1f9;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  padding: 20px;
  margin: 20px auto;
  width: 800px;
  height: 800px;
}

.header h2 {
  font-family: 'Arial', sans-serif;
  font-size: 24px;
  margin-bottom: 20px;
  color: #4a4a4a;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
}

.form-checkbox {
  display: block;
  align-items: start;
}

.form-checkbox label {
  margin-right: 10px;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.btn {
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.btn.save {
  background-color: #6c63ff;
  color: white;
  border: none;
}

.btn.cancel {
  background-color: #e0e0e0;
  color: #333;
  border: none;
}
</style>
