package mamie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainSuiviBuiltPEtC {

	public static void main(String[] args) throws IOException, Exception {
		String fichierCarnet = "test/CARNET";
		int i = 1;
		Protocole monProtocoleConstruit = new Director<Protocole>(new ProtocoleBuilder(), Files.readAllLines(new File("save/PROTOCOLE").toPath()), null).construct();
		System.out.println(monProtocoleConstruit.toString());
		//Carnet monCarnetConstruit = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File("CARNET").toPath()), monProtocoleConstruit).construct();
		System.out.println();
		System.out.println("---------- MATIN FORT + MIDI LEGER + MATIN FORT ----------");
		System.out.println();
		Carnet monCarnetConstruit1 = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(fichierCarnet+(i++)).toPath()), monProtocoleConstruit).construct();
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- UN NORMAL + MIDI RICHE + UN FORT ----------");
		System.out.println();
		Carnet monCarnetConstruit2 = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(fichierCarnet+(i++)).toPath()), monProtocoleConstruit).construct();
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- 1 PENDANT DEUX JOURS + SOIR RICHE ----------");
		System.out.println();
		Carnet monCarnetConstruit3 = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(fichierCarnet+(i++)).toPath()), monProtocoleConstruit).construct();
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- HYPO APRES-MIDI + SOIR FORT ----------");
		System.out.println();
		Carnet monCarnetConstruit4 = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(fichierCarnet+(i++)).toPath()), monProtocoleConstruit).construct();
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- HYPO APRES-MIDI + SOIR FAIBLE ----------");
		System.out.println();
		Carnet monCarnetConstruit5 = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(fichierCarnet+(i++)).toPath()), monProtocoleConstruit).construct();
		System.out.println(monProtocoleConstruit.toString());
		System.out.println();
		System.out.println("---------- APRES-MIDI NORMAL + SOIR NORMAL ----------");
		System.out.println();
		Carnet monCarnetConstruit6 = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(fichierCarnet+(i++)).toPath()), monProtocoleConstruit).construct();
		System.out.println(monProtocoleConstruit.toString());
	}

}
