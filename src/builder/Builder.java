package builder;

import java.util.List;
import java.util.Stack;

import diabete.Protocole;

/**
 * @author marie
 * Cette classe est le builder du BUILDER PATTERN.
 * Permet de construire un objet et de récupérer ce résultat.
 * Utilisée pour construire un Protocole et un Carnet à partir de fichiers textes
 * @param <T> type de l'objet à construire
 */
public abstract class Builder<T> {
	
	/**
	 * l'objet construit
	 */
	protected T result;
	/**
	 * Les fichiers textes attendus sont composés de DEBUT MOTIF, FIN MOTIF.
	 * Permet de vérifier que les motifs ne se chevauchent pas
	 */
	protected Stack<String> parenthesage = new Stack<String>();
	
	/**
	 * @return l'objet construit
	 */
	public T getResult() {
		return this.result;
	}

	/**
	 * @param lignes, les lignes du fichier
	 * @param protocole, besoin du protocole pour construire le carnet
	 * @throws Exception en cas de chevauchement, motif non reconnu...
	 */
	public abstract void build(List<String> lignes, Protocole protocole) throws Exception;
	
}
