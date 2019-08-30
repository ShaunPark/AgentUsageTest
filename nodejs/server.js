require('@instana/collector')({tracing: {
        enabled: true
    }});
// init tracing
// MUST be done before loading anything else!
//instana({
//    tracing: {
//        enabled: true
//    }
//});
//
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

function getPromise(options) {
	return new Promise((resolve, reject) => {
		http.get(options, (response) => {
			let chunks_of_data = [];

//			response.on('data', (fragments) => {
//				chunks_of_data.push(fragments);
//			});

			response.on('end', () => {
				let response_body = Buffer.concat(chunks_of_data);
				resolve(response_body.toString());
			});

			response.on('error', (error) => {
				reject(error);
			});
		});
	});
}

function logJob(message) {
    console.log(message);
}

// async function which will wait for the HTTP request to be finished
async function httpJob(options) {
	try {
		let http_promise = getPromise(options);
		let response_body = await http_promise;

		// holds response from server that is passed when Promise is resolved
		console.log(response_body);
	}
	catch(error) {
		// Promise rejected
		console.log(error);
	}
}

app.post('/request', (req, res) => {
    info = req.body;
//    console.log(info);
//    console.log(info.subcall);
    var jobs = [];
    for( var i = 0 ; i < info.subcall ; i++ ) {
        var url = info.targetUrl;
        jobs.push({'type':'http', 
                   'options':{hostname: info.targetHost,
                              port: info.targetPort,
                              path: (url.startsWith('/')?'':'/') + url + '_' +(i%5),
                              headers: {'Host':'localhost'}
                }});
        //'http://'+info.targetHost + ':'+ info.targetPort + (url.startsWith('/')?'':'/') + url + '_' +(i%5)});
    }
    
    for( var i = 0 ; i < info.logMessages; i++ ) {
        jobs.push({'type':'log', 'message':'logMessage test'});
    }
    
    jobs = shuffle(jobs);
    
    jobs.forEach((job) =>{
        switch(job.type) {
            case 'http':httpJob(job.options); break;
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