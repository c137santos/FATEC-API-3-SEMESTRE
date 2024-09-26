import express from 'express'
import fs from 'fs'

const app = express()

app.listen(3000, () => {
	console.log(`Mock API Initialized`)
})

app.get('/', async (_, res) => {
	const mockData = JSON.parse(fs.readFileSync('./mock/api/mock-data.json'))
	res.json(mockData)
})