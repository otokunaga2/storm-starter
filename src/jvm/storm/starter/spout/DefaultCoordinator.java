package storm.starter.spout;




import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import storm.trident.spout.ITridentSpout.BatchCoordinator;

public class DefaultCoordinator implements BatchCoordinator<Long>,Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(DefaultCoordinator.class);
	
	
	@Override
	public Long initializeTransaction(long txid, Long prevMetadata,
			Long currMetadata) {
		// TODO Auto-generated method stub
		log.info("Initializing transaction [" + txid + "]");
		return null;
	}

	@Override
	public void success(long txid) {
		// TODO Auto-generated method stub
		log.info("successful transaction [" + txid + "]");
	}

	@Override
	public boolean isReady(long txid) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
