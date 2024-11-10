import express from 'express'
import ejs from 'ejs'

const app = express()
app.set('views', import.meta.dirname + '/views');
app.engine('html', ejs.renderFile);

app.listen(3001, () => {
	console.log(`Mock News outlet Initialized`)
})

app.get('/', (_, res) => {
	res.render('home.html')
})
app.get('/news/foo', (_, res) => {
	res.render('foo.html')
})

app.get('/news/bar', (_, res) => {
	res.render('bar.html')
})