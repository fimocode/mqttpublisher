package application.Mqtt_Application;

import java.util.concurrent.CountDownLatch;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class App
{
	static Multithreading[] Multithreading=null;

    public static void main(String[] args) throws MqttPersistenceException, MqttException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(50);
        Multithreading = new Multithreading[500];
        for(int i=0;i<40;i++)
        {
        	Multithreading[i] = new Multithreading(latch);
        	Multithreading[i].setName(String.valueOf(i));
        	Multithreading[i].start();
        }
        latch.await();
        System.out.println("****************************************************************************************");

        for(int i=0;i<40;i++)
        {
        	Multithreading[i].getClient().disconnect();
        	Multithreading[i].getClient().close();
        }
        System.out.println("*****************completed*******************");
    }
}
