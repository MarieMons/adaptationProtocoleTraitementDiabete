package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import builder.CarnetBuilder;
import builder.Director;
import builder.ProtocoleBuilder;
import diabete.Carnet;
import diabete.Protocole;

public class AfficherUnCarnet {

	public static void main(String[] args) throws IOException, Exception {
		if(args.length != 1) {
			System.out.println("FICHIER_CARNET FICHIER_PROTOCOLE");
		}
		Protocole monProtocoleConstruit = new Director<Protocole>(new ProtocoleBuilder(), Files.readAllLines(new File(args[1]).toPath()), null).construct();
		Carnet monCarnetConstruit = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(args[0]).toPath()), monProtocoleConstruit).construct();
		System.out.println("-------------------- CARNET --------------------");
		System.out.println(monCarnetConstruit);
	}

}
