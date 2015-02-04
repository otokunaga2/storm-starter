package storm.memoryaids.spout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WordSpout implements IRichSpout{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private SpoutOutputCollector collector;
	private FileReader fileReader;
	private boolean completed = false;
	private TopologyContext context;
	public boolean isDistributed(){return false;};
	

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.context = context;
		try {
			System.out.println("---------debug--------------");
			System.out.println(conf.get("wordsFile").toString());
			this.fileReader = new FileReader(conf.get("wordsFile").toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error reading file ["+conf.get("wordsFile")+"]");
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		if(completed){
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				//Do nothing
			}
			return;
		}
		String str;
		BufferedReader reader = new BufferedReader(fileReader);
		try {
			while((str = reader.readLine()) != null){
				this.collector.emit(new Values(str));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			completed = true;
		}
	}

	@Override
	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		System.out.println("OK:"+msgId);
	}

	@Override
	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		System.out.println("FAIL:"+msgId);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("line"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
