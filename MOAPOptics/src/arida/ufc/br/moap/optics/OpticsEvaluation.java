package arida.ufc.br.moap.optics;

import java.util.List;

import arida.ufc.br.moap.clustering.api.ICluster;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.core.imp.Reporter.ReporterLevel;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;

public class OpticsEvaluation {
	
	private final Reporter reporter = new Reporter(this.getClass());

	public void evaluate(Optics<Object> optics){
		
		this.reporter.setReport("Optics evaluation", ReporterLevel.INFO );
		
		int nClusters = optics.getResult().getInstances().size()-1;
		
		System.out.println("Optics Evaluation:");
		System.out.println("N of clusters "+nClusters);
		
		for(Object object : optics.getResult().getInstances()){
			@SuppressWarnings("unchecked")
			ICluster<Object> cluster = (ICluster<Object>)object;
			
			if(cluster.getId() != Optics.NOISE_ID){
				System.out.println("Cluster "+cluster.getId()+" with "+cluster.getObjects().size()+" members");
			}
			else{
				System.out.println("Noise "+cluster.getObjects().size());
			}

		}
		
		double intraCluster = intraClusterDistance(optics);
		System.out.println("Intra-Cluster Distance: "+intraCluster);
		
	}
	
	private double intraClusterDistance(Optics<Object> optics){
		
//		List<Object> list = new ArrayList<>();
//		list.add(opt)
		
		double avg = 0.0;
		int count = 0;
		IDistanceFunction<Object> df = optics.getDistanceFunction();
		
		for(Object object : optics.getResult().getInstances()){
			@SuppressWarnings("unchecked")
			ICluster<Object> cluster = (ICluster<Object>)object;
			List<Object> list = cluster.getObjects();
			int size = list.size();
			
			for(int i=0;i<size;i++){
				Object m1 = list.get(i);
				for(int j=i+1;j<size;j++){
					Object m2 = list.get(j);
					
					avg += df.evaluate(m1, m2);
					count++;
				}
			}
			
		}
		
		return (double)avg/count;
		
	}
}
