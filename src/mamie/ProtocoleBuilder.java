package mamie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class ProtocoleBuilder extends Builder<Protocole> {

	private Piqure piqure;
	private Periode periode1;
	private int base;
	private Periode periode2;

	/**
	 * autant de piqures que de couples (type,période)
	 */
	private ArrayList<Piqure> mesPiqures = new ArrayList<Piqure>();
	boolean justeType;

	/**
	 * on ajoute un nouveau type de piqure
	 * @param ligne
	 */
	private void buildPiqure(String ligne) {
		String[] res = ligne.split(" ");
		String med = "";
		if(res.length == 2)
			med = res[1];
		this.mesPiqures.add(new Piqure(res[0], med));
		this.justeType = true;
	}

	/**
	 * on doit copier le type de piqure de la derniere pour ajouter avec cette période
	 * on doit ajouter la piqure à la période
	 * on doit ajouter la période à la journée
	 * @param ligne
	 */
	private void buildPeriode(String ligne) {
		Piqure derniere = this.mesPiqures.get(this.mesPiqures.size()-1), piqurePeriode;
		if(this.justeType)
			piqurePeriode = derniere;
		else
			piqurePeriode = derniere.getShallowCopy();
		this.mesPiqures.add(piqurePeriode);
		this.justeType = false;
		this.periode1 = Periode.getPeriode(ligne);
		this.periode1.addMesPiqures(piqurePeriode);
		this.result.addPeriode(this.periode1);
	}

	private void buildPosologie(String ligne) {
		HashMap<Float,Integer> conf = new HashMap<Float, Integer>();
		String[] res = ligne.split(" ");
		float k = 0;
		if(res.length == 1) {
			this.base = Integer.parseInt(res[0]);
		}else {
			k = Float.parseFloat(res[0]);
			this.base = Integer.parseInt(res[1]);
		}
		conf.put(k, this.base);
		Piqure derniere = this.mesPiqures.get(this.mesPiqures.size()-1);
		derniere.setValeurGlycemie_unitesAAdministrer(conf);
	}

	private void buildAdaptation(String ligne) {
		String[] res = ligne.split(" ");
		float k = 0;
		int unites;
		if(res.length == 1) {
			unites = this.base + Integer.parseInt(res[0]);
		}else {
			k = Float.parseFloat(res[0]);
			unites = this.base + Integer.parseInt(res[1]);
		}
		Piqure derniere = this.mesPiqures.get(this.mesPiqures.size()-1);
		derniere.addValeurGlycemie_unitesAAdministrer(k, unites);
	}

	private void buildSurveillances(String ligne) {
		if(ligne.equals(""))
			this.periode2 = this.periode1;
		else
			this.periode2 = Periode.getPeriode(ligne);
	}

	private void buildModification(String ligne) {
		String[] res = ligne.split(" ");
		Piqure derniere = this.mesPiqures.get(this.mesPiqures.size()-1);
		if(res.length == 4) {
			this.result.addSurveillanceDePeriodePourModificationPiqure(new Surveillance(Float.parseFloat(res[0]), Integer.parseInt(res[1]), Integer.parseInt(res[2]), derniere.getDescription(), this.periode2, this.periode1, res[3].equals("oui")));
		}else {
			this.result.addSurveillanceDePeriodePourModificationPiqure(new Surveillance(Float.parseFloat(res[0]), Float.parseFloat(res[1]), Integer.parseInt(res[2]), Integer.parseInt(res[3]), derniere.getDescription(), this.periode2, this.periode1, res[4].equals("oui")));
		}
	}

	public void build(List<String> lignes, Protocole protocole) throws Exception {
		if(!lignes.get(0).equals("PROTOCOLE"))
			throw new Exception("on attend un fichier de protocole");
		ArrayList<Periode> maJournee = new ArrayList<Periode>();
		this.result = new Protocole(maJournee);
		for(int i=1 ; i<lignes.size() ; i++) {
			String ligne = lignes.get(i);
			if(ligne.startsWith("DEBUT ")) {
				String debut = ligne.split("DEBUT ")[1];
				this.parenthesage.push(debut);
				String suivante = lignes.get(++i);
				switch(debut) {
				case "PIQURE" :
					this.buildPiqure(suivante);
					break;
				case "PERIODE" :
					this.buildPeriode(suivante);
					break;
				case "POSOLOGIE" :
					this.buildPosologie(suivante);
					break;
				case "ADAPTATION" :
					this.buildAdaptation(suivante);
					break;
				case "SURVEILLANCE" :
					if(suivante.startsWith("DEBUT ")) {
						this.buildSurveillances("");
						i--;
					}else {
						this.buildSurveillances(suivante);
					}
					break;
				case "MODIFICATION" :
					this.buildModification(suivante);
					break;
				default :
					/*switch(this.parenthesage.pop()) {
					case "ADAPTATION" :
						this.buildAdaptation(ligne);
						i--;
						break;
					case "MODIFICATION" :
						this.buildModification(ligne);
						i--;
						break;
					default :
						System.out.println(this.parenthesage.pop());
						System.out.println(ligne);
						System.out.println(suivante);
						throw new Exception("mauvais motif");
					}*/
					throw new Exception("mauvais motif");
				}
			}else {
				if(ligne.startsWith("FIN ")) {
					if(!this.parenthesage.pop().equals(ligne.split("FIN ")[1]))
						throw new Exception("mauvais parenthesage");
				}else {
					switch(this.parenthesage.peek()) {
					case "ADAPTATION" :
						this.buildAdaptation(ligne);
						break;
					case "MODIFICATION" :
						this.buildModification(ligne);
						break;
					default :
						throw new Exception("mauvais motif");
					}
				}
			}
		}
	}

}
