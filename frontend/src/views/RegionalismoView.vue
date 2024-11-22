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
import '@/assets/base.css';

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
