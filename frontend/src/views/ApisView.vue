

<template>
  <div class="api-views">    
    <!-- Chama o componente AdicionarApi -->
    <AdicionarApi @api-salva="handleApiSalva" />

    <ListarApi />
    
    <!-- Você pode adicionar outros elementos ou componentes aqui -->
  </div>
</template>

<script>
import AdicionarApi from '@/components/AdicionarApi.vue';
import ListarApi from "@/components/ListarApi.vue";

export default {
  
  name: 'ListarApiView',
      components: {
        ListarApi
      },

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
     


<script>
    
</script>

