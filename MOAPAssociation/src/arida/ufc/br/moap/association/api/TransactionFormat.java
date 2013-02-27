package arida.ufc.br.moap.association.api;

public enum TransactionFormat {
	SIMPLE("simple"),
	LIST("list");
	
	private final String format;
	
	TransactionFormat(String format){
		this.format = format;
	}
}
