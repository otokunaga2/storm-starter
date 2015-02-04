package storm.book.trend;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

public class BooleanFilter extends BaseFilter {

	
	
	@Override
	public boolean isKeep(TridentTuple tuple) {
		// TODO Auto-generated method stub
		return tuple.getBoolean(0);
	}

}
