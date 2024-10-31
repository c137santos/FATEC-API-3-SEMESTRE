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

const errorMessage = ref('');

const resetFields = () => {
    regionalismoInput.value = '';
    tagSelect.value = null;
};

const fetch = async () => {
    try {
        const tagsResponse = await axios.get('http://localhost:8080/tags/listar');
        tagList.value = tagsResponse.data;
        errorMessage.value = '';
    } catch (error) {
        console.error('Erro ao buscar dados:', error);
        errorMessage.value = 'Erro ao conectar com o servidor. Verifique se o backend está ativo.';
    }
};

const save = async () => {
    try {
        if (!tagSelect.value || !regionalismoInput.value) {
            console.warn('Por favor, preencha todos os campos antes de salvar.');
            return;
        }

        await axios.post('http://localhost:8080/regionalismo/cadastrar', {
            tagId: tagSelect.value,
            nome: regionalismoInput.value
        });
        await fetch();
    } catch (error) {
        console.error('Erro ao salvar regionalismo:', error);
    }
};

const editarRegionalismo = (regionalismo: any) => {
    regionalismoEdit.value = { ...regionalismo };
    showModal.value = true;
};

const salvarEdicao = async (regionalismoAtualizado: any) => {
    try {
        await axios.put(`http://localhost:8080/regionalismos/editar/${regionalismoAtualizado.id}`, regionalismoAtualizado);
        await fetch();
        showModal.value = false;
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
        <!-- Mensagem de erro condicional -->
        <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

        <div>
            <div class="d-flex plr-medium ptb-small flex-column border-1">
                <h2>Cadastrar Regionalismos</h2>
                <h4>Regionalismo</h4>
                <span class="d-flex mtb-small justify-stretch">
                    <input v-model="regionalismoInput" class="w-100 plr-small border-1 h-medium" type="text" />
                </span>
                <div class="d-flex flex-column mtb-small w-33">
                    <div>Tags relacionadas</div>
                    <select v-model="tagSelect" class="h-medium plr-small mtb-small border-1 bg-transparent">
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
                                <CerbButton fill-type="mute" @click="editarRegionalismo(regionalismo)">Editar</CerbButton>
                            </span>
                        </span>
                        <span v-else>Nenhum regionalismo cadastrado</span>
                    </span>
                </div>
            </div>
        </div>

        <!-- Modal de edição -->
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
.error-message {
    color: red;
    font-weight: bold;
    margin: 1rem 0;
}
</style>
