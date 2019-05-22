package mamie;

import java.util.ArrayList;
import java.util.HashMap;

public class MainConfigDisplay {

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
		//traitement 3x dans la journée
		Periode matin = Periode.MATIN;
		Periode midi = Periode.MIDI;
		Periode soir = Periode.SOIR;
		//association période-traitement
		ArrayList<Piqure> traitementMatin = new ArrayList<Piqure>();
		traitementMatin.add(rapideDuMatin);
		traitementMatin.add(lenteDuMatin);
		matin.setMesPiqures(traitementMatin);
		ArrayList<Piqure> traitementMidi = new ArrayList<Piqure>();
		traitementMidi.add(rapideDuMidi);
		midi.setMesPiqures(traitementMidi);
		ArrayList<Piqure> traitementSoir = new ArrayList<Piqure>();
		traitementSoir.add(rapideDuSoir);
		soir.setMesPiqures(traitementSoir);
		//création protocole de la journée
		ArrayList<Periode> maJournee = new ArrayList<Periode>();
		maJournee.add(matin);
		maJournee.add(midi);
		maJournee.add(soir);
		Protocole monProtocole = new Protocole(maJournee);
		//affichage du protocole
		System.out.println(monProtocole.toString());
	}

}
