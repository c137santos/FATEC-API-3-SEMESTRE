### Entidade: ApiPublica

Esta entidade representa uma API pública, armazenando informações essenciais para sua configuração e utilização, como nome, frequência de uso, descrição, estado de atividade e URL.

#### Campos

1. **id** (`api_id`)
   - **Tipo:** `Long`
   - **Descrição:** Identificador único da API pública. Gerado automaticamente.
   - **Anotações:**
     - `@Id`: Indica que é a chave primária da tabela.

2. **nome** (`api_nome`)
   - **Tipo:** `String`
   - **Descrição:** Nome da API pública.
   - **Restrição:** Não pode ser nulo (`nullable = false`).
   - **Exemplo:** "API de Consulta de Tempo".

3. **frequencia** (`api_frequencia`)
   - **Tipo:** `String`
   - **Descrição:** Frequência com que a API é consultada. Pode indicar intervalos de tempo, como "diária" ou "semanal".
   - **Restrição:** Não pode ser nulo (`nullable = false`).
   - **Comportamento Especial:** Convertido para letras minúsculas ao ser atribuído no construtor.

4. **descricao** (`api_descricao`)
   - **Tipo:** `String`
   - **Descrição:** Descrição detalhada da API, incluindo informações adicionais sobre seu propósito ou funcionalidade.
   - **Restrição:** Opcional (pode ser nulo).

5. **active** (`api_active`)
   - **Tipo:** `Boolean`
   - **Descrição:** Indica se a API está ativa e disponível para uso.
   - **Valores Possíveis:**
     - `true`: API está ativa.
     - `false`: API está inativa.

6. **url** (`api_url`)
   - **Tipo:** `String`
   - **Descrição:** URL base da API pública.
   - **Restrição:** 
     - Tamanho máximo: 2048 caracteres.
   - **Exemplo:** "https://api.openweathermap.org".

### Entidade: Noticia

Esta entidade representa uma notícia capturada pelo crawler, contendo informações como data, texto, URL, portal de origem, autor (repórter) e suas tags associadas.

#### Campos

1. **notiId** (`noti_id`)
   - **Tipo:** `Long`
   - **Descrição:** Identificador único da notícia. Gerado automaticamente.
   - **Anotações:**
     - `@Id`: Define o campo como chave primária.

2. **notiData** (`noti_data`)
   - **Tipo:** `LocalDate`
   - **Descrição:** Data de publicação da notícia ou data de captura pelo crawler.
   - **Exemplo:** `2024-11-30`.

3. **notiText** (`noti_text`)
   - **Tipo:** `String`
   - **Descrição:** Texto completo da notícia, incluindo o conteúdo principal capturado pelo crawler.
   - **Restrição:** Opcional (pode ser nulo).
   - **Exemplo:** "Hoje, um novo marco foi atingido no setor tecnológico..."

4. **url** (`noti_url`)
   - **Tipo:** `String`
   - **Descrição:** URL original da notícia no portal de origem.
   - **Restrição:** 
     - Tamanho máximo: 2048 caracteres.
   - **Exemplo:** `https://www.noticias.com.br/artigo/tecnologia-2024`.

5. **porId** (`por_id`)
   - **Tipo:** `Long`
   - **Descrição:** Identificador do portal de origem da notícia. Este campo é uma chave estrangeira para a entidade `Portal`.
   - **Relacionamento:** Relacionado ao campo `por_id` da tabela `portal`.
   - **Nota:** Marcado como não inserível e não atualizável, pois o relacionamento direto é tratado pelo campo `portal`.

6. **portal** (`Portal`)
   - **Tipo:** `Portal`
   - **Descrição:** Relacionamento com o portal de origem da notícia.
   - **Anotações:**
     - `@ManyToOne`: Define um relacionamento muitos-para-um.
     - `@JoinColumn`: Configura a coluna de junção (`por_id`).

7. **reporte** (`Reporter`)
   - **Tipo:** `Reporter`
   - **Descrição:** Relacionamento com o repórter ou autor da notícia.
   - **Relacionamento:**
     - `@ManyToOne`: Define um relacionamento muitos-para-um.
     - `@JoinColumn(name = "rep_id", referencedColumnName = "rep_id")`: Configura a coluna de junção com a entidade `Reporter`.

8. **tagNoticia** (`TagNoticia`)
   - **Tipo:** `Set<TagNoticia>`
   - **Descrição:** Conjunto de tags associadas à notícia, usadas para categorização e pesquisa.
   - **Relacionamento:**
     - `@OneToMany(mappedBy = "notiId")`: Relacionamento um-para-muitos com a entidade `TagNoticia`, mapeando pelo campo `notiId`.

#### Relações

- **Portal**
  - Relacionamento com a entidade `Portal` para identificar o portal de origem da notícia.
- **Reporter**
  - Relacionamento com a entidade `Reporter`, indicando o autor da notícia.
- **TagNoticia**
  - Conjunto de tags associadas à notícia, representando a categorização.

---

### Entidade: Portal

Esta entidade representa um portal de notícias, armazenando informações básicas e metadados relevantes para identificar e configurar o uso do portal.

#### Campos

1. **id** (`por_id`)
   - **Tipo:** `Long`
   - **Descrição:** Identificador único do portal. Gerado automaticamente.
   - **Anotações:**
     - `@Id`: Define o campo como chave primária.

2. **ativo** (`por_ativo`)
   - **Tipo:** `boolean`
   - **Descrição:** Indica se o portal está ativo para coleta de dados ou monitoramento.
   - **Valores Possíveis:**
     - `true`: Portal ativo.
     - `false`: Portal inativo.

3. **nome** (`por_nome`)
   - **Tipo:** `String`
   - **Descrição:** Nome do portal.
   - **Restrição:**
     - Tamanho máximo: 30 caracteres.
   - **Exemplo:** "Portal Notícias Brasil".

4. **url** (`por_url`)
   - **Tipo:** `String`
   - **Descrição:** URL base do portal de notícias.
   - **Restrição:** 
     - Tamanho máximo: 2048 caracteres.
   - **Exemplo:** `https://www.noticiasbrasil.com`.

5. **data** (`por_data`)
   - **Tipo:** `LocalDate`
   - **Descrição:** Data de criação ou registro do portal na base de dados.
   - **Exemplo:** `2024-11-30`.

6. **frequencia** (`por_frequencia`)
   - **Tipo:** `String`
   - **Descrição:** Frequência com que os dados do portal são coletados ou monitorados.
   - **Restrição:** 
     - Tamanho máximo: 30 caracteres.
   - **Exemplo:** "Diário", "Semanal".

7. **tagPortals** (`TagPortal`)
   - **Tipo:** `List<TagPortal>`
   - **Descrição:** Lista de tags associadas ao portal para categorização ou metadados adicionais.
   - **Relacionamento:**
     - `@OneToMany(mappedBy = "portal", cascade = CascadeType.ALL)`: Relacionamento um-para-muitos com a entidade `TagPortal`, permitindo cascata de operações como inserção e exclusão.

#### Relações

- **TagPortal**
  - Relacionamento um-para-muitos com a entidade `TagPortal`, que permite associar tags que categorizam o portal.


Essa estrutura fornece uma base sólida para configurar os portais de onde o crawler extrai notícias, incluindo estado ativo/inativo, categorização, e frequência de coleta.

### Entidade: Regionalismo

A entidade `Regionalismo` representa palavras ou expressões que possuem múltiplos significados, sendo categorizadas por meio de tags.

#### Campos

1. **regId** (`reg_id`)
   - **Tipo:** `Long`
   - **Descrição:** Identificador único do regionalismo. Gerado automaticamente.
   - **Anotações:**
     - `@Id`: Define o campo como chave primária.

2. **tagId** (`tag_id`)
   - **Tipo:** `Integer`
   - **Descrição:** Identificador da tag associada ao regionalismo. Este campo é uma chave estrangeira que se relaciona à entidade `Tag`.
   - **Anotações:**
     - `@Column(name = "tag_id")`: Representa a chave estrangeira para a tabela de tags.
     - Não é diretamente inserível ou atualizável por estar relacionado à entidade `Tag`.

3. **nome** (`nome`)
   - **Tipo:** `String`
   - **Descrição:** Nome do regionalismo (palavra ou expressão).
   - **Restrição:** 
     - Tamanho máximo: 100 caracteres.
   - **Exemplo:** "abacaxi" (pode significar tanto a fruta quanto um problema difícil de resolver).

4. **tag** (`Tag`)
   - **Tipo:** `Tag`
   - **Descrição:** Relacionamento com a entidade `Tag`, que categoriza o regionalismo.
   - **Relacionamento:**
     - `@ManyToOne`: Define um relacionamento muitos-para-um.
     - `@JoinColumn(name = "tag_id", referencedColumnName = "tag_id", insertable = false, updatable = false)`: Configura a coluna de junção.
     - `@JsonBackReference`: Evita loops de serialização JSON ao serializar a entidade.

#### Relações

- **Tag**
  - Relacionamento muitos-para-um com a entidade `Tag`, que categoriza regionalismos.

---

### Entidade: Reporter

A entidade `Reporter` representa jornalistas ou autores responsáveis pela criação de conteúdos em portais de notícias.

#### Campos

1. **repId** (`rep_id`)
   - **Tipo:** `Long`
   - **Descrição:** Identificador único do repórter. Gerado automaticamente.
   - **Anotações:**
     - `@Id`: Define o campo como chave primária.

2. **nome** (`rep_nome`)
   - **Tipo:** `String`
   - **Descrição:** Nome do repórter.
   - **Restrições:**
     - Não pode ser nulo.
   - **Exemplo:** "Maria Silva".

3. **portal** (`por_id`)
   - **Tipo:** `Portal`
   - **Descrição:** Relacionamento com a entidade `Portal`, indicando a qual portal o repórter está associado.
   - **Relacionamento:**
     - `@ManyToOne`: Define um relacionamento muitos-para-um.
     - `@JoinColumn(name = "por_id", referencedColumnName = "por_id")`: Configura a coluna de junção entre `Reporter` e `Portal`.

#### Relações

- **Portal**
  - Relacionamento muitos-para-um com a entidade `Portal`, indicando que um repórter pode estar vinculado a apenas um portal de notícias.

---

### Entidade: ResultApi

A entidade `ResultApi` armazena os resultados brutos (raw) retornados por APIs públicas, incluindo a data da resposta e o payload completo no formato JSON.

#### Campos

1. **resId** (`res_id`)
   - **Tipo:** `Long`
   - **Descrição:** Identificador único do resultado. Gerado automaticamente.
   - **Anotações:**
     - `@Id`: Define o campo como chave primária.

2. **resData** (`res_data`)
   - **Tipo:** `LocalDate`
   - **Descrição:** Data em que o resultado foi obtido da API.
   - **Exemplo:** `2024-11-30`.

3. **resPayload** (`res_payload`)
   - **Tipo:** `String` (armazenado como JSONB no banco de dados)
   - **Descrição:** Dados brutos retornados pela API no formato JSON.
   - **Restrições:**
     - Armazenado como JSONB no banco de dados para consultas e indexação otimizadas.
   - **Anotações:**
     - `@Type(JsonBinaryType.class)`: Configura o tipo de dado como `jsonb` para bancos de dados PostgreSQL.

4. **apiPublica** (`api_id`)
   - **Tipo:** `ApiPublica`
   - **Descrição:** Relacionamento com a entidade `ApiPublica`, indicando a API que forneceu o resultado.
   - **Relacionamento:**
     - `@ManyToOne`: Define um relacionamento muitos-para-um.
     - `@JoinColumn(name = "api_id")`: Configura a coluna de junção entre `ResultApi` e `ApiPublica`.

#### Relações

- **ApiPublica**
  - Relacionamento muitos-para-um com a entidade `ApiPublica`, associando cada resultado a uma API específica.

---

### Entidade: Tag

A entidade `Tag` representa metadados associados a elementos que o crawler utiliza para identificar e copiar notícias para o banco de dados.

#### Campos

1. **tagId** (`tag_id`)
   - **Tipo:** `Long`
   - **Descrição:** Identificador único da tag. Gerado automaticamente.
   - **Anotações:**
     - `@Id`: Define o campo como chave primária.

2. **tagNome** (`tag_nome`)
   - **Tipo:** `String`
   - **Descrição:** Nome da tag utilizada para categorizar ou identificar notícias.
   - **Restrições:**
     - Não pode ser nulo.
     - Comprimento máximo de 30 caracteres.
   - **Exemplo:** `"economia"`.

3. **tagDescricao** (`tag_descricao`)
   - **Tipo:** `String`
   - **Descrição:** Descrição detalhada ou explicação da tag.
   - **Restrições:**
     - Comprimento máximo de 250 caracteres.
   - **Exemplo:** `"Tag usada para identificar notícias relacionadas a economia."`.

4. **tagData** (`tag_data`)
   - **Tipo:** `LocalDate`
   - **Descrição:** Data de criação ou associação da tag.
   - **Exemplo:** `2024-11-30`.

5. **tagActive** (`tag_active`)
   - **Tipo:** `boolean`
   - **Descrição:** Indica se a tag está ativa para uso.
   - **Valores:**
     - `true`: Tag ativa.
     - `false`: Tag inativa.

6. **regionalismos** 
   - **Tipo:** `List<Regionalismo>`
   - **Descrição:** Lista de regionalismos associados à tag, representando palavras com múltiplos significados que o crawler pode usar para identificação contextual.
   - **Relacionamento:**
     - `@OneToMany(mappedBy = "tag")`: Define um relacionamento um-para-muitos com a entidade `Regionalismo`.
     - `@JsonManagedReference`: Gerencia referências cíclicas para evitar problemas de serialização/deserialização.

#### Relações

- **Regionalismos**
  - Relacionamento um-para-muitos com a entidade `Regionalismo`, indicando os regionalismos associados à tag.

---

### Entidade: TagNoticia

A entidade `TagNoticia` modela o relacionamento entre notícias e tags, permitindo que uma notícia esteja associada a várias tags e uma tag seja vinculada a várias notícias.

#### Campos

1. **notiId** (`noti_id`)
   - **Tipo:** `Noticia`
   - **Descrição:** Representa a notícia associada à tag.
   - **Relacionamento:**
     - `@ManyToOne`: Relacionamento muitos-para-um com a entidade `Noticia`.
     - `@JoinColumn(name = "noti_id")`: Define a chave estrangeira para a tabela `noticia`.
     - `@Id`: Parte da chave composta da relação.

2. **tagId** (`tag_id`)
   - **Tipo:** `Tag`
   - **Descrição:** Representa a tag associada à notícia.
   - **Relacionamento:**
     - `@ManyToOne`: Relacionamento muitos-para-um com a entidade `Tag`.
     - `@JoinColumn(name = "tag_id")`: Define a chave estrangeira para a tabela `tag`.
     - `@Id`: Parte da chave composta da relação.

#### Relações

- **Notícia**
  - Uma notícia pode estar associada a várias tags através da entidade `TagNoticia`.
- **Tag**
  - Uma tag pode estar vinculada a várias notícias através da entidade `TagNoticia`.

---

### Classe: TagPortal

A entidade `TagPortal` é utilizada para representar a relação entre as tabelas `Tag` e `Portal`. Essa relação permite que múltiplas tags sejam associadas a um portal e vice-versa.

#### Campos

1. **tag**
   - **Tipo:** `Tag`
   - **Relacionamento:** 
     - Muitos-para-um com a entidade `Tag`.
   - **Anotação:** `@ManyToOne` e `@JoinColumn(name = "tag_id")`
   - **Descrição:** Representa a tag associada ao portal.

2. **portal**
   - **Tipo:** `Portal`
   - **Relacionamento:** 
     - Muitos-para-um com a entidade `Portal`.
   - **Anotações:** 
     - `@ManyToOne`
     - `@JoinColumn(name = "por_id")`
     - `@JsonBackReference`: Evita recursão infinita na serialização JSON.
   - **Descrição:** Representa o portal associado à tag.

---

# Classe: TagResult

A entidade `TagResult` representa a relação entre os resultados da API pública (`ResultApi`) e as tags associadas, permitindo filtrar os resultados com base nas tags relevantes.

## Anotações e Características

- **@Entity**: Define a classe como uma entidade JPA.
- **@Table(name = "tag_result")**: Especifica o nome da tabela associada.
- **@IdClass(TagResultId.class)**: Define a chave composta usando a classe `TagResultId`.
- **@ManyToOne**: Relacionamento muitos-para-um com `ResultApi` e `Tag`.
- **@JoinColumn**: Especifica as colunas de junção para os relacionamentos.

## Campos

- **resultApi**: Relacionamento muitos-para-um com `ResultApi`, representando o resultado da API associada.
- **tag**: Relacionamento muitos-para-um com `Tag`, representando a tag associada ao resultado da API.
  
## Chave Composta

A chave composta é formada pelos campos `res_id` (referente ao `ResultApi`) e `tag_id` (referente à `Tag`), mapeados na classe `TagResultId`.
