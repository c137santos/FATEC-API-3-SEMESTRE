<template>
    <div class="wrapper">
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


        <div>
            <h2 class="mtb-medium">Tags com regionalismos conectados</h2>
            <div v-if="tagList.length > 0">
                <div v-for="(tag, index) in tagList" :key="index" class="tag-section">
                    <h4>Tag: {{ tag.tagNome }}</h4>
                    
                    <div class="regionalismo-list">
                        <div v-if="tag.regionalismos.length > 0" v-for="(regionalismo, idx) in tag.regionalismos" :key="idx" class="regionalismo-item">
                            <div class="regionalismo-nome">
                                {{ regionalismo.nome }}
                            </div>
                            <div class="regionalismo-editar">
                                <CerbButton fill-type="mute" @click="editarRegionalismo(tag, regionalismo)">Editar</CerbButton>
                            </div>
                        </div>
                        <div v-else>Nenhum regionalismo cadastrado</div>
                    </div>
                </div>
            </div>
        </div>

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
        await fetch();  // Atualiza a lista
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
</style>
