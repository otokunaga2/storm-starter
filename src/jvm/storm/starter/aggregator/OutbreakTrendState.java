package storm.starter.aggregator;

import storm.trident.state.State;
import storm.trident.state.map.IBackingMap;
import storm.trident.state.map.NonTransactionalMap;

public class OutbreakTrendState extends NonTransactionalMap<Long>{

	protected OutbreakTrendState(OutbreakTrendBackingMap outbreakTrendBackingMap) {
		super(outbreakTrendBackingMap);
		// TODO Auto-generated constructor stub
	}
//implements State {

//	public OutbreakTrendState(OutbreakTrendBackingMap outbreakTrendBackingMap) {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public void beginCommit(Long txid) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void commit(Long txid) {
//		// TODO Auto-generated method stub
//
//	}

}
