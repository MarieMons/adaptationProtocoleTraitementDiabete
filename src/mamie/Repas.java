package mamie;

/**
 * @author marie
 * représente un repas : léger, normal ou riche
 * Il faut l'associer à Glycemie et Periode pour obtenir le Protocole correspondant
 */
public enum Repas {

	LEGER("léger"), NORMAL("normal"), RICHE("riche");
	
	private String description = "";
	
	// utilisé par carnet et piqure
	public String getDescription() {
		return description;
	}

	Repas(String description) {
		this.description = description;
	}
	
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
	
	// utilisé par piqure
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
