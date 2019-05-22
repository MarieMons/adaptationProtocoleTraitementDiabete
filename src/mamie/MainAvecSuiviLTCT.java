package mamie;

import java.util.ArrayList;
import java.util.HashMap;

public class MainAvecSuiviLTCT {

	public static void main(String[] args) {
		//création de toutes les piqures de la journée
		Piqure lenteDuMatin = new Piqure("lente","TOUJEO");
		Piqure rapideDuMatin = new Piqure("rapide", "HUMALOG");
		Piqure rapideDuMidi = new Piqure("rapide", "HUMALOG");
		Piqure rapideDuSoir = new Piqure("rapide", "HUMALOG");
		//configuration avec seuil glycémie / unites
		HashMap<Float,Integer> conf1 = new HashMap<Float, Integer>();
		conf1.put((float) 0, 11);
		lenteDuMatin.setValeurGlycemie_unitesAAdministrer(conf1);
		HashMap<Float,Integer> conf2 = new HashMap<Float, Integer>();
		conf2.put((float) 0, 8);
		rapideDuMatin.setValeurGlycemie_unitesAAdministrer(conf2);
		int uniteMidiRef = 6;
		HashMap<Float,Integer> conf3 = new HashMap<Float, Integer>();
		conf3.put((float) 0, uniteMidiRef-3);
		conf3.put((float) 0.9, uniteMidiRef-2);
		conf3.put((float) 1.1, uniteMidiRef);
		conf3.put((float) 1.9, uniteMidiRef+1);
		conf3.put((float) 2.4, uniteMidiRef+2);
		rapideDuMidi.setValeurGlycemie_unitesAAdministrer(conf3);
		HashMap<Float,Integer> conf4 = new HashMap<Float, Integer>();
		conf4.put((float) 2.5, 2);
		rapideDuSoir.setValeurGlycemie_unitesAAdministrer(conf4);
		//association période-traitement
		ArrayList<Piqure> traitementMatin = new ArrayList<Piqure>();
		traitementMatin.add(rapideDuMatin);
		traitementMatin.add(lenteDuMatin);
		Periode.MATIN.setMesPiqures(traitementMatin);
		ArrayList<Piqure> traitementMidi = new ArrayList<Piqure>();
		traitementMidi.add(rapideDuMidi);
		Periode.MIDI.setMesPiqures(traitementMidi);
		ArrayList<Piqure> traitementSoir = new ArrayList<Piqure>();
		traitementSoir.add(rapideDuSoir);
		Periode.SOIR.setMesPiqures(traitementSoir);
		//création protocole de la journée
		ArrayList<Periode> maJournee = new ArrayList<Periode>();
		maJournee.add(Periode.MATIN);
		maJournee.add(Periode.MIDI);
		maJournee.add(Periode.SOIR);
		Protocole monProtocole = new Protocole(maJournee);
		//affichage du protocole
		System.out.println(monProtocole.toString());
		//creation des seuils de surveillances
		ArrayList<Surveillance> surveillanceGlycemie = new ArrayList<Surveillance>();
		surveillanceGlycemie.add(new Surveillance((float)1.6, 2, 1, "lente", Periode.MATIN, Periode.MATIN, true));
		surveillanceGlycemie.add(new Surveillance((float)0.95, (float)1.1, 2, -1, "lente", Periode.MATIN, Periode.MATIN, true));
		surveillanceGlycemie.add(new Surveillance((float)0.95, 1, -1, "lente", Periode.MATIN, Periode.MATIN, true));
		surveillanceGlycemie.add(new Surveillance((float)0.8, 1, -2, "lente", Periode.MATIN, Periode.MATIN, true));
		surveillanceGlycemie.add(new Surveillance((float)Glycemie.hypoGlycemie, 1, -2, "rapide", Periode.APRES_MIDI, Periode.SOIR, false));
		//ajout surveillance au carnet
		monProtocole.setSurveillanceDePeriodePourModificationPiqure(surveillanceGlycemie);
		//creation du carnet
		Carnet monSuivi = new Carnet(monProtocole);
		System.out.println();
		System.out.println("---------- MATIN FORT + MIDI LEGER + MATIN FORT ----------");
		System.out.println();
		//ajout de deux glycémies fortes le matin au carnet
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1.7));
		monSuivi.ajoutGlycemie(Periode.MIDI, new Glycemie((float)1), Repas.LEGER);
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1.7));
		System.out.println(monProtocole.toString());
		System.out.println();
		System.out.println("---------- UN NORMAL + MIDI RICHE + UN FORT ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1));
		monSuivi.ajoutGlycemie(Periode.MIDI, new Glycemie((float)1), Repas.RICHE);
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1.6));
		System.out.println(monProtocole.toString());
		System.out.println();
		System.out.println("---------- 1 PENDANT DEUX JOURS + SOIR RICHE ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1));
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1));
		monSuivi.ajoutGlycemie(Periode.SOIR, new Glycemie((float)1), Repas.RICHE);
		System.out.println(monProtocole.toString());
		System.out.println();
		System.out.println("---------- HYPO APRES-MIDI + SOIR FORT ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.APRES_MIDI, new Glycemie((float)0.6));
		monSuivi.ajoutGlycemie(Periode.SOIR, new Glycemie((float)3));
		System.out.println(monProtocole.toString());
		System.out.println();
		System.out.println("---------- HYPO APRES-MIDI + SOIR FAIBLE ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.APRES_MIDI, new Glycemie((float)0.6));
		monSuivi.ajoutGlycemie(Periode.SOIR, new Glycemie((float)0.8));
		System.out.println(monProtocole.toString());
		System.out.println();
		System.out.println("---------- APRES-MIDI NORMAL + SOIR NORMAL ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.APRES_MIDI, new Glycemie((float)1));
		monSuivi.ajoutGlycemie(Periode.SOIR, new Glycemie((float)1));
		System.out.println(monProtocole.toString());
	}

}
