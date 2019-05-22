package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import builder.CarnetBuilder;
import builder.CarnetPrinter;
import builder.Director;
import builder.ProtocoleBuilder;
import builder.ProtocolePrinter;
import diabete.Carnet;
import diabete.Protocole;

public class EnregistrementCarnetProtocole {

	public static void main(String[] args) throws IOException, Exception {
		Protocole monProtocoleConstruit = new Director<Protocole>(new ProtocoleBuilder(), Files.readAllLines(new File("save/PROTOCOLE_j16m5").toPath()), null).construct();
		Carnet monCarnetConstruit = new Director<Carnet>(new CarnetBuilder(), Files.readAllLines(new File("save/CARNET_j16m5_j19m5").toPath()), monProtocoleConstruit).construct();
		Director<Carnet> enregistreurDeCarnet = new Director<Carnet>(new CarnetPrinter(), monCarnetConstruit, "test/CARNET_copie");
		enregistreurDeCarnet.print();
		Director<Protocole> enregistreurDeProtocole = new Director<Protocole>(new ProtocolePrinter(), monProtocoleConstruit, "test/PROTOCOLE_copie_j20m5");
		enregistreurDeProtocole.print();
	}

}
