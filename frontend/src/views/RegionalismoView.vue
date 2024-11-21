<template>
    <div class="wrapper">
        <!-- Botão Cadastrar Regionalismo -->
        <div class="botao-cadastrar-container">
            <button class="btn-show-form" @click="toggleFormulario">Cadastrar Regionalismo</button>
        </div>

        <!-- Formulário de Cadastro -->
        <div v-if="showCadastro" class="formulario-cadastro">
            <div class="info-icon">
                <span>ⓘ</span>
            </div>
            <h2>Cadastrar Regionalismo</h2>
            <h4>Nome do regionalismo</h4>
            <span class="input-container">
                <input v-model="regionalismoInput" class="input-text" type="text" placeholder="Digite o nome do regionalismo" />
            </span>
            <div class="input-container">
                <div>Tags relacionadas</div>
                <select v-model="tagSelect" class="input-select">
                    <option value="" disabled>Selecione uma tag</option>
                    <option v-for="(tag, index) in tagList" :key="index" :value="tag.tagId"> 
                        {{ tag.tagNome }} 
                    </option>
                </select>
            </div>
            <div class="botoes-container">
                <CerbButton :disabled="!regionalismoInput || !tagSelect" @click="save">Salvar</CerbButton>
                <CerbButton fill-type="revert" @click="resetFields">Cancelar</CerbButton>
            </div>
        </div>

        <!-- Listagem de Regionalismos -->
        <div>
            <h2 class="mtb-medium">Tags com regionalismos conectados</h2>
            <div v-if="tagList.length > 0" class="tag-grid">
                <div v-for="(tag, index) in tagList" :key="index" class="tag-section">
                    <div class="tag-header">
                        <h4>Tag: {{ tag.tagNome }}</h4>
                    </div>
                    <div class="regionalismo-list">
                        <div v-if="tag.regionalismos.length > 0" v-for="(regionalismo, idx) in tag.regionalismos" :key="idx" class="regionalismo-item">
                            <div class="regionalismo-nome">
                                {{ regionalismo.nome }}
                            </div>
                            <div class="regionalismo-editar">
                                <CerbButton fill-type="mute" @click="editarRegionalismo(tag, regionalismo)" class="botao-grande">Editar</CerbButton>
                            </div>
                        </div>
                        <div v-else class="no-regionalismo">Nenhum regionalismo cadastrado</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal de Edição -->
        <ModalEdicaoRegionalismo
            v-if="showModal"
            :regionalismo="regionalismoEdit"
            :tags="tagList"
            @salvar-edicao="salvarEdicao"
            @fechar="showModal = false"
        />
    </div>
</template>

<script setup lang="ts">
import CerbButton from '@/components/CerbButton.vue';
import ModalEdicaoRegionalismo from '@/components/ModalEdicaoRegionalismo.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue';

const tagList = ref<any[]>([]); 
const regionalismoInput = ref('');
const tagSelect = ref<string | null>(null);

const showCadastro = ref(false);
const showModal = ref(false);
const regionalismoEdit = ref<any>(null);

const exibirFormulario = ref(false);

// Método para alternar a exibição do formulário
const toggleFormulario = () => {
    exibirFormulario.value = !exibirFormulario.value;
    showCadastro.value = exibirFormulario.value; // Atualiza a variável para exibir ou esconder o formulário
    if (!exibirFormulario.value) {
        resetFields();
    }
};

const resetFields = () => {
    regionalismoInput.value = '';
    tagSelect.value = null;
    showCadastro.value = false;
};

const fetch = async () => {
    try {
        const tagsResponse = await axios.get('http://localhost:8080/tags/listar');
        tagList.value = tagsResponse.data;
    } catch (error) {
        console.error('Erro ao buscar dados:', error);
    }
};

const save = async () => {
    try {
        if (!tagSelect.value || !regionalismoInput.value) {
            console.warn('Por favor, preencha todos os campos antes de salvar.');
            return;
        }

        const response = await axios.post('http://localhost:8080/regionalismos/cadastrar', {
            tagId: tagSelect.value,
            nome: regionalismoInput.value
        });
        
        console.log('Regionalismo salvo:', response.data);
        await fetch();
        resetFields();
        exibirFormulario.value = false;
    } catch (error) {
        console.error('Erro ao salvar regionalismo:', error);
    }
};

const editarRegionalismo = (tag: any, regionalismo: any) => {
    regionalismoEdit.value = { 
        id: regionalismo.regId, // Corrigido: Apenas uma propriedade 'id'
        nome: regionalismo.nome, 
        tagId: regionalismo.tagId 
    };

    showModal.value = true;
};

const salvarEdicao = async (regionalismoAtualizado: any) => {
    console.log(regionalismoAtualizado);
    try {
        const response = await axios.put(
            `http://localhost:8080/regionalismos/editar/${regionalismoAtualizado.id}`,
            regionalismoAtualizado
        );
        console.log('Edição salva com sucesso:', response.data);
        showModal.value = false;
        await fetch();
    } catch (error) {
        console.error('Erro ao salvar edição:', error);
    }
};

onMounted(() => {
    fetch();
});
</script>

<style scoped>
.wrapper {
    padding: 1rem;
}

.botao-cadastrar-container {
    margin-bottom: 1rem;
}

.btn-show-form {
  padding: 8px 16px;
  margin-top: 10px;
  color: white; /* Cor do texto do botão */
  background-color: black; /* Cor preta para o botão "Cadastrar Regionalismo" */
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

.formulario-cadastro {
    background-color: #f5f5f5;
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 8px;
}

.info-icon {
    font-size: 1.5rem;
    background-color: #fff;
    color: #000;
    border-radius: 50%;
    padding: 0.3rem;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 0.5rem;
}

h2 {
    font-size: 1.2rem;
    font-weight: bold;
    margin-top: 0;
}

h4 {
    margin-bottom: 0.3rem;
}

.input-container {
    margin-bottom: 1rem;
}

.input-text,
.input-select {
    width: 100%;
    height: 2.5rem;
    padding: 0.5rem;
    border: 1px solid #cdc6d8;
    border-radius: 8px;
    background-color: #e9d8fd;
}

.botoes-container {
    display: flex;
    gap: 0.5rem;
}

CerbButton {
    border-radius: 20px;
}

.tag-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 1rem;
    margin-top: 1.5rem;
}

.tag-section {
    background-color: #f5f5f5;
    border: 1px solid #ddd;
    padding: 1rem;
    border-radius: 8px;
}

.tag-header {
    font-size: 1.1rem;
    font-weight: bold;
    margin-bottom: 1rem;
}

.regionalismo-list {
    margin-top: 1rem;
}

.regionalismo-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
    padding: 0.5rem;
    background-color: #f0f0f0;
    border-radius: 8px;
}

.regionalismo-nome {
    font-size: 1rem;
}

.regionalismo-editar {
    margin-left: 1rem;
}

.no-regionalismo {
    text-align: center;
    color: #888;
    font-size: 0.9rem;
}
</style>
