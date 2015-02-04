package storm.starter.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

import javax.swing.JEditorPane;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import storm.trident.spout.ITridentSpout;

public class DiagnosisEventSpout implements ITridentSpout<Long> {
	private static final long serialVersionUID = 1L;
	SpoutOuputCollector collector;
	BatchCoordinator<Long> coordinator = new DefaultCoordinator();
	Emitter<Long> emitter = new DiagnosisEventEmitter();
	
	@Override
	public storm.trident.spout.ITridentSpout.BatchCoordinator<Long> getCoordinator(
			String txStateId, Map conf, TopologyContext context) {
		// TODO Auto-generated method stub
		return coordinator;
	}

	@Override
	public storm.trident.spout.ITridentSpout.Emitter<Long> getEmitter(
			String txStateId, Map conf, TopologyContext context) {
		// TODO Auto-generated method stub
		return emitter;
	}

	@Override
	public Map getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fields getOutputFields() {
		// TODO Auto-generated method stub
		return new Fields("event");
	}
 

}