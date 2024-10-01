<template>
  <div class="portal-cadastrado">
    <div class="novo-portal">
      <button class="cadastrar-portal-novo" @click="toggleNovoPortalForm">Cadastrar Portal</button>

      <div v-if="exibirNovaTagForm" class="formulario-cadastro-portal">
        <form @submit.prevent="validadorDadosNovoPortal">
            <img class="imagem-logo" src="@/assets/Logo_padrao.jpeg">
            <input
                class="campo-cadastro-nome"
                type="text"
                v-model="novoPortal.portalNome"
                placeholder="Nome do Portal"
            />
            <input
                class="campo-cadastro-url"
                type="url"
                v-model="novoPortal.portalUrl"
                placeholder="Url do Portal"
            />
        </form>
          <div>
            <label class="checkbox-portal">Ativo</label>
            <input
                class="checkAtivoPortal"
                type="checkbox"
                v-model="novoPortal.portalAtivo"
            />
          </div>
          <div>
            <p>Tags: </p>
            <select class="seletorTags">
                <ListaTags/>
            </select>
          </div>
          <button class="botao-salvar-portal" type="submit">Salvar</button>
          <button class="botao-cancelar-portal" @click.prevent="cancelarCadastro">Cancelar</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
import ListaTags from '@/components/ListaTags.vue',
name: ListaTags,
    data() {
            return {
                portais: [],
                novoPortal: {portalNome: '', portalUrl: '', portalAtivo: true},
                exibirNovaTagForm: false,
                selectPortal: ''
            }
        },

        methods: {
            toggleNovoPortalForm() {
                this.exibirNovaTagForm = !this.exibirNovaTagForm;
            },

        salvarPortal() {
            const resquestionOption = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(this.novoPortal),
            }
            fetch('http://localhost:8080/portais/cadastrar', resquestionOption).then((Response) =>
                response.json()
                )
        },

        validadorDadosNovoPortal() {
            if(this.novoPortal.portalNome && this.novoPortal.portalUrl) {
                this.salvarPortal()
                let portalSerSalvo = {... this.novoPortal}
                this.$emit('novo-portal', portalSerSalvo)
                this.novoPortal.portalUrl = ''
                this.novoPortal.portalNome = ''
                this.novoPortal.portalAtivo = true
                this.exibirNovaPortalForm = false
            }
        },

        cancelarCadastro() {
            this.novoPortal = {portalNome: '', portalUrl: '', portalAtivo: true}
            this.exibirNovaPortalForm = fase
        }
    }
}

</script>

<style scoped>

.portal-cadastrado {
    margin-top: 10px;
    width: 100%;
}

/* Formulário de cadastro de novo portal */
.formulario-cadastrar-portal {
    width: 700px;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 20px;
    margin-top: 20px;
    background-color: #f9f9f9;
}

/* Imagem que é o ícone do lado das tags */
.imagem-logo {
    width: 25px;
    margin-right: 35px;
}

.campo-cadastro-nome,
.campo-cadastro-url {
    display: block;
    width: 600px;
    padding: 8px;
    margin-bottom: 15px;
    border-radius: 5px;
    background-color: #e4ceff;
}

.checkbox-portal {
    margin-top: 20px;
}

.seletorTags {
    width: 150px;
    height: 20px;
}

/* Botões de salvar, cancelar e cadastrar */
.botao-salvar-portal,
.botao-cancelar-portal,
.cadastrar-portal-novo {
    padding: 8px 16px;
    margin-top: 10px;
    color: white;
    border-radius: 8px;
    cursor: pointer;
    font-size: 14px;
}

/* Botão cadastrar nova tag */
.cadastrar-portal-novo {
    background-color: black;
    margin-bottom: 20px;
}

/* Botão salvar */
.botao-salvar-portal {
    background-color: rgb(141, 107, 207);
}

/* Botão cancelar */
.botao-cancelar-portal {
    color: mediumpurple;
    border: none;
    background-color: white;
}
</style>