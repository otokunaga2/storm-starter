package storm.book.trend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.tuple.Values;
import bsh.This;
import storm.book.trend.EWMA.Time;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class MovingAverageFunction extends BaseFunction {
	private static final Logger LOG = LoggerFactory.getLogger(BaseFunction.class);
	
	
	private EWMA ewma;
	private Time emitRatePer;
	
	
	public MovingAverageFunction(EWMA ewma, Time emitRatePer){
		this.ewma = ewma;
		this.emitRatePer = emitRatePer;
	}
	
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		// TODO Auto-generated method stub
		this.ewma.mark(tuple.getLong(0));
		LOG.debug("Rate: {}", this.ewma.getAverageRatePer(this.emitRatePer));
		collector.emit(new Values(this.ewma.getAverageRatePer(this.emitRatePer)));
	}

}
