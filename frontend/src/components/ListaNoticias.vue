<template>
    <div>
        <h2>Notícias Cadastradas</h2>
        <div v-if="noticias.length > 0">
            <div v-for="noticia in noticias" :key="noticia.id" class="noticia-item">
                <div class="noticia-info">
                    <h3>{{ noticia.notiText }}</h3>
                    <p>{{ noticia.notiData }}</p>
                    <p>{{ noticia.url }}</p>
                    <p>{{ noticia.reporte }}</p>
                </div>
                <button @click="abrirModalNoticia" class="noticia-btn">Ler Notícia</button>
            </div>
        </div>
        <p v-else>Nenhuma notícia cadastrada.</p>
    </div>
</template>

<script>
export default {
    props: {
        noticias: Array,
    },
    methods: {
        abrirModalNoticia(noticia) {
            this.$emit('ler-noticia', noticia)
        }
    }
}
</script>

<style scoped>
.tag-item {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #f5f5f5;
  padding: 15px;
  margin-top: 10px;
  border-radius: 8px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.editar-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: #2c2c2c;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
}

.editar-btn:hover {
  background-color: #45a049;
}

.tag-info h3 {
  margin: 0;
  font-size: 1.2rem;
}

.tag-info p {
  margin: 5px 0;
  font-size: 0.9rem;
}
</style>
