package diabete;

import java.util.ArrayList;

/**
 * @author marie
 * représente la surveillance d'une période dont les résultats impactent une autre période
 * les résultats doivent dépasser un seuil ou être entre deux seuils pendant un certain nombre de jours consécutifs
 * pour qu'on applique une modification d'un certain nombre d'unités sur une piqure dont on connaît la description et
 * la période
 * la modification est définitive ou ponctuelle
 */
public class Surveillance {

	/**
	 * si l'autre seuil est également renseigné alors seuil inférieur
	 * sinon dépend de la modification : si négative seuil inf sinon sup
	 */
	private float seuil;
	/**
	 * si renseigné seuil supérieur
	 * sinon -1 à ignorer
	 */
	private float seuil2 = -1;
	public float getSeuil2() {
		return seuil2;
	}

	private int nombreDeJoursConsecutifs;
	public int getNombreDeJoursConsecutifs() {
		return nombreDeJoursConsecutifs;
	}

	/**
	 * positif ou négatif
	 */
	private int modificationEnUnites;
	/**
	 * ex : lente ou rapide
	 */
	private String descriptionPiqure;
	private Periode aSurveiller;
	private Periode impactee;
	/**
	 * si true définitive sinon ponctuelle
	 */
	private boolean longTerme_courtTerme;
	
	private void init(float seuil, int nombreDeJoursConsecutifs, int modification, String descriptionPiqure, Periode aSurveiller, Periode impactee, boolean longTerme_courtTerme) {
		this.seuil = seuil;
		this.modificationEnUnites = modification;
		this.nombreDeJoursConsecutifs = nombreDeJoursConsecutifs;
		this.descriptionPiqure = descriptionPiqure;
		this.aSurveiller = aSurveiller;
		this.impactee = impactee;
		this.longTerme_courtTerme = longTerme_courtTerme;
	}

	public Surveillance(float seuil, int nombreDeJoursConsecutifs, int modification, String descriptionPiqure, Periode aSurveiller, Periode impactee, boolean longTerme_courtTerme) {
		this.init(seuil, nombreDeJoursConsecutifs, modification, descriptionPiqure, aSurveiller, impactee, longTerme_courtTerme);
	}

	public Surveillance(float seuil, float seuil2, int nombreDeJoursConsecutifs, int modification, String descriptionPiqure, Periode aSurveiller, Periode impactee, boolean longTerme_courtTerme) {
		this.init(seuil, nombreDeJoursConsecutifs, modification, descriptionPiqure, aSurveiller, impactee, longTerme_courtTerme);
		this.seuil2 = seuil2;
	}

	public float getSeuil() {
		return seuil;
	}

	/**
	 * utilisé par carnet pour pouvoir adapter le protocole
	 * @return la modification à appliquer
	 */
	public int getModificationEnUnites() {
		return modificationEnUnites;
	}

	/**
	 * utilisé par carnet pour savoir quelle piqure adapter
	 * @return ex : lente ou rappide
	 */
	public String getDescriptionPiqure() {
		return descriptionPiqure;
	}

	/**
	 * utilisé par carnet
	 * @param indiceDebut 
	 * @param historiqueGlycemie, liste des glycémies pour une période donnée
	 * @return true si pendant un minimum de nombre de jours consécutifs 
	 */
	public boolean aModifier(ArrayList<Glycemie> historiqueGlycemie, Integer indiceDebut) {
		int nbGlycTot = historiqueGlycemie.size();
		int nbGlyc = nbGlycTot - indiceDebut;
		if(this.nombreDeJoursConsecutifs > nbGlyc)
			return false;
		else {
			if(this.seuil2 == -1) {
				if(this.modificationEnUnites > 0) {
					for(int i=indiceDebut ; i<nbGlycTot ; i++) {
						Glycemie g = historiqueGlycemie.get(i);
						if(g == null)
							return false;
						else
							if(g.getValeur() < this.getSeuil())
								return false;
					}
				}else {
					for(int i=indiceDebut ; i<nbGlycTot ; i++) {
						Glycemie g = historiqueGlycemie.get(i);
						if(g == null)
							return false;
						else
							if(g.getValeur() > this.getSeuil())
								return false;
					}
				}
			}else {
				for(int i=indiceDebut ; i<nbGlycTot ; i++) {
					Glycemie g = historiqueGlycemie.get(i);
					if(g == null)
						return false;
					else {
						float valeur = g.getValeur();
						if(this.seuil >= valeur || valeur >= this.seuil2)
							return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * utilisé par carnet
	 * @return
	 */
	public Periode getaSurveiller() {
		return aSurveiller;
	}

	/**
	 * utilisé par carnet
	 * @return
	 */
	public Periode getImpactee() {
		return impactee;
	}

	/**
	 * utilisé par carnet
	 * @return
	 */
	public boolean isLongTerme_courtTerme() {
		return longTerme_courtTerme;
	}

}
