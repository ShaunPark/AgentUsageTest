import mysql.connector
import random

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
    url = ''
    param = ''
        
    def execute(self):
        pool = HTTPConnectionPool(self.targetHost, maxsize=100)
        urlStr = url + '_' + str(index % 5)
        if param != '':
            urlStr = urlStr + '?' + param
        pool.request('GET', urlStr)
                
@app.route('/request', methods=['POST'])
def request():
    job = []
    data = request.get_json(force=True)
    subcall = data['subcall']
    for i in range(subcall):
        http = Http()
        http.url = "http://" + data['targetHost']
        http.index = i
        http.param = data['urlParam']
        job.append(http)
    
    random.shuffle(job)
    
    for j in job:
        j.execute()        
    
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