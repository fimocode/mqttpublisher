package application.Mqtt_Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class App 
{
	static MqttClient client;
	static int qos=0;
	public static void main( String[] args ) throws MqttException, IOException
    {
//    	int n = 4; // Number of threads 
//        for (int i=0; i<n; i++) 
//        {
//            Thread object = new Thread(new Multithreading(i)); 
//            object.start(); 
//        }
        
        client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
    	client.setCallback( new MyCallback() );
    	client.connect();
		BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\Tonya\\Downloads\\sorted100M.csv"));
//		BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\Tonya\\Downloads\\debs40houses16h\\house-0.csv"));
		String row;
		long start = System.currentTimeMillis();    
		while ((row = csvReader.readLine()) != null) {
//		    String[] data = row.split(",");
		    MqttMessage message = new MqttMessage();
	    	message.setPayload(row.getBytes());
	    	message.setQos(qos);
	    	client.publish("iot_data7", message);
	    	if ((System.currentTimeMillis() - start) >= 1000) {
	    		break;
	    	}
		}
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.print("Total time: " + elapsedTime);
		csvReader.close();

    	client.disconnect();
    }
}
