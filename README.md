# Log file reader service

This is a Java microservice built with Spring framework. It reads a log file located in a location and returns the last `numLines` matching lines that contain a specific `searchText`. The default location of the log files can be specified in Spring properties `log-location.default` in [application.properties](src/main/resources/application.properties).

## Sample log files
- A small sample HDFS log file (2k lines) is included for testing ([HDFS_2k.log](logfiles/HDFS_2k.log))
- Other larger sample HDFS log files in ([logfiles](logfiles)) are retrieved from this public repo: https://zenodo.org/record/8196385. Once downloaded and unzipped, place the files in that folder where the service will read from.

## Prerequisite
+ JDK 17: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
+ Maven 3.9.1: https://maven.apache.org/download.cgi

## Build & Run
The project is built using Maven : to build and start the service, in the project root folder, invoke:
```agsl
./mvnw spring-boot:run
```

## Usage
Invoke the service with the following `curl` command:
```agsl
curl --location 'http://localhost:8080/logLines?numLines=10&searchText=PacketResponder&fileName=HDFS_v1.log'
```

### Endpoint & Parameter
`GET` `/logLines`

 Parameter      | Description                                                                             |  Sample value  |  Nulable  |
| :---        |:----------------------------------------------------------------------------------------|:--------------:|:---------:|
| fileName      | The log file name to query.                                                             | `HDFS_v1.log`  |    No     |
| numLines   | Specifies how many matching lines to retrieve. Default `5`                              |      `10`      |    Yes    |
| searchText   | The text to search in a log line. If empty, the last `numLines` of log will be returned |    `PacketResponder`    |    Yes    |

### Sample response
Searching for the latest 10 log lines that has the string `PacketResponder`:
```agsl
{
    "asOf": "2023-08-20T19:00:46.307+00:00",
    "searchText": "PacketResponder",
    "logLines": [
        "081111 110423 27425 INFO dfs.DataNode$PacketResponder: Received block blk_-9128742458709757181 of size 49648902 from /10.250.14.224",
        "081111 110423 27425 INFO dfs.DataNode$PacketResponder: PacketResponder 0 for block blk_-9128742458709757181 terminating",
        "081111 110423 26950 INFO dfs.DataNode$PacketResponder: Received block blk_-9128742458709757181 of size 49648902 from /10.251.203.179",
        "081111 110423 26950 INFO dfs.DataNode$PacketResponder: PacketResponder 1 for block blk_-9128742458709757181 terminating",
        "081111 110423 26688 INFO dfs.DataNode$PacketResponder: Received block blk_-9128742458709757181 of size 49648902 from /10.251.203.179",
        "081111 110423 26688 INFO dfs.DataNode$PacketResponder: PacketResponder 2 for block blk_-9128742458709757181 terminating",
        "081111 110421 26445 INFO dfs.DataNode$PacketResponder: Received block blk_-6647188313098014330 of size 1888541 from /10.250.19.102",
        "081111 110421 26445 INFO dfs.DataNode$PacketResponder: PacketResponder 2 for block blk_-6647188313098014330 terminating",
        "081111 110421 26370 INFO dfs.DataNode$PacketResponder: Received block blk_-6647188313098014330 of size 1888541 from /10.251.110.160",
        "081111 110421 26370 INFO dfs.DataNode$PacketResponder: PacketResponder 1 for block blk_-6647188313098014330 terminating"
    ]
}
```
## Monitoring
When this service is deployed to Production, we'd want to monitor its health and other telemetry information. Here, I use [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/) to expose a few endpoints for the service:
+ http://localhost:8080/actuator/health
+ http://localhost:8080/actuator/env
+ http://localhost:8080/actuator/info
+ http://localhost:8080/actuator/beans


## Future development

If this service is to be developed further, I think we can implement the following:
+ Contanerize the service with `Docker` to package all requirements (like JDK, Maven, etc.)
+ Support matching multiple texts.
+ Support regex match.
+ Support time range query.
+ Add additional telemetry/monitoring to this service itself.