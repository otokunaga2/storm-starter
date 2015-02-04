package storm.book.trend;


import org.apache.zookeeper.server.quorum.QuorumCnxManager.Message;
import org.mockito.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;


public class XMPPFunction extends BaseFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(XMPPFunction.class);
	
	public static final String XMPP_TO = "storm.xmpp.to";
	public static final String XMPP_USER = "storm.xmpp.user";
	public static final String XMPP_PASSWORD = "storm.xmpp.password";
	public static final String XMPP_SERVER = "storm.xmpp.server";
	
	private XMPPConnection xmppConnection;
	private String to;
	private MessageMapper mapper;
	
	public XMPPFunction(MessageMapper mapper){
		this.mapper = mapper;
	}
	
	public void prepare(java.util.Map conf, storm.trident.operation.TridentOperationContext context) {
		
		
		
	};
	
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		// TODO Auto-generated method stub
//		Message msg = new Message(null, 0);
//		Message msg = new Message(this.to,Type.normal);
		
//		msg.setBody(this.mapper.toMessageBody(tuple));
//		this.xmppConnection
		
	}

}
