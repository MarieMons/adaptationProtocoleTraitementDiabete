package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import builder.CarnetBuilder;
import builder.Director;
import builder.ProtocoleBuilder;
import diabete.Carnet;
import diabete.Protocole;

public class AfficherUnProtocole {

	public static void main(String[] args) throws IOException, Exception {
		if(args.length != 1 && args.length != 2) {
			System.out.println("FICHIER_PROTOCOLE [FICHIER_CARNET]");
			System.out.println("Si FICHIER_CARNET alors le protocole affiché est mis à jour en fonction" +
					" des résultats de ce carnet, sinon protocole de base");
		}
		Protocole monProtocoleConstruit = new Director<Protocole>(new ProtocoleBuilder(), Files.readAllLines(new File(args[0]).toPath()), null).construct();
		Carnet monCarnetConstruit = null;
		if(args.length == 2)
			monCarnetConstruit = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(args[1]).toPath()), monProtocoleConstruit).construct();
		System.out.println("-------------------- PROTOCOLE --------------------");
		System.out.println(monProtocoleConstruit.toString_RepasMidi());
		if(monCarnetConstruit != null) {
			System.out.println("-------------------- CARNET --------------------");
			System.out.println(monCarnetConstruit.toString());
		}
	}

}
