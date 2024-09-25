

<template>
  <div class="api-views">
    <h1>Gerenciamento de APIs</h1>
    
    <!-- Chama o componente AdicionarApi -->
    <AdicionarApi @api-salva="handleApiSalva" />
    
    <!-- Você pode adicionar outros elementos ou componentes aqui -->
  </div>
</template>

<script>
import AdicionarApi from '../components/AdicionarApi.vue';

export default {
  components: {
    AdicionarApi,
  },
  methods: {
    handleApiSalva(apiData) {
      // Aqui você pode tratar o evento de API salva
      console.log('API salva:', apiData);
    },
  },
};
</script>

<style scoped>
.api-views {
  padding: 20px;
  font-family: 'SF Pro', sans-serif;
}
</style>
    <ListarApi />
</template>


<script>
    import ListarApi from "@/components/ListarApi.vue";
    export default {
      name: 'ListarApiView',
      components: {
        ListarApi
      }
    }
</script>

