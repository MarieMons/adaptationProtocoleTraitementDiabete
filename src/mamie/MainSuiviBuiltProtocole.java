package mamie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainSuiviBuiltProtocole {

	public static void main(String[] args) throws IOException, Exception {
		Protocole monProtocoleConstruit = new Director<Protocole>(new ProtocoleBuilder(), Files.readAllLines(new File("PROTOCOLE").toPath()), null).construct();
		System.out.println(monProtocoleConstruit.toString());
		//creation du carnet
		Carnet monSuivi = new Carnet(monProtocoleConstruit);
		System.out.println();
		System.out.println("---------- MATIN FORT + MIDI LEGER + MATIN FORT ----------");
		System.out.println();
		//ajout de deux glycémies fortes le matin au carnet
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1.7));
		monSuivi.ajoutGlycemie(Periode.MIDI, new Glycemie((float)1), Repas.LEGER);
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1.7));
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- UN NORMAL + MIDI RICHE + UN FORT ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1));
		monSuivi.ajoutGlycemie(Periode.MIDI, new Glycemie((float)1), Repas.RICHE);
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1.6));
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- 1 PENDANT DEUX JOURS + SOIR RICHE ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1));
		monSuivi.ajoutGlycemie(Periode.MATIN, new Glycemie((float)1));
		monSuivi.ajoutGlycemie(Periode.SOIR, new Glycemie((float)1), Repas.RICHE);
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- HYPO APRES-MIDI + SOIR FORT ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.APRES_MIDI, new Glycemie((float)0.6));
		monSuivi.ajoutGlycemie(Periode.SOIR, new Glycemie((float)3));
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- HYPO APRES-MIDI + SOIR FAIBLE ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.APRES_MIDI, new Glycemie((float)0.6));
		monSuivi.ajoutGlycemie(Periode.SOIR, new Glycemie((float)0.8));
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- APRES-MIDI NORMAL + SOIR NORMAL ----------");
		System.out.println();
		monSuivi.ajoutGlycemie(Periode.APRES_MIDI, new Glycemie((float)1));
		monSuivi.ajoutGlycemie(Periode.SOIR, new Glycemie((float)1));
		System.out.println(monProtocoleConstruit.toString());
	}

}
