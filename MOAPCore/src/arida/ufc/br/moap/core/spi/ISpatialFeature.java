package arida.ufc.br.moap.core.spi;

public interface ISpatialFeature<S> {
	public S getGeometry();
	public S setGeometry();
}
