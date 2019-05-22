package diabete;

/**
 * @author marie
 * représente une glycémie par sa valeur flottante
 * valeurs globales de l'hypo et l'hyper glycémies
 */
public class Glycemie {
	
	/**
	 * valeur flottante représentant la glycémie
	 */
	private float valeur;
	public static Glycemie hypoGlycemie = new Glycemie((float)0.7);
	public static Glycemie hyperGlycemie = new Glycemie((float)1.1);	

	public Glycemie(float valeur){
		this.valeur = valeur;
	}
	
	public Glycemie(float valeur, Repas repas){
		this.valeur = valeur;
	}

	public float getValeur() {
		return valeur;
	}

	// this>autre --> neg
	// this<autre --> pos
	private float compareTo(Glycemie autre) {
		return autre.getValeur() - this.valeur;
	}
	
	private float objectif() {
		if(this.compareTo(hyperGlycemie) < 0)
			return 1;
		else
			if(this.compareTo(hypoGlycemie) > 0)
				return -1;
		return 0;
	}
	
	public String toString() {
		String res = this.valeur + "g/L (";
		switch((int)this.objectif()) {
		case 0:
			res += "dans l'objectif";
			break;
		case -1:
			res += "hypoglycémie";
			break;
		case 1:
			res += "hyperglycémie";
			break;
		}
		return res + ")";
	}

}
