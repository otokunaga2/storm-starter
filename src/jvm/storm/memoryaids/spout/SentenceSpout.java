package storm.memoryaids.spout;

import java.util.Map;


//import org.apache.jute.Utils;






import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.*;
//import backtype.storm.utils.Utils;

public class SentenceSpout extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		if (index >= sentences.length){
			this.collector.emit(new Values(sentences[index]));
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("sentence"));
	}

}
