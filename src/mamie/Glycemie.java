package mamie;

/**
 * @author marie
 * représente la glycémie à un moment donné
 * Il faut l'associer à Repas et Periode pour obtenir le Protocole correspondant
 */
public class Glycemie {
	
	//format 1 pas 100
	private float valeur;
	public static float hypoGlycemie = (float)0.7;
	public static float hyperGlycemie = (float)1.1;
	

	public Glycemie(float valeur){
		this.valeur = valeur;
	}
	
	public Glycemie(float valeur, Repas repas){
		this.valeur = valeur;
		this.setRepas(repas);
	}

	// utilisé par surveillance
	public float getValeur() {
		return valeur;
	}

	/*private int compare() {
		if(this.valeur > hyperGlycemie)
			return 1;
		else{
			if(this.valeur < hypoGlycemie)
				return -1;
			else
				return 0;
		}
	}*/
	
	private void setRepas(Repas repas) {
	}

}
