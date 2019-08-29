import mysql.connector
import random
import json

from datetime import datetime
from flask import Flask, request
from flask_json import FlaskJSON, JsonError, json_response, as_json


from urllib3 import HTTPConnectionPool

app = Flask(__name__)
FlaskJSON(app)


class Job:
    index = 0
    def setIndex(self, index):
        self.index = index

class Http(Job):
    pool = ''
    url = ''
    param = ''
        
    def execute(self):
        urlStr = self.url + '_' + str(index % 5)
        if self.param != '':
            urlStr = urlStr + '?' + self.param
        self.pool.request('GET', urlStr, headers={'Host': 'localhost'})


@app.route('/request', methods=['POST'])
def procReq():
    #print(request.content_type)
    print('request from ' + request.remote_addr)
    job = []
    #data = json.loads(request.data, strict=False) 
    data = request.get_json(force=True)
    subcall = data['subcall']
    
    
    host = data.get('targetHost','')
    portStr = data.get('targetPort', '80')
    url = data.get('targetUrl', '/')
    param = data.get('urlParam', '')

    port = int(portStr)
        
    pool = HTTPConnectionPool(host=host, port=port, maxsize=100, headers={'Host':'localhost'})

    for i in range(int(subcall)):
        http = Http()
        http.url = url
        http.index = i
        http.pool = pool
        http.param = param
        job.append(http)
        
    random.shuffle(job)
    
    for j in job:
        j.execute()   
        
    return "Call success"

    
@app.route('/insert')
def insertData():
    data = request.args.get('data')
    print(data)
    
    mydb = mysql.connector.connect(
        host="172.31.25.154",
        user="shpark",
        passwd="shpark",
        database="sctest",
        use_pure=True
    )
    try:
        mycursor = mydb.cursor(prepared=True)

        sql = "insert into test (name) values (%s)"

        val = (data,)
        mycursor.execute(sql, val)

        mydb.commit()

        print(mycursor.rowcount, "record inserted.")
        return "message success"
    except mysql.connector.Error as error :
        print("Failed to update record to database: {}".format(error))
        mydb.rollback()
        return "message failed".format(error)

    finally:
        #closing database connection.
        if(mydb.is_connected()):
            mydb.close()
            print("MySQL connection is closed")

if __name__ == '__main__':
    app.run(host= '0.0.0.0', port=8080)