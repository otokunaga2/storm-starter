package storm.book.trend;

import java.util.Map;

import org.json.simple.JSONValue;

import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class JsonProjectFunction extends BaseFunction {

	private Fields fields;
	
	public JsonProjectFunction(Fields fields) {
		// TODO Auto-generated constructor stub
		this.fields = fields;
	}
	
	
	
	
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		// TODO Auto-generated method stub
		String json = tuple.getString(0);
		Map<String,Object> map = (Map<String, Object>) JSONValue.parse(json);
		Values values = new Values();
		for(int i=0; i< this.fields.size(); i++){
			values.add(map.get(this.fields.get(i)));
		}
		collector.emit(values);
	}

}
