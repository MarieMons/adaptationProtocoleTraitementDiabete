package builder;

import java.util.List;

import diabete.Protocole;

/**
 * @author marie
 * Director du BUILDER PATTERN
 * @param <T> le type de l'objet à construire
 * permet de construire un objet à partir d'un constructeur, d'une liste de lignes, et d'un objet protocole
 * pour la construction d'un carnet
 */
public class Director<T> {
	
	private Builder<T> builder;
	private List<String> lignes;
	private Protocole protocole;
	
	private Printer<T> printer;
	private T object;
	private String nomDeFichier;

	public Director(Builder<T> builder, List<String> lignes, Protocole protocole) {
		this.builder = builder;
		this.lignes = lignes;
		this.protocole = protocole;
	}
	
	public Director(Printer<T> printer, T object, String nomDeFichier) {
		this.printer = printer;
		this.object = object;
		this.nomDeFichier = nomDeFichier;
	}
	
	/**
	 * @return l'objet après l'avoir construit et récupérer le résultat par le constructeur
	 * @throws Exception
	 */
	public T construct() throws Exception {
		this.builder.build(this.lignes, this.protocole);
		return this.builder.getResult();
	}
	
	public void print() throws Exception {
		this.printer.build(this.object);
		this.printer.print(this.nomDeFichier);
	}

}
