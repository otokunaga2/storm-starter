package storm.memoryaids.spout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class ReportBolt extends BaseRichBolt {

	private HashMap<String, Long> counts = null;
	
	
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.counts = new HashMap<String,Long>();
	}

	@Override
	public void execute(Tuple tuple) {
		// TODO Auto-generated method stub
		String word = tuple.getStringByField("word");
		Long count = tuple.getLongByField("count");
		this.counts.put(word, count);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}
	public void cleanup(){
		System.out.println("-----------------FINAL COUNTS-----------------");
		List<String> keys = new ArrayList<String>();
		keys.addAll(this.counts.keySet());
		for(String key: keys){
			System.out.println(key + ":" + this.counts.get(key));
		}
		System.out.println("-------");
	}

}
