package storm.starter.trident;



import org.slf4j.Logger;

import clojure.tools.logging.impl.LoggerFactory;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.starter.spout.DiagnosisEvent;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFilter;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.MemoryMapState;
import storm.trident.tuple.TridentTuple;


public class DiseaseFilter extends BaseFilter{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(DiseaseFilter.class);
public static class Split extends BaseFunction {
    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
      String sentence = tuple.getString(0);
      for (String word : sentence.split(" ")) {
        collector.emit(new Values(word));
      }
    }
  }


@Override
public boolean isKeep(TridentTuple tuple) {
	DiagnosisEvent diagnosis = (DiagnosisEvent) tuple.getValue(0);
	Integer code = Integer.parseInt(diagnosis.diagnosisCode);
	if(code.intValue() <= 322){
		LOG.debug("Emitting disease [" + diagnosis.diagnosisCode +"]");
		return true;
	}else{
		LOG.debug("Filtering disease [" + diagnosis.diagnosisCode +"]");
		return false;
	}
}

}
