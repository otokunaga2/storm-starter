package storm.kafka.starter;

import java.util.Properties;

import storm.kafka.trident.TridentKafkaState;
import storm.kafka.trident.TridentKafkaStateFactory;
import storm.kafka.trident.TridentKafkaUpdater;
import storm.kafka.trident.mapper.FieldNameBasedTupleToKafkaMapper;
import storm.kafka.trident.selector.DefaultTopicSelector;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;
import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class KaTridentTopology {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	     Fields fields = new Fields("word", "count");
	        FixedBatchSpout spout = new FixedBatchSpout(fields, 4,
	                new Values("storm", "1"),
	                new Values("trident", "1"),
	                new Values("needs", "1"),
	                new Values("javadoc", "1")
	        );
	        spout.setCycle(true);

	        TridentTopology topology = new TridentTopology();
	        Stream stream = topology.newStream("spout1", spout);

	        TridentKafkaStateFactory stateFactory = new TridentKafkaStateFactory()
	                .withKafkaTopicSelector(new DefaultTopicSelector("test"))
	                .withTridentTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper("word", "count"));
	        stream.partitionPersist(stateFactory, fields, new TridentKafkaUpdater(), new Fields());

	        Config conf = new Config();
	        //set producer properties.
	        Properties props = new Properties();
	        props.put("metadata.broker.list", "192.168.100.103:9094");
	        props.put("request.required.acks", "1");
	        props.put("serializer.class", "kafka.serializer.StringEncoder");
	        conf.put(TridentKafkaState.KAFKA_BROKER_PROPERTIES, props);
	        
				try {
					StormSubmitter.submitTopology("kafkaTridentTest", conf, topology.build());
				} catch (AlreadyAliveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidTopologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	}

}
