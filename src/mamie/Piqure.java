package mamie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author marie
 * dépend ou non de la valeur de la glycémie
 */
public class Piqure {
	
	/**
	 * valeurGlycemie_unitesAAdministrer un dictionnaire qui associe seuil inférieur glycémique
	 * au nombre d'unités à admistrer. ex : si unités indépendants de glycémie une entrée : 0->unités
	 */
	private HashMap<Float,Integer> valeurGlycemie_unitesAAdministrer;

	private String description;
	private String medicament;
	private Repas repas = Repas.NORMAL;
	private String repasDescription;
	private Integer modificationPonctuelle = null;

	// config
	public void setValeurGlycemie_unitesAAdministrer(HashMap<Float, Integer> valeurGlycemie_unitesAAdministrer) {
		this.valeurGlycemie_unitesAAdministrer = valeurGlycemie_unitesAAdministrer;
	}
	
	// config
	public void addValeurGlycemie_unitesAAdministrer(float valeurGlycemie, int unites) {
		this.valeurGlycemie_unitesAAdministrer.put(valeurGlycemie, unites);
	}
	
	// config
	public void setRepas(Repas repas) {
		this.repas = repas;
	}

	public Piqure(String description, String medicament){
		this.description = description;
		this.medicament = medicament;
	}
	
	// builder
	public Piqure getShallowCopy() {
		Piqure copy = new Piqure(this.description, this.medicament);
		return copy;
	}

	/**
	 * @param glycemie, le flottant décrivant la glycémie
	 * @return le nombre d'unités à administrer en fonction de la glycémie
	 */
	/*private int unitesAAdministrer(float glycemie) {
		int unites = 0;
		for(Float seuilInfGly : this.valeurGlycemie_unitesAAdministrer.keySet()) {
			if(seuilInfGly <= glycemie)
				unites = this.valeurGlycemie_unitesAAdministrer.get(seuilInfGly);
			else
				return unites;
		}
		return unites;
	}*/

	// utilisé par protocole
	public String getDescription() {
		return description;
	}

	public String toString() {
		String res = "Piqure de " + this.description + " (" + this.medicament + ")" + " : ";
		ArrayList<Float> seuils = new ArrayList<Float>(this.valeurGlycemie_unitesAAdministrer.keySet());
		seuils.sort(null);
		String desc;
		if(this.repasDescription != null)
			desc = this.repasDescription;
		else
			desc = this.repas.getDescription();
		if(seuils.size() == 1 && seuils.get(0)==0)
			res += "vous devez faire " +
					this.repas.adapte(this.valeurGlycemie_unitesAAdministrer.get(seuils.iterator().next())) +
					" unités pour un repas " + desc + ".";
		else {
			res += "le nombre d'unités que vous devez faire dépend de votre glycémie.\n";
			String gl = "g/L";
			float seuilPrec = -1;
			int unite;
			for(Float seuilActuel : seuils) {
				if(seuilPrec != -1) {
					unite = this.repas.adapte(this.valeurGlycemie_unitesAAdministrer.get(seuilPrec));
					res += "\tSi votre glycémie est comprise entre " + String.format("%.02f", seuilPrec) + gl 
							+ " et " + String.format("%.02f", seuilActuel) + gl	+ " vouz devez faire " +
							unite + " unité" + (unite>1?"s":"") + " pour un repas " + desc + ".\n";
				}
				seuilPrec = seuilActuel;
			}
			unite = this.repas.adapte(this.valeurGlycemie_unitesAAdministrer.get(seuilPrec));
			res += "\tSi votre glycémie est supérieure à " + String.format("%.02f", seuilPrec) + gl +
					" vous devez faire " + unite + " unité" + (unite>1?"s":"") + " pour un repas " + desc + ". ";
		}
		if(this.repas != Repas.NORMAL)
			this.repas = Repas.NORMAL;
		if(this.repasDescription != null)
			this.repasDescription = null;
		if(this.modificationPonctuelle != null) {
			this.modification(this.modificationPonctuelle, true);
			this.modificationPonctuelle = null;
		}
		return res;
	}

	// utilisé par protocole
	public void modification(int modification, boolean longTerme_courtTerme) {
		for(Float seuil : this.valeurGlycemie_unitesAAdministrer.keySet()) {
			int uniteActuel = this.valeurGlycemie_unitesAAdministrer.get(seuil);
			this.valeurGlycemie_unitesAAdministrer.put(seuil, uniteActuel+modification);
		}
		if(!longTerme_courtTerme)
			this.modificationPonctuelle = modification*(-1);
	}

	// utilisé par carnet
	public void setRepasDescription(String repasDescription) {
		this.repasDescription = repasDescription;
	}
}
