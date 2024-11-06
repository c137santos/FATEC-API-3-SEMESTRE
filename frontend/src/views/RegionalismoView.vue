<script setup lang="ts">
import CerbButton from '@/components/CerbButton.vue';
import ModalEdicaoRegionalismo from '@/components/ModalEdicaoRegionalismo.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue';

const tagList = ref<any[]>([]); // Lista de tags
const regionalismoInput = ref(''); // Input para o nome do regionalismo
const tagSelect = ref<string | null>(null); // Tag selecionada

const showModal = ref(false); // Controla a exibição do modal de edição
const regionalismoEdit = ref<any>(null); // Regionalismo que será editado

// Função para resetar os campos do formulário de criação
const resetFields = () => {
    regionalismoInput.value = '';
    tagSelect.value = null;
};

// Função para buscar as tags e regionalismos associados
const fetch = async () => {
    try {
        const tagsResponse = await axios.get('http://localhost:8080/tags/listar');
        tagList.value = tagsResponse.data;
    } catch (error) {
        console.error('Erro ao buscar dados:', error);
    }
};

// Função para salvar um novo regionalismo
const save = async () => {
    try {
        if (!tagSelect.value || !regionalismoInput.value) {
            console.warn('Por favor, preencha todos os campos antes de salvar.');
            return;
        }

        // Envia a requisição para salvar o novo regionalismo
        const response = await axios.post('http://localhost:8080/regionalismos/cadastrar', {
            tagId: tagSelect.value,
            nome: regionalismoInput.value
        });
        
        console.log('Regionalismo salvo:', response.data);
        await fetch(); // Recarrega os dados após salvar
        resetFields(); // Limpa os campos após o salvamento
    } catch (error) {
        console.error('Erro ao salvar regionalismo:', error);
    }
};

// Função para abrir o modal de edição
const editarRegionalismo = (regionalismo: any) => {
    regionalismoEdit.value = { ...regionalismo }; // Define o regionalismo que será editado
    showModal.value = true; // Exibe o modal
};

// Função para salvar as edições feitas no modal
const salvarEdicao = async (regionalismoAtualizado: any) => {
    try {
        await axios.put(`http://localhost:8080/regionalismos/editar/${regionalismoAtualizado.id}`, regionalismoAtualizado);
        await fetch(); // Recarrega a lista após salvar
        showModal.value = false; // Fecha o modal
    } catch (error) {
        console.error('Erro ao salvar edição:', error);
    }
};

onMounted(() => {
    fetch();
});
</script>

<template>
    <div class="wrapper">
        <div>
            <div class="d-flex plr-medium ptb-small flex-column border-1">
                <h2>Cadastrar Regionalismos</h2>
                <h4>Regionalismo</h4>
                <span class="d-flex mtb-small justify-stretch">
                    <input v-model="regionalismoInput" class="w-100 plr-small border-1 h-medium" type="text" placeholder="Digite o nome do regionalismo" />
                </span>
                <div class="d-flex flex-column mtb-small w-33">
                    <div>Tags relacionadas</div>
                    <select v-model="tagSelect" class="h-medium plr-small mtb-small border-1 bg-transparent">
                        <option value="" disabled>Selecione uma tag</option>
                        <option v-for="(tag, index) in tagList" :key="index" :value="tag.tagId"> 
                            {{ tag.tagNome }} 
                        </option>
                    </select>
                </div>
                <div class="mtb-small d-flex flex-gap-1">
                    <CerbButton :disabled="!regionalismoInput || !tagSelect" @click="save">Salvar</CerbButton>
                    <CerbButton fill-type="revert" @click="resetFields">Cancelar</CerbButton>
                </div>
            </div>
        </div>
        <div>
            <h2 class="mtb-medium">Tags com regionalismos conectados</h2>
            <div v-if="tagList.length > 0" 
                 class="d-flex align-center plr-medium ptb-small mtb-small justify-between" 
                 v-for="(tag, index) in tagList" :key="index">
                <div>
                    <h4>Tag: {{ tag.tagNome }}</h4>
                    <span>Regionalismos:
                        <span v-if="tag.regionalismos.length > 0">
                            <span v-for="(regionalismo, idx) in tag.regionalismos" :key="idx">
                                <span v-if="idx > 0">, </span>
                                {{ regionalismo.nome }}
                            </span>
                        </span>
                        <span v-else>Nenhum regionalismo cadastrado</span>
                    </span>
                </div>
                <div>
                    <CerbButton fill-type="mute" @click="editarRegionalismo(tag)">Editar</CerbButton>
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
</style>
