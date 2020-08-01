var express = require('express');
var cors = require('cors')
var bodyParser = require('body-parser');
var router = require('./routes');

var app = express();

app.use(cors())
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json()); 
app.use(router);


var port = process.env.PORT || 3300
app.listen(port, () => {console.log(`server running on port ${port}`)});
