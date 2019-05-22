package builder;

import java.util.ArrayList;
import java.util.HashMap;

import diabete.Periode;
import diabete.Piqure;
import diabete.Protocole;
import diabete.Surveillance;

public class ProtocolePrinter extends Printer<Protocole> {

	@Override
	public void build(Protocole object) throws Exception {
		this.result = "PROTOCOLE";
		HashMap<String,String> blocsPiqurePeriode = new HashMap<String,String>();
		ArrayList<Surveillance> surveillances = object.getSurveillanceDePeriodePourModificationPiqure();
		for(Periode periode : object.getPeriodes()) {
			for(Piqure piqure : periode.getMesPiqures()) {
				String debutPiqure = "DEBUT PIQURE\n" + piqure.getDescription() + " " + piqure.getMedicament();
				String blocPeriode = "";
				if(blocsPiqurePeriode.containsKey(debutPiqure))
					blocPeriode = blocsPiqurePeriode.get(debutPiqure);
				else
					blocPeriode = "";
				blocPeriode += "\nDEBUT PERIODE\n" + periode.getDescription() + "\n";
				HashMap<Float, Integer> posologie = piqure.getValeurGlycemie_unitesAAdministrer();
				Integer unitesBase = null;
				String blocAdaptation = "";
				Float premierSeuil = null;
				ArrayList<Float> seuils = new ArrayList<Float>(posologie.keySet());
				seuils.sort(null);
				for(Float seuil : seuils) {
					int unites = posologie.get(seuil);
					if(unitesBase == null) {
						premierSeuil = seuil;
						unitesBase = unites;
						blocPeriode += "DEBUT POSOLOGIE\n" + seuil + " " + unitesBase + "\n";
					}else {
						if(blocAdaptation == "") {
							blocAdaptation += "DEBUT ADAPTATION\n" + premierSeuil + " 0" + "\n";
						}
						blocAdaptation += seuil + " " + (unites-unitesBase) + "\n";
					}
				}
				if(blocAdaptation != "") {
					blocAdaptation += "FIN ADAPTATION\n";
					blocPeriode += blocAdaptation;
				}
				HashMap<Periode,String> blocSurveillance = new HashMap<Periode,String>();
				for(Surveillance surveillance : surveillances) {
					if(surveillance.getImpactee() == periode && surveillance.getDescriptionPiqure() == piqure.getDescription()) {
						Periode surveillee = surveillance.getaSurveiller();
						String tmp;
						if(!blocSurveillance.containsKey(surveillee))
							tmp = "DEBUT SURVEILLANCE\n" + surveillee.getDescription() + "\nDEBUT MODIFICATION\n";
						else
							tmp = blocSurveillance.get(surveillee);
						float seuil2 = surveillance.getSeuil2();
						tmp += surveillance.getSeuil() + " " + (seuil2==-1?"":seuil2+" ") +
								surveillance.getNombreDeJoursConsecutifs() + " " + surveillance.getModificationEnUnites()
								+ " " + (surveillance.isLongTerme_courtTerme()?"oui":"non") + "\n";
						blocSurveillance.put(surveillee, tmp);
					}
				}
				for(String bs : blocSurveillance.values()) {
					bs += "FIN MODIFICATION\nFIN SURVEILLANCE\n";
					blocPeriode += bs;
				}
				blocPeriode += "FIN POSOLOGIE\nFIN PERIODE";
				blocsPiqurePeriode.put(debutPiqure, blocPeriode);
			}
		}
		for(String piqure : blocsPiqurePeriode.keySet()) {
			this.result += "\n" + piqure + blocsPiqurePeriode.get(piqure) + "\nFIN PIQURE";
		}
	}

}
