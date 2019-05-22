package mamie;

import java.util.ArrayList;

/**
 * @author marie
 * seuil/nbjour+modif
 */
public class Surveillance {

	// si un seuil : soit inf soit sup
	// si deux seuils : inf
	private float seuil;
	// si present : sup
	private float seuil2 = -1;
	private int nombreDeJoursConsecutifs;
	private int modificationEnUnites;
	private String descriptionPiqure;
	private Periode aSurveiller;
	private Periode impactee;
	private boolean longTerme_courtTerme;

	public Surveillance(float seuil, int nombreDeJoursConsecutifs, int modification, String descriptionPiqure, Periode aSurveiller, Periode impactee, boolean longTerme_courtTerme) {
		this.seuil = seuil;
		this.modificationEnUnites = modification;
		this.nombreDeJoursConsecutifs = nombreDeJoursConsecutifs;
		this.descriptionPiqure = descriptionPiqure;
		this.aSurveiller = aSurveiller;
		this.impactee = impactee;
		this.setLongTerme_courtTerme(longTerme_courtTerme);
	}

	public Surveillance(float seuil, float seuil2, int nombreDeJoursConsecutifs, int modification, String descriptionPiqure, Periode aSurveiller, Periode impactee, boolean longTerme_courtTerme) {
		this.seuil = seuil;
		this.seuil2 = seuil2;
		this.modificationEnUnites = modification;
		this.nombreDeJoursConsecutifs = nombreDeJoursConsecutifs;
		this.descriptionPiqure = descriptionPiqure;
		this.aSurveiller = aSurveiller;
		this.impactee = impactee;
		this.setLongTerme_courtTerme(longTerme_courtTerme);
	}

	private float getSeuil() {
		return seuil;
	}

	// utilisé par carnet
	public int getModificationEnUnites() {
		return modificationEnUnites;
	}

	// utilisé par carnet
	public String getDescriptionPiqure() {
		return descriptionPiqure;
	}

	// utilisé par carnet
	public boolean aModifier(ArrayList<Glycemie> historiqueGlycemie) {
		int nbGlyc = historiqueGlycemie.size();
		if(this.nombreDeJoursConsecutifs > nbGlyc)
			return false;
		else {
			if(this.seuil2 == -1) {
				if(this.modificationEnUnites > 0) {
					for(int i=0 ; i<this.nombreDeJoursConsecutifs ; i++) {
						if(historiqueGlycemie.get(nbGlyc-1-i).getValeur() < this.getSeuil())
							return false;
					}
				}else {
					for(int i=0 ; i<this.nombreDeJoursConsecutifs ; i++) {
						if(historiqueGlycemie.get(nbGlyc-1-i).getValeur() > this.getSeuil())
							return false;
					}
				}
			}else {
				for(int i=0 ; i<this.nombreDeJoursConsecutifs ; i++) {
					float valeur = historiqueGlycemie.get(nbGlyc-1-i).getValeur();
					if(this.seuil >= valeur || valeur >= this.seuil2)
						return false;
				}
			}
		}
		return true;
	}

	// utilisé par carnet
	public Periode getaSurveiller() {
		return aSurveiller;
	}

	// utilisé par carnet
	public Periode getImpactee() {
		return impactee;
	}

	// utilisé par carnet
	public boolean isLongTerme_courtTerme() {
		return longTerme_courtTerme;
	}

	private void setLongTerme_courtTerme(boolean longTerme_courtTerme) {
		this.longTerme_courtTerme = longTerme_courtTerme;
	}

}
