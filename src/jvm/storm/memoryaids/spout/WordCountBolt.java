package storm.memoryaids.spout;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCountBolt extends BaseRichBolt {
	private OutputCollector collector;
	private HashMap<String,Long> counts = null;
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		this.counts = new HashMap<String, Long>();

	}

	@Override
	public void execute(Tuple tuple) {
		// TODO Auto-generated method stub
		String word = tuple.getStringByField("word");
		Long count = this.counts.get(word);
		if(count == null){
			count = 0L;
		}
		count++;
		this.counts.put(word,count);
		this.collector.emit(new Values(word,count));

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("word","count"));
	}

}
