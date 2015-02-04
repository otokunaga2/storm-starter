package storm.starter.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.starter.aggregator.OutbreakTrendFactory;
import storm.starter.func.CityAssignment;
import storm.starter.func.DispatchAlert;
import storm.starter.func.HourAssignment;
import storm.starter.func.OutbreakDetector;
import storm.starter.spout.DiagnosisEventSpout;
import storm.trident.Stream;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.MemoryMapState;
import storm.trident.tuple.TridentTuple;


public class OutbreakDetectionTopology {
	
	public static StormTopology buildTopology(){
		TridentTopology topology = new TridentTopology();
		DiagnosisEventSpout spout = new DiagnosisEventSpout();
		Stream inputStream = topology.newStream("event", spout);
		inputStream
		.each(new Fields("event"), new DiseaseFilter())
			.each(new Fields("event"),new CityAssignment(), new Fields("city"))
			.each(new Fields("event","city"), new HourAssignment(), new Fields("hour","cityDiseaseHour"))
			.persistentAggregate(new OutbreakTrendFactory(), new Count(), new Fields("count"))
			.newValuesStream()
			.each(new Fields("cityDiseaseHour","count"),
					new OutbreakDetector(), new Fields("alert"))
			.each(new Fields("alert"), new DispatchAlert(), new Fields());
//		.each(new Fields("event"), new CityAssignment(), new Fields("city"))
		
		
		
		
		return null;
		
	}
	
	
	
//  public static class Split extends BaseFunction {
//    @Override
//    public void execute(TridentTuple tuple, TridentCollector collector) {
//      String sentence = tuple.getString(0);
//      for (String word : sentence.split(" ")) {
//        collector.emit(new Values(word));
//      }
//    }
//  }
//
//  public static StormTopology buildTopology(LocalDRPC drpc) {
//    FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 3, new Values("the cow jumped over the moon"),
//        new Values("the man went to the store and bought some candy"), new Values("four score and seven years ago"),
//        new Values("how many apples can you eat"), new Values("to be or not to be the person"));
//    spout.setCycle(true);
//
//    TridentTopology topology = new TridentTopology();
//    TridentState wordCounts = topology.newStream("spout1", spout).parallelismHint(16).each(new Fields("sentence"),
//        new Split(), new Fields("word")).groupBy(new Fields("word")).persistentAggregate(new MemoryMapState.Factory(),
//        new Count(), new Fields("count")).parallelismHint(16);
//
//    topology.newDRPCStream("words", drpc).each(new Fields("args"), new Split(), new Fields("word")).groupBy(new Fields(
//        "word")).stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count")).each(new Fields("count"),
//        new FilterNull()).aggregate(new Fields("count"), new Sum(), new Fields("sum"));
//    return topology.build();
//  }

  public static void main(String[] args) throws Exception {
   Config conf = new Config();
   LocalCluster cluster = new LocalCluster();
   cluster.submitTopology("cdc", conf, buildTopology());
   Thread.sleep(200000);
   cluster.shutdown();
	  
  }
}
