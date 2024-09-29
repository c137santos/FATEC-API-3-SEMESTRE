<template>
  
  <div class="api-views">
    <AdicionarApi/>
  </div>
  
  <div class="apis-view">
    <h1>APIs Cadastradas</h1>
    <apisListar></apisListar>
  </div>

  </template>
  
<script>
import apisListar from '@/components/apisListar.vue';
import AdicionarApi from '@/components/AdicionarApi.vue';


export default {
  components: {
    AdicionarApi,
    apisListar,
  },
};
</script>
  
<style scoped>
.apis-view {
  padding: 20px;
}
</style>
  