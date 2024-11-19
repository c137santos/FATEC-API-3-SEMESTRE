<template>
    <div class="wrapper">
        <!-- Botão para Exibir o Formulário -->
        <div class="d-flex justify-between align-center mtb-medium">
            <CerbButton @click="toggleFormulario" class="botao-grande">
                {{ exibirFormulario ? 'Fechar' : 'Cadastrar Regionalismo' }}
            </CerbButton>
        </div>

        <!-- Formulário de Cadastro -->
        <div v-if="exibirFormulario" class="d-flex plr-medium ptb-small flex-column border-1">
            <h4>Regionalismo</h4>
            <span class="d-flex mtb-small justify-stretch">
                <input v-model="regionalismoInput" class="modal-input w-100 plr-small h-medium" type="text" placeholder="Digite o nome do regionalismo" />
            </span>
            <div class="d-flex flex-column mtb-small w-33">
                <div>Tags relacionadas</div>
                <select v-model="tagSelect" class="modal-select h-medium plr-small mtb-small">
                    <option value="" disabled>Selecione uma tag</option>
                    <option v-for="(tag, index) in tagList" :key="index" :value="tag.tagId"> 
                        {{ tag.tagNome }} 
                    </option>
                </select>
            </div>
            <div class="mtb-small d-flex flex-gap-1">
                <CerbButton :disabled="!regionalismoInput || !tagSelect" @click="save" class="salvar-btn">Salvar</CerbButton>
                <CerbButton fill-type="revert" @click="resetFields" class="cancelar-btn">Cancelar</CerbButton>
            </div>
        </div>

        <!-- Listagem de Regionalismos -->
        <div>
            <h2 class="mtb-medium">Regionalismos Cadastrados</h2>
            <div v-if="tagList.length > 0">
                <div v-for="(tag, index) in tagList" :key="index" class="tag-section">
                    <h4>Tag: {{ tag.tagNome }}</h4>
                    
                    <div class="regionalismo-list">
                        <div v-if="tag.regionalismos.length > 0" v-for="(regionalismo, idx) in tag.regionalismos" :key="idx" class="regionalismo-item">
                            <div class="regionalismo-nome">
                                {{ regionalismo.nome }}
                            </div>
                            <div class="regionalismo-editar">
                                <CerbButton fill-type="mute" @click="editarRegionalismo(tag, regionalismo)" class="botao-grande">Editar</CerbButton>
                            </div>
                        </div>
                        <div v-else>Nenhum regionalismo cadastrado</div>
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

const showModal = ref(false);
const regionalismoEdit = ref<any>(null);

const exibirFormulario = ref(false);

const toggleFormulario = () => {
    exibirFormulario.value = !exibirFormulario.value;
    if (!exibirFormulario.value) {
        resetFields();
    }
};

const resetFields = () => {
    regionalismoInput.value = '';
    tagSelect.value = null;
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
        id: regionalismo.regId,
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
.border-1 {
    border: 1px solid #cdc6d8;
}
.d-flex {
    display: flex;
}
.flex-gap-1 {
    gap: 1rem;
}
.flex-column {
    flex-direction: column;
}
.justify-stretch {
    justify-content: stretch;
}
.justify-between {
    justify-content: space-between;
}
.align-center {
    align-items: center;
}
.w-100 {
    width: 100%;
}
.w-33 {
    width: 33%;
}
.h-medium {
    height: 2rem;
}
.bg-transparent {
    background-color: transparent;
}

/* Estilos de Lista e Agrupamento */
.tag-section {
    margin-bottom: 1rem;
    padding: 1rem;
    border: 1px solid #ccc;
    border-radius: 8px;
}
.regionalismo-list {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}
.regionalismo-item {
    display: flex;
    justify-content: space-between;
    padding: 0.5rem;
    border: 1px solid #eee;
    border-radius: 5px;
}
.regionalismo-nome {
    flex-grow: 1;
}
.regionalismo-editar {
    margin-left: 1rem;
}

/* Estilos para o botão */
.botao-grande {
    height: 2rem;
    font-size: 0.9rem;
    background-color: #6a1b9a; /* Cor do botão editado */
    color: white;
    border-radius: 5px;
    padding: 0.5rem 1rem;
    display: inline-block;
    transition: background-color 0.3s;
}

.botao-grande:hover {
    background-color: #4a148c; /* Cor do botão no hover */
}

.salvar-btn {
    background-color: #6a1b9a; /* Cor do botão Salvar */
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
}

.salvar-btn:hover {
    background-color: #4a148c; /* Cor do botão Salvar no hover */
}

.cancelar-btn {
    background-color: transparent;
    color: #6a1b9a;
    padding: 10px 20px;
    cursor: pointer;
    font-size: 16px;
    border-radius: 5px;
    border: 2px solid #6a1b9a; /* Cor da borda do botão Cancelar */
}

.cancelar-btn:hover {
    text-decoration: underline;
}

/* Estilo do campo de input e select */
.modal-input, .modal-select {
    border: 1px solid #d1c4e9;
    border-radius: 5px;
    padding: 12px;
    margin-bottom: 20px;
    font-size: 16px;
    background-color: #f3e5f5; /* Cor do fundo dos campos */
}
</style>
