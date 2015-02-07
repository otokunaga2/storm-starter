package storm.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerConnector;
import kafka.consumer.KafkaStream;

public class ConsumerGroupExample {
	//final いらない
	private kafka.javaapi.consumer.ConsumerConnector consumer;
	private String topic;
	private ExecutorService executor;
	public ConsumerGroupExample(String a_zookeeper, String a_groupId, String a_topic){
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig(a_zookeeper, a_groupId));
		this.topic = a_topic;
	}
	

	
	public void shutdown(){
		if (consumer != null) consumer.shutdown();
		if(executor != null)  executor.shutdown();
	}
	
	public void run(int a_numThrads){
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(a_numThrads));
		
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
		
		executor = Executors.newFixedThreadPool(a_numThrads);
		int threadNumber = 0;
		for(final KafkaStream stream: streams){
			executor.submit(new ConsumerTest(stream, threadNumber));
			threadNumber++;
		}
	}
	
	private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId){
		Properties props = new Properties();
		props.put("zookeeper.connect", a_zookeeper);
		props.put("group.id",a_groupId);
		props.put("zookeeper.session.timeout.ms","400");
		props.put("zookeeper.sync.time.ms","200");
		props.put("auto.commit.interval.ms","1000");
		return new ConsumerConfig(props);
	}
	
	public static void main(String[] args){
		String zookeeper = "192.168.100.103:2181";
		String groupId = "a";
		String topic = "page-visits";
		String threadNumer = "4";
		int threads = 50;
		ConsumerGroupExample example = new ConsumerGroupExample(zookeeper, groupId, topic);
		example.run(threads);
		try{
			Thread.sleep(10000);
		}catch (InterruptedException e){
			
		}
		example.shutdown();
	}
	
}
