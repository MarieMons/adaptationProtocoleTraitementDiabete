package mamie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author marie
 *   
 */
public class Carnet {

	private HashMap<Periode,ArrayList<Glycemie>> carnetDeGlycemie;
	private Protocole protocoleASuivre;
	
	public Carnet(Protocole protocole) {
		this.protocoleASuivre = protocole;
		this.carnetDeGlycemie = new HashMap<Periode,ArrayList<Glycemie>>();
	}

	private void adapte(Surveillance surveillance) {
		if(this.carnetDeGlycemie.containsKey(surveillance.getaSurveiller()))
			if(surveillance.aModifier(this.carnetDeGlycemie.get(surveillance.getaSurveiller())))
				this.protocoleASuivre.modification(surveillance.getImpactee(), surveillance.getDescriptionPiqure(), surveillance.getModificationEnUnites(), surveillance.isLongTerme_courtTerme());
	}

	// config
	public void ajoutGlycemie(Periode periode, Glycemie glycemie) {
		ArrayList<Glycemie> lesGlycemies;
		if(this.carnetDeGlycemie.containsKey(periode)) {
			lesGlycemies = this.carnetDeGlycemie.get(periode);
		}else {
			lesGlycemies = new ArrayList<Glycemie>();
		}
		lesGlycemies.add(glycemie);
		this.carnetDeGlycemie.put(periode, lesGlycemies);
		for(Surveillance surveillance : this.protocoleASuivre.getSurveillanceDePeriodePourModificationPiqure())
			if(surveillance.getaSurveiller() == periode)
				this.adapte(surveillance);
	}

	// config
	public void ajoutGlycemie(Periode periode, Glycemie glycemie, Repas repas) {
		this.ajoutGlycemie(periode, glycemie);
		if(periode == Periode.MIDI)
			for(Piqure p :periode.getMesPiqures())
				p.setRepas(repas);
		else
			for(Piqure p :periode.getMesPiqures())
				p.setRepasDescription(repas.getDescription());
	}

}
