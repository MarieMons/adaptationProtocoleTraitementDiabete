package diabete;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author marie
 * Représente un carnet de glycémie. Les glycémies sont associées à des périodes de la journée.
 * Le carnet est associé à un protocole. Ce dernier est mis à jour au fur et à mesure.
 */
/**
 * @author marie
 *
 */
public class Carnet {

	private Periode derniere;
	public Periode getDerniere() {
		return derniere;
	}

	/**
	 * les glycémies par période
	 */
	private HashMap<Periode,ArrayList<Glycemie>> carnetDeGlycemie = new HashMap<Periode,ArrayList<Glycemie>>();
	public HashMap<Periode, ArrayList<Glycemie>> getCarnetDeGlycemie() {
		return carnetDeGlycemie;
	}

	/**
	 * pour chacune des périodes stockent indice de derniere adaptation plus 1
	 */
	private HashMap<Periode,Integer> carnetDeGlycemie_indiceDerniereAdaptation = new HashMap<Periode,Integer>();
	/**
	 * le protocole associé
	 */
	private Protocole protocoleASuivre;

	/**
	 * @param protocole, le protocole à associer au carnet
	 */
	public Carnet(Protocole protocole) {
		this.protocoleASuivre = protocole;
	}

	/**
	 * @param surveillance
	 * adapte le protocole si nécessaire à partir de l'objet surveillance
	 */
	private void adapte(Surveillance surveillance) {
		Periode periode = surveillance.getaSurveiller();
		ArrayList<Glycemie> lesGlycemies = this.carnetDeGlycemie.get(periode);
		if(this.carnetDeGlycemie.containsKey(periode))
			if(surveillance.aModifier(lesGlycemies, this.carnetDeGlycemie_indiceDerniereAdaptation.get(periode))) {
				this.protocoleASuivre.modification(surveillance.getImpactee(), surveillance.getDescriptionPiqure(), surveillance.getModificationEnUnites(), surveillance.isLongTerme_courtTerme());
				this.carnetDeGlycemie_indiceDerniereAdaptation.put(periode, lesGlycemies.size());
			}
	}

	/**
	 * si la période n'est pas dans le carnet : on l'ajoute et on comble manque avec null
	 * dans tous les cas
	 * @param periode
	 */
	private void comble(Periode periode) {
		if(this.derniere != null) {
			if(!this.carnetDeGlycemie.containsKey(periode)) {
				ArrayList<Glycemie> glycemies = new ArrayList<Glycemie>();
				for(int i=1 ; i<this.carnetDeGlycemie.get(this.derniere).size() ; i++)
					glycemies.add(null);
				this.carnetDeGlycemie.put(periode, glycemies);
				this.carnetDeGlycemie_indiceDerniereAdaptation.put(periode, glycemies.size());
			}
			ArrayList<Periode> lesPeriodes = new ArrayList<Periode>(this.carnetDeGlycemie.keySet());
			lesPeriodes.sort(null);
			Periode zappee;
			int n = lesPeriodes.size(), p = lesPeriodes.indexOf(periode), der = lesPeriodes.indexOf(this.derniere);
			int nbPeriodesZappees = ((n-1) + (p-der)) %n;
			for(int i=0 ; i<nbPeriodesZappees ; i++) {
				zappee = lesPeriodes.get((der + nbPeriodesZappees )% n);
				ArrayList<Glycemie> aCombler = this.carnetDeGlycemie.get(zappee);
				aCombler.add(null);
				this.carnetDeGlycemie.put(zappee, aCombler);
				this.carnetDeGlycemie_indiceDerniereAdaptation.put(zappee, aCombler.size());
			}
		}
	}

	/**
	 * @param periode
	 * @param glycemie
	 * ajoute la glycémie à cette période
	 * si la période est surveillée on adapte le protocole à partir des objets surveillances correspondants
	 */
	public void ajoutGlycemie(Periode periode, Glycemie glycemie) {
		this.comble(periode);
		this.derniere = periode;
		ArrayList<Glycemie> lesGlycemies;
		if(this.carnetDeGlycemie.containsKey(periode)) {
			lesGlycemies = this.carnetDeGlycemie.get(periode);
		}else {
			lesGlycemies = new ArrayList<Glycemie>();
			this.carnetDeGlycemie_indiceDerniereAdaptation.put(periode, 0);
		}
		lesGlycemies.add(glycemie);
		this.carnetDeGlycemie.put(periode, lesGlycemies);
		for(Surveillance surveillance : this.protocoleASuivre.getSurveillanceDePeriodePourModificationPiqure())
			if(surveillance.getaSurveiller() == periode)
				this.adapte(surveillance);
	}

	/**
	 * @param periode
	 * @param glycemie
	 * ajoute la glycémie à cette période
	 * si la période est surveillée on adapte le protocole à partir des objets surveillances correspondants
	 * de plus, le midi on tient compte du repas
	 */
	public void ajoutGlycemie(Periode periode, Glycemie glycemie, Repas repas) {
		this.ajoutGlycemie(periode, glycemie);
		if(periode == Periode.MIDI)
			for(Piqure p :periode.getMesPiqures())
				p.setRepas(repas);
		else
			for(Piqure p :periode.getMesPiqures())
				p.setRepasDescription(repas.getDescription());
	}

	public String toString() {
		String res = "Historique du carnet de glycémie\n\nVous avez pris votre glycémie à différents moments de la "
				+ "journée : ";
		ArrayList<Periode> journee = new ArrayList<Periode>(this.carnetDeGlycemie.keySet());
		journee.sort(null);
		Periode p;
		int i;
		for(i=0 ; i<journee.size()-1 ; i++) {
			p = journee.get(i);
			res += p.getArticleDefini() + " " + p.getDescription() + ", ";
		}
		p = journee.get(i);
		res += p.getArticleDefini() + " " + p.getDescription() + ". Voici les mesures que vous avez relevées, jour par "
				+ "jour.\n\n";
		Glycemie g;
		for(i=1 ; i<this.carnetDeGlycemie.get(this.derniere).size()+1 ; i++) {
			res += "Journée " + i + " :\n";
			for(Periode periode : journee) {
				res += "\t" + periode.getArticleDefini() + " " + periode.getDescription() + " : ";
				//System.out.println(this.carnetDeGlycemie + " " + periode.getDescription() + " " + (i-1));
				try{
					g = this.carnetDeGlycemie.get(periode).get(i-1);
					if(g != null)
						res += "Votre glycémie était de " + g.toString();
					else
						res += "Vous n'aviez pas pris votre glycémie";
					res += ".\n";
				}catch(IndexOutOfBoundsException e) {
					res += "Vous n'aviez pas pris votre glycémie.\n";
				}
			}
			res += "\n";
		}
		return res;
	}

}
