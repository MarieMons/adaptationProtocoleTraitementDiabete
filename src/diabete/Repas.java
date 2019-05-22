package diabete;

/**
 * @author marie
 * représente un repas : léger, normal ou riche
 * Il faut l'associer à Glycemie et Periode pour obtenir le Protocole correspondant
 */
public enum Repas {

	LEGER("léger"), NORMAL("normal"), RICHE("riche");
	
	private String description = "";
	
	/**
	 * utilisé par carnet et piqure
	 * @return la description du repas
	 */
	public String getDescription() {
		return description;
	}

	Repas(String description) {
		this.description = description;
	}
	
	/**
	 * @param description
	 * @return l'instance de repas correspondant à description
	 */
	static public Repas getRepas(String description) {
		switch(description) {
		case "léger" :
			return Repas.LEGER;
		case "normal" :
			return Repas.NORMAL;
		case "riche" :
			return Repas.RICHE;
		}
		return null;
	}
	
	public String toString() {
		return this.description;
	}
	
	/**
	 * utilisé par piqure
	 * @param unitesNormales
	 * @return le nombre d'unités à faire après adaptation en fonction du repas
	 */
	public int adapte(int unitesNormales) {
		switch(this) {
		case LEGER :
			return unitesNormales/2;
		case NORMAL :
			return unitesNormales;
		case RICHE :
			return unitesNormales+1;
		}
		return unitesNormales;
	}
}
