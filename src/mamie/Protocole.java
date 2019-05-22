package mamie;

import java.util.ArrayList;

/**
 * @author marie
 */
public class Protocole {
	
	private ArrayList<Periode> periodes;
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

	// utilisé par carnet
	public void modification(Periode periode, String descriptionPiqure, int modification, boolean longTerme_courtTerme) {
		for(Piqure p : periode.getMesPiqures())
			if(p.getDescription().equals(descriptionPiqure))
				p.modification(modification, longTerme_courtTerme);
	}

	// utilisé par carnet
	public ArrayList<Surveillance> getSurveillanceDePeriodePourModificationPiqure() {
		return surveillanceDePeriodePourModificationPiqure;
	}

	// config
	public void setSurveillanceDePeriodePourModificationPiqure(ArrayList<Surveillance> surveillanceDePeriodePourModificationPiqure) {
		this.surveillanceDePeriodePourModificationPiqure = surveillanceDePeriodePourModificationPiqure;
	}

	// config
	public void addSurveillanceDePeriodePourModificationPiqure(Surveillance surveillance) {
		this.surveillanceDePeriodePourModificationPiqure.add(surveillance);
	}
	
	public void addPeriode(Periode periode) {
		if(!this.periodes.contains(periode))
			this.periodes.add(periode);
	}

}
