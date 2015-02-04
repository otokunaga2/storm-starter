package storm.starter.aggregator;

import storm.trident.operation.CombinerAggregator;
import storm.trident.tuple.TridentTuple;

public class Count implements CombinerAggregator<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Long init(TridentTuple tuple) {
		// TODO Auto-generated method stub
		return 1L;
	}

	@Override
	public Long combine(Long val1, Long val2) {
		// TODO Auto-generated method stub
		return val1+val2;
	}

	@Override
	public Long zero() {
		// TODO Auto-generated method stub
		return 0L;
	}

}
