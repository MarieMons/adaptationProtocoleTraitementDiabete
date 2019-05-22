package builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author marie
 * Cette classe est le builder du BUILDER PATTERN.
 * Permet de construire un objet et de récupérer ce résultat.
 * Utilisée pour construire un Protocole et un Carnet à partir de fichiers textes
 * @param <T> type de l'objet à construire
 */
public abstract class Printer<T> {
	
	/**
	 * l'objet construit
	 */
	protected String result;
	
	/**
	 * @return l'objet construit
	 * @throws FileNotFoundException 
	 */
	public void print(String nomDeFichier) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(nomDeFichier));
		pw.print(this.result);
		pw.flush();
	}

	/**
	 * @param lignes, les lignes du fichier
	 * @param protocole, besoin du protocole pour construire le carnet
	 * @throws Exception en cas de chevauchement, motif non reconnu...
	 */
	public abstract void build(T object) throws Exception;
	
}
