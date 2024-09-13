const express = require('express');
const app = express();
const bodyParser = require('body-parser');

// Middleware para parsing de JSON
app.use(bodyParser.json());

const apis = [];  // Array para armazenar as APIs

// Endpoint para salvar API
app.post('/api/salvar', (req, res) => {
    const { nomeApi, urlApi, periodoCaptura, dataFuturaCaptura } = req.body;

    // Validações básicas
    if (!nomeApi || !urlApi || !periodoCaptura || !dataFuturaCaptura) {
        return res.status(400).json({ message: 'Todos os campos são obrigatórios.' });
    }

    // Salvando na "tabela" de APIs (aqui usamos um array)
    apis.push({
        nomeApi,
        urlApi,
        periodoCaptura,
        dataFuturaCaptura,
    });

    res.status(201).json({ message: 'API salva com sucesso!', apis });
});

// Endpoint para listar as APIs
app.get('/api/listar', (req, res) => {
    res.status(200).json(apis);
});

// Endpoint para cancelar (descartar mudanças)
app.delete('/api/cancelar', (req, res) => {
    apis.length = 0;  // Limpa o array de APIs
    res.status(200).json({ message: 'Mudanças descartadas e lista de APIs resetada.' });
});

// Configuração do servidor
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Servidor rodando na porta ${PORT}`);
});
