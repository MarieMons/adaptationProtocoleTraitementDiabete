package diabete;

import java.util.ArrayList;

/**
 * @author marie
 * représente 5 périodes différentes de la journée
 */
public enum Periode{

	MATIN("matin","le"), MIDI("midi","le"), APRES_MIDI("après-midi","l'"), SOIR("soir","le"), NUIT("nuit","la");
	
	private String description;
	private String articleDefini;
	/**
	 * contient la liste des piqures pour cette période de la journée
	 */
	private ArrayList<Piqure> mesPiqures = new ArrayList<Piqure>();
	
	Periode(String description, String articleDefini) {
		this.description = description;
		this.articleDefini = articleDefini;
	}
	
	/**
	 * @param description
	 * @return l'instance correspondant à description
	 */
	static public Periode getPeriode(String description) {
		switch(description) {
		case "matin" :
			return Periode.MATIN;
		case "midi" :
			return Periode.MIDI;
		case "après-midi" :
			return Periode.APRES_MIDI;
		case "soir" :
			return Periode.SOIR;
		case "nuit" :
			return Periode.NUIT;
		}
		return null;
	}

	/**
	 * utilisé par protocole
	 * @return la description de la période
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * utilisé par protocole
	 * @return l'article défini associé
	 */
	public String getArticleDefini() {
		return articleDefini;
	}

	/**
	 * utilisé par protocole
	 * @return la liste des piqures associées à la période
	 */
	public ArrayList<Piqure> getMesPiqures() {
		return mesPiqures;
	}

	/**
	 * @param piqure, piqure à ajouter à cette période
	 */
	public void addMesPiqures(Piqure piqure) {
		this.mesPiqures.add(piqure);
	}
	
	public String toString() {
		int nombrePiqures = this.mesPiqures.size();
		String res = "Pour " + this.articleDefini + " " + this.description + " vous avez " + nombrePiqures 
				+ " traitement" + (nombrePiqures>1?"s":"") + " par piqûre.\n\n";
		for(int i=0 ; i<nombrePiqures ; i++) {
			res += "Traitement " + (i+1) + ", " + this.mesPiqures.get(i).toString() + "\n";
		}
		return res;
	}
	
}
