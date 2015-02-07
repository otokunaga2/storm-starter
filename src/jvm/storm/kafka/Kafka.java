package storm.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.*;

import storm.kafka.SimplePartitioner;
public class Kafka {

	public static void main(String args[]){
		String test = "10";
		long events = Long.parseLong(test);
		Properties props = new Properties();
		Random rnd = new Random();
		props.put("zk.connect", "192.168.100.103:2181");
		props.put("metadata.broker.list", "192.168.100.103:9093,192.168.100.103:9094");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("partitioner.class", "storm.kafka.SimplePartitioner");
		props.put("request.required.acks", "1");
		
//		props.put("zk.connect", "192.168.100.103:2181");
//		props.put("metadata.broker.list", "broker1:9092,broker2:9092");
//		props.put("Serializer.class", "kafka.serializer.StringEncoder");
////		props.put("partitioner.class","example.producer.SimplePartitioner");
//		props.put("request.required.acks", "1");
		
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> product = new Producer<String, String>(config);
		
		for(long nEvents = 0; nEvents < events; nEvents++){
			long runtime = new Date().getTime();
			String ip = "192.168.2" + rnd.nextInt(255);
			String msg = runtime + ",www.example.com" + ip;
			KeyedMessage<String, String> data = new KeyedMessage<String,String>("page_visits",ip,msg);
			product.send(data);
			
		}
		
	}
	
	
//	props.put("zk.connect","192.168.100.103:2181");
	
}
