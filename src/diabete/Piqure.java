package diabete;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author marie
 * représente une piqure avec la posologie qui associe des seuils de glycémie au nombre d'unités à administrer
 * on peut lui associer un repas pour adapter la posologie si on est le midi
 * dans certains cas la piqure devra être modifiée ponctuellement ou définitivement
 */
public class Piqure {
	
	/**
	 * valeurGlycemie_unitesAAdministrer un dictionnaire qui associe seuil inférieur glycémique
	 * au nombre d'unités à admistrer. ex : si unités indépendants de glycémie une entrée : 0->unités
	 */
	private HashMap<Float,Integer> valeurGlycemie_unitesAAdministrer;
	public HashMap<Float, Integer> getValeurGlycemie_unitesAAdministrer() {
		return valeurGlycemie_unitesAAdministrer;
	}

	/**
	 * ex : lente ou rapide
	 */
	private String description;
	/**
	 * ex : TOUJEO ou HUMALOG
	 */
	private String medicament;
	public String getMedicament() {
		return medicament;
	}

	/**
	 * le repas associé pour pouvoir adapter la posologie
	 * par défaut : repas normal
	 */
	private Repas repas = Repas.NORMAL;
	/**
	 * dans certains cas le repas n'impacte pas la posologie donc on garde repas=normal mais
	 * on veut quand même avoir la description du vrai repas pour l'affichage
	 */
	private String repasDescription;
	/**
	 * lorsque la modification est ponctuelle on la garde en mémoire pour l'inverser ensuite
	 */
	private Integer modificationPonctuelle = null;

	/**
	 * @param valeurGlycemie_unitesAAdministrer, configuration de la posologie
	 */
	public void setValeurGlycemie_unitesAAdministrer(HashMap<Float, Integer> valeurGlycemie_unitesAAdministrer) {
		this.valeurGlycemie_unitesAAdministrer = valeurGlycemie_unitesAAdministrer;
	}
	
	/**
	 * pour configurer la posologie par ajout d'un couple seuil=valeurGlycemie et unités
	 * @param valeurGlycemie
	 * @param unites
	 */
	public void addValeurGlycemie_unitesAAdministrer(float valeurGlycemie, int unites) {
		this.valeurGlycemie_unitesAAdministrer.put(valeurGlycemie, unites);
	}
	
	/**
	 * @param repas, repas à associer
	 */
	public void setRepas(Repas repas) {
		this.repas = repas;
	}

	/**
	 * on cree un objet piqure à partir de sa description et du nom du médicament
	 * @param description
	 * @param medicament
	 */
	public Piqure(String description, String medicament){
		this.description = description;
		this.medicament = medicament;
	}
	
	/**
	 * le même type de piqure, couple (description,médicament), peut être utilisé plusieurs fois
	 * à différentes périodes de la journée et avec une posologie différente
	 * @return un objet piqure avec la même description et le même médicament
	 */
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

	/**
	 * utilisé par protocole
	 * @return la description de la piqure
	 */
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

	/**
	 * utilisé par protocole
	 * @param modification, entier positif ou négatif d'unités à ajouter à toutes les valeurs associées de la posologie
	 * @param longTerme_courtTerme, si true modification définitive sinon temporaire
	 */
	public void modification(int modification, boolean longTerme_courtTerme) {
		for(Float seuil : this.valeurGlycemie_unitesAAdministrer.keySet()) {
			int uniteActuel = this.valeurGlycemie_unitesAAdministrer.get(seuil);
			this.valeurGlycemie_unitesAAdministrer.put(seuil, uniteActuel+modification);
		}
		if(!longTerme_courtTerme)
			this.modificationPonctuelle = modification*(-1);
	}

	/**
	 * utilisé par carnet pour mettre à jour la description du repas
	 * @param repasDescription
	 */
	public void setRepasDescription(String repasDescription) {
		this.repasDescription = repasDescription;
	}
}
