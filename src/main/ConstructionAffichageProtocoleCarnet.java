package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import builder.CarnetBuilder;
import builder.Director;
import builder.ProtocoleBuilder;
import diabete.Carnet;
import diabete.Protocole;

public class ConstructionAffichageProtocoleCarnet {

	public static void main(String[] args) throws IOException, Exception {
		Protocole monProtocoleConstruit = new Director<Protocole>(new ProtocoleBuilder(), Files.readAllLines(new File("save/PROTOCOLE_j16m5").toPath()), null).construct();
		Carnet monCarnetConstruit = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File("save/CARNET_j16m5_j19m5").toPath()), monProtocoleConstruit).construct();
		System.out.println("-------------------- PROTOCOLE --------------------");
		System.out.println(monProtocoleConstruit.toString_RepasMidi());
		System.out.println("-------------------- CARNET --------------------");
		System.out.println(monCarnetConstruit);
	}

}
