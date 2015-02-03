package storm.memoryaids.spout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

public class WordCountTopology {
	private static final String SENTENCE_SPOUT_ID = "sentence-spout";
	private static final String SPLIT_BOLT_ID = "split-bolt";
	private static final String COUNT_BOLT_ID = "count-bolt";
	private static final String REPORT_BOLT_ID = "report-bolt";
	private static final String TOPOLOGY_NAME = "word-count-topology";
	
	
	public static void main(String[] args) throws InterruptedException{
		SentenceSpout spout = new SentenceSpout();
		SplitSentenceBolt splitBolt = new SplitSentenceBolt();
		WordCountBolt countBolt = new WordCountBolt();
		ReportBolt reportBolt = new ReportBolt();
		
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout(SENTENCE_SPOUT_ID, spout, 2);
		//SentenceSpout --> SplitSentenceBolt
		
		builder.setBolt(SPLIT_BOLT_ID, splitBolt, 2).setNumTasks(4).shuffleGrouping(SENTENCE_SPOUT_ID);
		//SpliteSentenceBolt --> WordCountBolt
		
//		builder.setBolt(COUNT_BOLT_ID,countBolt, 4).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));
		builder.setBolt(COUNT_BOLT_ID,countBolt, 4).shuffleGrouping(SPLIT_BOLT_ID);
		
		//WorrdCOuntBolt --> REportBolt
		
		
		builder.setBolt(REPORT_BOLT_ID,reportBolt).globalGrouping(COUNT_BOLT_ID);
		
		Config config = new Config();
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
		
		
		Utils.sleep(5000);
		
		cluster.killTopology(TOPOLOGY_NAME);
		cluster.shutdown();
//		WordCountB
	}


//	private static void waitForSeconds(int i) {
//		try {
//			Thread.sleep(i*1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// TODO Auto-generated method stub
//		
	
}
