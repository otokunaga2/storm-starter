package storm.kafka;

import java.util.Properties;

import storm.kafka.bolt.selector.DefaultTopicSelector;
import storm.kafka.trident.GlobalPartitionInformation;
import storm.kafka.trident.OpaqueTridentKafkaSpout;
import storm.kafka.trident.TridentKafkaConfig;
import storm.kafka.trident.TridentKafkaState;
import storm.kafka.trident.TridentKafkaStateFactory;
import storm.kafka.trident.TridentKafkaUpdater;
import storm.kafka.trident.mapper.FieldNameBasedTupleToKafkaMapper;
import storm.kafka.trident.selector.KafkaTopicSelector;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;
import nl.minvenj.nfi.storm.kafka.KafkaSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class ExKTopology {
	public static void main(String[] args){
		 Broker brokerForPartition0 = new Broker("192.168.100.103");//localhost:9092
		    Broker brokerForPartition1 = new Broker("192.168.100.103", 9092);//localhost:9092 but we specified the port explicitly
		    Broker brokerForPartition2 = new Broker("192.168.100.104",9093);//localhost:9092 specified as one string.
		    GlobalPartitionInformation partitionInfo = new GlobalPartitionInformation();
		    partitionInfo.addPartition(0, brokerForPartition0);//mapping form partition 0 to brokerForPartition0
		    partitionInfo.addPartition(1, brokerForPartition1);//mapping form partition 1 to brokerForPartition1
		    partitionInfo.addPartition(2, brokerForPartition2);//mapping form partition 2 to brokerForPartition2
		    StaticHosts hosts = new StaticHosts(partitionInfo);
		    System.out.println(hosts);
		    KafkaConfig conf = new KafkaConfig(hosts, "test");
		 
	}

}
