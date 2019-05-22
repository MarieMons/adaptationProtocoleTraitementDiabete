package builder;

import java.util.ArrayList;
import java.util.HashMap;

import diabete.Carnet;
import diabete.Glycemie;
import diabete.Periode;

public class CarnetPrinter extends Printer<Carnet> {

	/**
	 * @see builder.Printer#build(java.lang.Object)
	 */
	@Override
	public void build(Carnet object) throws Exception {
		this.result = "CARNET";
		HashMap<Periode,ArrayList<Glycemie>> cdg = object.getCarnetDeGlycemie();
		ArrayList<Periode> journee = new ArrayList<Periode>(cdg.keySet());
		journee.sort(null);
		for(int i=0 ; i<cdg.get(object.getDerniere()).size() ; i++) {
			for(Periode periode : journee) {
				Glycemie glyc = cdg.get(periode).get(i);
				if(glyc != null) {
					this.result += "\nDEBUT DEXTRO";
					this.result += "\nPERIODE " + periode.getDescription();
					this.result += "\nGLYCEMIE " + cdg.get(periode).get(i).getValeur();
					this.result += "\nFIN DEXTRO";
				}
			}
		}
	}
}
