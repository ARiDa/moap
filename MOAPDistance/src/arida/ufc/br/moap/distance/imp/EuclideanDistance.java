package arida.ufc.br.moap.distance.imp;

import arida.ufc.br.moap.distance.spi.IDistanceFunction;

public class EuclideanDistance implements IDistanceFunction<double[]> {

    
    
    @Override
    public Double evaluate(double[] o1, double[] o2) {
        // TODO Auto-generated method stub
        if(o1.length!=o2.length){
            throw new IllegalArgumentException("size of the two arrays are differents: "+o1.length+" against "+o2.length);
        }
        
        return 0.0;
    }

    @Override
    public String getName() {
        return "Euclidian Distance";
    }
}
