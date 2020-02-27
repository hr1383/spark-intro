
## Runtime Requirments
1. Docker (at least 2 CPU cores and 8gb RAM)
2. System Terminal (iTerm, Terminal, etc)
3. Working Web Browser (Chrome or Firefox)

### Technologies Used
1. [Apache Zeppelin](https://zeppelin.apache.org/docs/latest/interpreter/spark.html)
3. [Mysql]

#### Spark 2.4.5
- http://mirror.metrocast.net/apache/spark/spark-2.4.5/spark-2.4.5-bin-hadoop2.7.tgz


### Docker
Install Docker Desktop (https://www.docker.com/products/docker-desktop)

Additional Docker Resources:
* https://docs.docker.com/get-started/
* https://hub.docker.com/

#### Docker Runtime Recommendations
1. 2 or more cpu cores.
2. 8gb/ram or higher.

## Installation
1. Install Docker (See Docker above)
2. Once Docker is installed. Open up your terminal application and `cd /spark-intro/docker`
3. `./run.sh install`
4. `./run.sh start`

## Checking Zeppelin and Updating Zeppelin
1. The **Main Application** should now be running at http://localhost:8080/


### Update the Zeppelin Spark Interpreter Runtime
1. Go to http://localhost:8080/#/interpreter on your Web Browser
2. Search for `spark` in the `Search Interpreters` input field.
3. Click the `edit` button to initiate editing mode.
