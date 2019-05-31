package builder;

import java.util.List;

import diabete.Carnet;
import diabete.Glycemie;
import diabete.Periode;
import diabete.Protocole;
import diabete.Repas;

/**
 * @author marie
 * ConcreteBuilder du BUILDER PATTERN
 * permet de construire un carnet à partir d'un fichier texte et d'un objet protocole
 */
public class CarnetBuilder extends Builder<Carnet> {
	
	private String periode = null;
	private Float glycemie = null;
	private String repas = null;

	/**
	 * @see builder.Builder#build(java.util.List, diabete.Protocole)
	 */
	public void build(List<String> lignes, Protocole protocole) throws Exception {
		if(!lignes.get(0).equals("CARNET"))
			throw new Exception("on attend un fichier de carnet");
		this.result = new Carnet(protocole);
		for(int i=1 ; i<lignes.size() ; i++) {
			String ligne = lignes.get(i);
			//System.out.println(ligne);
			if(ligne.startsWith("DEBUT ")) {
				if(this.parenthesage.size() != 0) {
					//System.out.println(this.parenthesage);
					throw new Exception("mauvais parenthesage");
				}else
					this.parenthesage.push(ligne.split("DEBUT ")[1]);
			}else {
				if(ligne.startsWith("FIN ")) {
					if(this.parenthesage.size() != 1)
						throw new Exception("mauvais parenthesage");
					else {
						if(!this.parenthesage.pop().equals(ligne.split("FIN ")[1]))
							throw new Exception("mauvais parenthesage");
						else {
							if(this.periode!=null && this.glycemie!=null) {
								if(this.repas != null) {
									this.result.ajoutGlycemie(Periode.getPeriode(this.periode), new Glycemie(this.glycemie), Repas.getRepas(this.repas));
									this.repas = null;
								}else {
									this.result.ajoutGlycemie(Periode.getPeriode(this.periode), new Glycemie(this.glycemie));
								}
								this.periode = null;
								this.glycemie = null;
							}else {
								throw new Exception("manque des informations sur période ou glycémie");
							}
						}
					}
				}else {
					if(ligne.startsWith("PERIODE ")) {
						this.periode = ligne.split("PERIODE ")[1];
					}else {
						if(ligne.startsWith("GLYCEMIE ")) {
							this.glycemie = Float.parseFloat(ligne.split("GLYCEMIE ")[1]);
						}else {
							if(ligne.startsWith("REPAS ")) {
								this.repas = ligne.split("REPAS ")[1];
							}else {
								throw new Exception("motif non reconnu");
							}
						}
					}
				}
			}
		}
	}

}
