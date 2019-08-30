require('@instana/collector')();
// init tracing
// MUST be done before loading anything else!
//instana({
//    tracing: {
//        enabled: true
//    }
//});

const http = require('http')
const express = require('express');
const app = express();

//app.use((req, res, next) => {
//    res.set('Timing-Allow-Origin', '*');
//    res.set('Access-Control-Allow-Origin', '*');
//    next();
//});

//app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.json());


function shuffle(array) {
  var currentIndex = array.length, temporaryValue, randomIndex;

  // While there remain elements to shuffle...
  while (0 !== currentIndex) {

    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;

    // And swap it with the current element.
    temporaryValue = array[currentIndex];
    array[currentIndex] = array[randomIndex];
    array[randomIndex] = temporaryValue;
  }

  return array;
}

function httpJob(url) {
    console.log('httpJob - ' + url);
    http.get(url, (resp) => {
    })
    .on("error", (err) => {
        console.log("Error: " + err.message);
    });
}

function logJob(message) {
    console.log(message);
}

app.post('/request', (req, res) => {
    info = req.body;
    
    var jobs = [];
    for( var i = 0 ; i < info.subcall ; i++ ) {
        var url = info.targetUrl;
        jobs.push({'type':'http', 'url': 'http://'+info.targetHost + ':'+ info.targetPort + (url.startsWith('/')?'':'/') + url + '_' +(i%5)});
    }
    
    for( var i = 0 ; i < info.logMessages; i++ ) {
        jobs.push({'type':'log', 'message':'logMessage test'});
    }
    
    jobs = shuffle(jobs);
    
    jobs.forEach((job) =>{
        switch(job.type) {
            case 'http':httpJob(job.url); break;
            case 'log':logJob(job.message); break;
        }
    });
    
    console.log('request received')
    res.send('Call Success');
});


const port = '8080';
app.listen(port, () => {
    console.log(`Example app listening on port ${port}!`);
});