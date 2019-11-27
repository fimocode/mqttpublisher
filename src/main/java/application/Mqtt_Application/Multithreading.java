package application.Mqtt_Application;

import java.io.BufferedReader;
import java.io.FileReader;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class Multithreading implements Runnable
{ 
	int id;
	long houseId1, houseId2;
	
    public Multithreading(int i) {
		this.id = i;
	}
    
	public void run() 
    {
    	MqttClient client;
        try
        {
            System.out.println ("Thread " + id + " is running");
            
            client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
        	client.setCallback( new MyCallback() );
        	client.connect();
    		BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\Tonya\\Downloads\\sorted100M.csv"));
//    		BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\Tonya\\Downloads\\debs40houses16h\\house-0.csv"));
    		String row;
    		long start = System.currentTimeMillis();    
    		while ((row = csvReader.readLine()) != null) {
//    		    String[] data = row.split(",");
//    		    houseId1 = Long.parseLong(data[0]);
    		    MqttMessage message = new MqttMessage();
    	    	message.setPayload(row.getBytes());
    	    	client.publish("iot_data", message);
    		}
    		long elapsedTime = System.currentTimeMillis() - start;
    		System.out.print("Total time: " + elapsedTime);
    		csvReader.close();

        	client.disconnect();
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    }
} 
