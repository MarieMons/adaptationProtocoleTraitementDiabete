package diabete;

import java.util.ArrayList;

/**
 * @author marie
 */
public class Protocole {

	private ArrayList<Periode> periodes;
	public ArrayList<Periode> getPeriodes() {
		return periodes;
	}

	private ArrayList<Surveillance> surveillanceDePeriodePourModificationPiqure = new ArrayList<Surveillance>();

	public Protocole(ArrayList<Periode> periodes){
		this.periodes = periodes;
		this.periodes.sort(null);
	}

	public String toString() {
		String res = "Voici le protocole pour une journée complète. Vous avez un traitement par piqûre à "+
				this.periodes.size() + " moments de la journée : ";
		Periode p;
		int i;
		for(i=0 ; i<this.periodes.size()-1 ; i++) {
			p = this.periodes.get(i);
			res += p.getArticleDefini() + " " + p.getDescription() + ", ";
		}
		p = this.periodes.get(i);
		res += p.getArticleDefini() + " " + p.getDescription() + ". \nVoici les protocoles détaillés par "+
				"période de la journée.";
		for(Periode pe : this.periodes)
			res += "\n\n" + pe.toString();
		return res;
	}

	/**
	 * @return une chaine représentant le protocole une fois par type de repas
	 */
	public String toString_Repas() {
		String res = "";
		for(Repas repas : Repas.values()) {
			for(Periode periode : this.periodes) {
				if(periode == Periode.MIDI) {
					for(Piqure piqure : periode.getMesPiqures())
						piqure.setRepas(repas);
				}
				else
					for(Piqure piqure : periode.getMesPiqures())
						piqure.setRepasDescription(repas.getDescription());
			}
			res += "POUR UN REPAS " + repas.getDescription() + ": \n";
			res += this.toString() + "\n";
		}
		return res;
	}

	/**
	 * @return une chaine représentant le protocole avec le protocole du midi répété pour tous les types de repas
	 */
	public String toString_RepasMidi() {
		int nbPeriodes = this.periodes.size();
		String res = "Voici le protocole pour une journée complète. Vous avez un traitement par piqûre à "+
				nbPeriodes + " moments de la journée : ";
		Periode p;
		int i;
		for(i=0 ; i<nbPeriodes-1 ; i++) {
			p = this.periodes.get(i);
			res += p.getArticleDefini() + " " + p.getDescription() + ", ";
		}
		p = this.periodes.get(i);
		res += p.getArticleDefini() + " " + p.getDescription() + ". \nVoici les protocoles détaillés par "+
				"période de la journée.";
		for(Periode pe : this.periodes) {
			if(pe == Periode.MIDI) {
				for(Repas repas : Repas.values()) {
					for(Piqure piqure : pe.getMesPiqures())
						piqure.setRepas(repas);
					res += "\nPOUR UN REPAS " + repas.getDescription() + ": \n";
					res += pe.toString();
				}
			}else {
				res += "\n" + pe.toString();
			}
		}
		return res;
	}

	/**
	 * utilisé par carnet
	 * @param periode impactee
	 * @param descriptionPiqure "lente" ou "rapide"
	 * @param modification, positif ou négatif, nombre d'unités à ajouter
	 * @param longTerme_courtTerme, true si définitif false si ponctuelle
	 */
	public void modification(Periode periode, String descriptionPiqure, int modification, boolean longTerme_courtTerme) {
		for(Piqure p : periode.getMesPiqures())
			if(p.getDescription().equals(descriptionPiqure))
				p.modification(modification, longTerme_courtTerme);
	}

	/**
	 * utilisé par carnet
	 * @return
	 */
	public ArrayList<Surveillance> getSurveillanceDePeriodePourModificationPiqure() {
		return surveillanceDePeriodePourModificationPiqure;
	}

	public void setSurveillanceDePeriodePourModificationPiqure(ArrayList<Surveillance> surveillanceDePeriodePourModificationPiqure) {
		this.surveillanceDePeriodePourModificationPiqure = surveillanceDePeriodePourModificationPiqure;
	}

	public void addSurveillanceDePeriodePourModificationPiqure(Surveillance surveillance) {
		this.surveillanceDePeriodePourModificationPiqure.add(surveillance);
	}

	/**
	 * ajoute la période si elle n'y est pas déjà
	 * @param periode
	 */
	public void addPeriode(Periode periode) {
		if(!this.periodes.contains(periode))
			this.periodes.add(periode);
	}

}
