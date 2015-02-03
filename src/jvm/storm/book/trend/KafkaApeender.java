package storm.book.trend;

import java.util.Formatter;
import java.util.Properties;

import org.slf4j.helpers.MessageFormatter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class KafkaApeender extends AppenderBase<ILoggingEvent> {

	private String topic;
	private String zookeeperHost;
	private Producer<String,String> producer;
	private MessageFormatter formatter;
	
	public String getTopic(){
		return topic;
	}
	
	@Override
	public void start(){
		if(this.formatter == null){
			this.formatter = new MessageFormatter();
		}
		super.start();
		Properties props = new Properties();
		props.put("zk.connect", this.zookeeperHost);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		ProducerConfig config = new ProducerConfig(props);
		this.producer = new Producer<String, String>(config);
		
	}
	
	@Override
	protected void append(ILoggingEvent arg0) {
		// TODO Auto-generated method stub
		
	}




	public String getZookeeperHost() {
		return zookeeperHost;
	}




	public void setZookeeperHost(String zookeeperHost) {
		this.zookeeperHost = zookeeperHost;
	}




	public Producer<String,String> getProducer() {
		return producer;
	}




	public void setProducer(Producer<String,String> producer) {
		this.producer = producer;
	}






}
