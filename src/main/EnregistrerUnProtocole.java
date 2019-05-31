package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import builder.CarnetBuilder;
import builder.Director;
import builder.ProtocoleBuilder;
import builder.ProtocolePrinter;
import diabete.Carnet;
import diabete.Protocole;

public class EnregistrerUnProtocole {

	public static void main(String[] args) throws IOException, Exception {
		if(args.length != 3) {
			System.out.println("ANCIEN_PROTOCOLE NOUVEAU_PROTOCOLE FICHIER_CARNET");
		}
		Protocole monProtocoleConstruit = new Director<Protocole>(new ProtocoleBuilder(), Files.readAllLines(new File(args[0]).toPath()), null).construct();
		new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File(args[2]).toPath()), monProtocoleConstruit).construct();
		Director<Protocole> enregistreurDeProtocole = new Director<Protocole>(new ProtocolePrinter(), monProtocoleConstruit, args[1]);
		enregistreurDeProtocole.print();
	}

}
