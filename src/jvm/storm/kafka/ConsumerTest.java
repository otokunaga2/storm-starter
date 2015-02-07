package storm.kafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class ConsumerTest implements Runnable{

	private KafkaStream m_stream;
	private int m_threadNumber;
	
	
	public ConsumerTest(KafkaStream a_stream, int a_threadNumber){
		m_threadNumber = a_threadNumber;
		m_stream = a_stream;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		while(it.hasNext()){
			System.out.println("thread" + m_threadNumber +":" + new String(it.next().message()));
		}
		System.out.println("Shutting down Thread: "+ m_threadNumber);
	}
	

}
