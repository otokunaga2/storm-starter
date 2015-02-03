package storm.memoryaids.spout;

import java.util.Map;


//import org.apache.jute.Utils;
import org.slf4j.helpers.Util;
import org.testng.internal.Utils;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
//import backtype.storm.utils.Utils;

public class SentenceSpout extends BaseRichSpout {
	private SpoutOutputCollector collector;
	
	private String[] sentences = {
		"my dog has fleas",
		"i like cold beverages",
		"the dog ate my homework",
		"don't have a cow man",
		"i don't think i like fleas"
	};
	private int index = 0;
	
	
	
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		this.collector.emit(new Values(sentences[index]));
		index++;
		if (index >= sentences.length){
			index = 0;
		}	
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("sentence"));
	}

}
