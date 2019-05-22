package mamie;

import java.util.List;

public class Director<T> {
	
	private Builder<T> builder;
	private List<String> lignes;
	private Protocole protocole;

	public Director(Builder<T> builder, List<String> lignes, Protocole protocole) {
		this.builder = builder;
		this.lignes = lignes;
		this.protocole = protocole;
	}
	
	public T construct() throws Exception {
		this.builder.build(this.lignes, this.protocole);
		return this.builder.getResult();
	}

}
