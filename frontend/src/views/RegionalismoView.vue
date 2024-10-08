<script setup lang="ts">
import CerbButton from '@/components/CerbButton.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue';

const tagList = ref<any[]>([])

const relatedTagList = ref<any[]>([])
const relatedGroupRecord = ref<Record<string, any>>({})

const regionalismoInput = ref()
const tagSelect = ref()

const resetFields = () => {
    regionalismoInput.value = ''
    tagSelect.value = ''
}

const groupBy = <T>(array: T[], predicate: (value: T, index: number, array: T[]) => string) =>
    array.reduce((acc, value, index, array) => {
        (acc[predicate(value, index, array)] ||= []).push(value);
        return acc;
    }, {} as { [key: string]: T[] });

const fetch = () => {
    axios.get('http://localhost:8080/tags/listar').then((res) => {
        tagList.value = res.data
    })
    axios.get('http://localhost:8080/regionalismo/listar').then((res) => {
        relatedTagList.value = res.data
        relatedGroupRecord.value = groupBy(res.data, (data: any) => data.tag.tagNome)
    })
}

const save = () => {
    axios.post('http://localhost:8080/regionalismo/cadastrar', {
        tagId: tagSelect.value,
        nome: regionalismoInput.value
    }).then(() => fetch())
}

onMounted(() => {
    fetch()
})

</script>

<template>
    <div class="wrapper">
        <div>
            <div class="d-flex plr-medium ptb-small flex-column border-1">
                <h2>Cadastar Regionalismos</h2>
                <h4>Regionalismo</h4>
                <span class="d-flex mtb-small justify-stretch">
                    <input v-model="regionalismoInput" class="w-100 plr-small border-1 h-medium" type="text"/>
                </span>
                <div class="d-flex flex-column mtb-small w-33">
                    <div>Tag relacionadas</div>
                    <select v-model="tagSelect" class="h-medium plr-small mtb-small border-1 bg-trasparent">
                        <option v-for="(tag, i) in tagList" :key="i" :value="tag.tagId"> {{ tag.tagNome }} </option>
                    </select>
                </div>
                <div class="mtb-small d-flex flex-gap-1">
                    <CerbButton :disabled="!regionalismoInput || !tagSelect" @click="save">Salvar</CerbButton>
                    <CerbButton fill-type="revert" @click="resetFields">Cancelar</CerbButton>
                </div>    
            </div>
        </div>
        <div>
            <h2 class="mtb-medium">Tags com regionalismo conectados</h2>
            <div class="d-flex align-center plr-medium ptb-small mtb-small justify-between border-1" v-for="(tagName, i) in Object.keys(relatedGroupRecord)" :key="i">
                <div>
                    <h4>Tag: {{ tagName }}</h4>
                    <span>Regionalismos:
                        <span v-for="regionalismo, i in relatedGroupRecord[tagName]" :key="i">
                            <span v-if="i > 0">,</span>
                            {{ regionalismo.nome }}
                        </span>
                    </span>
                </div>
                <div>
                    <CerbButton fill-type="mute">Editar</CerbButton>
                </div>
            </div>
        </div>
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
    width: 33%
}
.h-medium {
    height: 2rem;
}
.bg-trasparent {
    background-color: transparent;
}
</style>