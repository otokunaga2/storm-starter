package storm.starter.func;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.starter.spout.DiagnosisEvent;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class CityAssignment extends BaseFunction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(CityAssignment.class);
	
	private static Map<String, double[]> CITIES = new HashMap<String, double[]>();
	{// initializee the cities we care about.
		double[] phl = {39.873635, -75.28524};
		CITIES.put("PHL",phl);
		double[] nyc = {40.71448, -74.00598};
		CITIES.put("NYC",nyc);
		double[] sf = {-31.4250142, -62.0841808};
		CITIES.put("SF", sf);
		double[] la = {-34.05374, -118.24307};
		CITIES.put("LA", la);
		
	}
	
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		DiagnosisEvent diagnosis = (DiagnosisEvent) tuple.getValue(0);
		double leastDistance = Double.MAX_VALUE;
		String closetCity = "NONE";
		//Find the closest city
	
		for(Entry<String, double[]> city: CITIES.entrySet()){
			double R = 6371;
			double x =(city.getValue()[0] - diagnosis.lng)* Math.cos((city.getValue()[0] + diagnosis.lng)/2);
			double y = (city.getValue()[1]- diagnosis.lat);
			double d = Math.sqrt(x*x + y*y)*R;
			
			if(d < leastDistance){
				leastDistance = d;
				closetCity = city.getKey();
			}
		}
		List<Object> values = new ArrayList<Object>();
		values.add(closetCity);
		LOG.debug("Closesest city to lat=[" + diagnosis.lat +"], "
				+ "lng={" + diagnosis.lng +"] == ["+ closetCity +"],d=[" + leastDistance +"]");
		collector.emit(values);
		
	}

}
