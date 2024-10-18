<template>
  <div>
    <AdicionarTag @nova-tag="adicionarTag" />
    <ListaTags :tags="tags" @editar-tag="iniciarEdicao" />
    <ModalEdicaoTag
      v-if="tagEmEdicao"
      :tag="tagEmEdicao"
      @salvar-edicao="salvarEdicao"
      @fechar="fecharModal"
    />
  </div>
</template>

<script>
import AdicionarTag from '@/components/AdicionarTag.vue'
import ListaTags from '@/components/ListaTags.vue'
import ModalEdicaoTag from '@/components/ModalEdicaoTag.vue'

export default {
  name: 'TagView',
  components: {
    AdicionarTag,
    ListaTags,
    ModalEdicaoTag
  },
  data() {
    return {
      tags: [],
      tagEmEdicao: null
    }
  },
  mounted() {
    this.carregarTags()
  },
  methods: {
    async carregarTags() {
      try {
        const response = await fetch('http://localhost:8080/tags/listar')
        if (!response.ok) {
          throw new Error('Falha ao carregar tags do backend')
        }
        const tags = await response.json()
        this.tags = tags
      } catch (error) {
        alert('Erro ao carregar as tags. Tente novamente mais tarde.')
      }
    },

    adicionarTag(novaTag) {
      this.tags.push(novaTag)
    },
    iniciarEdicao(tag) {
      const plainTag = JSON.parse(JSON.stringify(tag))
      if (!plainTag.tagId) {
        console.error('Tag selecionada não tem ID:', plainTag)
      } else {
        console.log('Tag selecionada para edição:', plainTag)
      }
      this.tagEmEdicao = { ...plainTag }
      console.log('Tag em edição:', this.tagEmEdicao)
    },
    async salvarEdicao(tagEditada) {
      try {
        const tagId = tagEditada.tagId
        if (!tagId) {
          console.error('Tag ID não definido!', tagEditada)
          alert('ID da tag não encontrado.')
          return
        }

        const response = await fetch(`http://localhost:8080/tags/editar/${tagId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(tagEditada)
        })

        if (response.ok) {
          const tagAtualizada = await response.json()
          console.log('Tag salva com sucesso:', tagAtualizada)

          const index = this.tags.findIndex((tag) => tag.tagId === tagAtualizada.tagId)
          if (index !== -1) {
            this.tags.splice(index, 1, tagAtualizada) // Atualiza a tag na lista
          }
          this.fecharModal()
        } else {
          alert('Erro ao salvar a tag. Tente novamente.')
          console.error('Erro ao salvar a tag:', response.statusText)
        }
      } catch (error) {
        alert('Erro ao salvar a tag. Tente novamente.')
        console.error('Erro ao salvar a tag:', error)
      }
    },

    fecharModal() {
      this.tagEmEdicao = null
    }
  }
}
</script>
