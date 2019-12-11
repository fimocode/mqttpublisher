package application.Mqtt_Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class Multithreading extends Thread {

    public static final String set_topic = "house-";
    private MqttClient client;
    public static final String BROKER_URL = "tcp://localhost:1883";
    CountDownLatch latch;
    static int qos = 1;
    static String path = "/home/tony/Downloads/debs40houses16h";

    public Multithreading (CountDownLatch latch) {
        super();
        this.latch = latch;
    }
    
    // public Multithreading (String s) {
    //     Multithreading.path = s;
    // }

    public void multithreading() {
        String clientid = Thread.currentThread().getName();
        //We have to generate a unique Client id.

        System.out.println("Thread "+clientid);
        MqttConnectOptions options = null;
        try {
            client = new MqttClient(BROKER_URL, clientid);
            options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setMaxInflight(50);
            client.connect(options);
        } catch (MqttException e) {
            try {
                client.connect(options);
            } catch (MqttSecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (MqttException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        multithreading();
        try {
            sendMessage(Thread.currentThread().getName());
            latch.countDown();
        } catch (MqttPersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void sendMessage(String s) throws MqttPersistenceException, MqttException, IOException {
        final MqttTopic test = client.getTopic(set_topic+s);
        BufferedReader csvReader = new BufferedReader(new FileReader(path + "/" + test +".csv"));
        String row;
        long start = System.currentTimeMillis();
        while ((row = csvReader.readLine()) != null) {
            // String[] data = row.split(",");
            MqttMessage message = new MqttMessage();
            message.setPayload(row.getBytes());
            message.setQos(qos);
            test.publish(message);
            // if ((System.currentTimeMillis() - start) > 1000) {
            //     break;
            // }
        }
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.print("Total time:" + "\t" + elapsedTime + "\t" + test + "\n");
        csvReader.close();
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }
}
