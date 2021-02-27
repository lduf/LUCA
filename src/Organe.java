
public abstract class Organe {
	protected double k = 1.0; //facteur de démultiplication lié à la possible chaine d'engrenage
	protected Moteur moteur; //Moteur associée à un organe
	protected String nom;
	
	public Organe() {
	}
	public Organe(Moteur moteur) {
		setMoteur(moteur);
	}
	public Organe(Moteur moteur, String nom){ // constructeur pour prendre en compte la présence d'une poulie ou d'engrenages
		this(moteur); // rapport, à multiplier par l'angele du moteu rpour avoir l'angle de l'organe
		setNom(nom);
	}
	public Organe(Moteur moteur, double rapport){ // constructeur pour prendre en compte la présence d'une poulie ou d'engrenages
		this(moteur);
		setK(rapport); // rapport, à multiplier par l'angele du moteu rpour avoir l'angle de l'organe
	}
	public Organe(Moteur moteur, double rapport, String nom) {
		this(moteur, rapport);
		setNom(nom);
	}
	
	
	public String VitesseLabel() {
		return "Vitesse "+getNom()+" (RPM) : ";
	}
	public String PositionLabel() {
		return "Position "+getNom()+" (°) : ";
	}
	
	public double Round(double d, int decimal) {
		  return(double) Math.round(d * Math.pow(10, decimal))/Math.pow(10, decimal);
		}  
	public double getK() {
		return k;
	}
	public void setK(double k) {
		this.k = k;
	}
	public Moteur getMoteur() {
		return moteur;
	}
	public void setMoteur(Moteur moteur) {
		this.moteur = moteur;
	}
	public double Vitesse() {
		return Round(moteur.getVitesse()*this.getK(),2);
	}
	public double Position() {
		return Round(moteur.getPositionAngulaire()*this.getK(),2);
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNom() {
		return this.nom;
	}
}
