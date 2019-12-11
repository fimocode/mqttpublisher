# mqttpublisher
Read a csv file to publish to MQTT broker

## Require
* mosquitto: https://mosquitto.org/
* Java JDK/JRE (8th or lastest)
```bash
sudo apt-get install openjdk-8-jdk
sudo apt-get install openjdk-8-jre
```
* Maven
```bash
sudo apt install maven
```
 or
```bash
sudo apt-get install maven
```
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
mosquitto_sub -t "topic_name" -u mosquitto_username -P mosquitto_passwd -v
```
