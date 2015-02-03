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

public class RedisSpout extends BaseRichSpout {
  SpoutOutputCollector _collector;
  Random _rand;


  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    _collector = collector;
    _rand = new Random();
  }

  @Override
  public void nextTuple() {
	String poobar; 
	JedisPool pool = null;
	Jedis jedis = pool.getResource();
	try{
		poobar = jedis.get("tokunaga");
	}finally{
		pool.returnResource(jedis);
	}
	pool.destroy();
	Utils.sleep(100);
	_collector.emit(new Values(poobar));
	    
  }

  @Override
  public void ack(Object id) {
  }

  @Override
  public void fail(Object id) {
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("word"));
  }

}