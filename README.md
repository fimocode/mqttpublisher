# mqttpublisher
Read a csv file to publish to MQTT broker

## Require
* mosquitto: https://mosquitto.org/
## Run project
* Using Eclipse
* Command Linux:
```bash
rm -rf target
mvn install package
java -jar target/mqttpublisher-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
## Open mosquitto and run command:
```bash
mosquitto_sub -t "topic_name" -v
```
