package storm.book.trend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class ThreasholdFilterFunction extends BaseFunction {
	private static  final Logger LOG = LoggerFactory.getLogger(ThreasholdFilterFunction.class);
	
	private static enum State{
		BELOW, ABOVE;
	}
	
	private State last = State.BELOW;
	private double threashold;
	
	
	public ThreasholdFilterFunction(double threashold){
		this.threashold = threashold;
	}
	
	
	
	
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		// TODO Auto-generated method stub
		double val = tuple.getDouble(0);
		State newState = val < this.threashold ? State.BELOW : State.ABOVE;
		boolean stateChange = this.last != newState;
		collector.emit(new Values(stateChange, threashold));
		this.last = newState;
		LOG.debug("State change? --> {}",stateChange);
	}

}
