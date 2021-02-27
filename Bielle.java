
public class Bielle extends Organe {
	protected double hauteurMax; //cm au dessus du toit
	protected double hauteurMin; // cm au dessus du toit
	
	public Bielle() {
		// TODO Auto-generated constructor stub
	}

	public Bielle(Moteur moteur) {
		super(moteur);
		// TODO Auto-generated constructor stub
	}

	public Bielle(Moteur moteur, String nom) {
		super(moteur, nom);
		// TODO Auto-generated constructor stub
	}

	public Bielle(Moteur moteur, double rapport) {
		super(moteur, rapport);
		// TODO Auto-generated constructor stub
	}

	public Bielle(Moteur moteur, double rapport, String nom) {
		super(moteur, rapport, nom);
		// TODO Auto-generated constructor stub
	}
	public Bielle(Moteur moteur, double rapport, double hauteurMax, double hauteurMin, String nom) {
		super(moteur, rapport, nom);
		setHauteurMax(hauteurMax);
		setHauteurMin(hauteurMin);
	}
	public double Position() {
		//on définit moteur à 180 comme étant position haute
		return Round(hauteurMin + (hauteurMax-hauteurMin)*Math.abs(Math.sin(Math.toRadians(moteur.getPositionAngulaire()%360))),2);
		//0 position basse
	}
	public double Vitesse() {//BPM
		//on définit moteur à 180 comme étant position haute
		return Round(2*moteur.getVitesse(),2);
		//0 position basse
	}

	public String VitesseLabel() {
		return "Battements par minutes "+getNom()+" (BPM) : ";
	}
	public String PositionLabel() {
		return "Hauteur "+getNom()+" (cm) : ";
	}
	public double getHauteurMax() {
		return hauteurMax;
	}

	public void setHauteurMax(double hauteurMax) {
		this.hauteurMax = hauteurMax;
	}

	public double getHauteurMin() {
		return hauteurMin;
	}

	public void setHauteurMin(double hauteurMin) {
		this.hauteurMin = hauteurMin;
	}
}
