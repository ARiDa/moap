package arida.ufc.br.moap.function.api;

public interface IDistanceFunction <T> extends IBinaryFunction<T, T, Double> {
    
    @Override
    public Double evaluate(T obj1,T obj2);
}
