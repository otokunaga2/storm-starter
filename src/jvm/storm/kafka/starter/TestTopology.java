package storm.kafka.starter;

import java.util.Properties;

import storm.kafka.bolt.KafkaBolt;
import storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import storm.kafka.bolt.selector.DefaultTopicSelector;
import storm.kafka.trident.TridentKafkaState;
import storm.trident.testing.FixedBatchSpout;
import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class TestTopology {
	public static void main(String args[])
	{
		TopologyBuilder builder = new TopologyBuilder();
		Fields fields = new Fields("key","message");
		IRichSpout spout = (IRichSpout) new FixedBatchSpout(fields, 4,
				new Values("storm",1),
				new Values("trident",1),
				new Values("needs",1),
				new Values("javadoc",1)
		);
		((FixedBatchSpout) spout).setCycle(true);
		builder.setSpout("spout", spout, 5);
		KafkaBolt bolt = new KafkaBolt()
						.withTopicSelector(new DefaultTopicSelector("test"))
						.withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper());
		
		builder.setBolt("forwardToKafka",bolt, 8).shuffleGrouping("spout");
//		Config conf = new Config();
		
		
		 Config conf = new Config();
	        //set producer properties.
	        Properties props = new Properties();
	        props.put("metadata.broker.list", "localhost:9092");
	        props.put("request.required.acks", "1");
	        props.put("serializer.class", "kafka.serializer.StringEncoder");
	        conf.put(TridentKafkaState.KAFKA_BROKER_PROPERTIES, props);

	        
				try {
					StormSubmitter.submitTopology("kafkaboltTest", conf, builder.createTopology());
				} catch (AlreadyAliveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidTopologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
		
	}
}
