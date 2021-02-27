
public class Toit extends Organe {
	Bielle bielle;
	protected double largeur, correctionHauteurBielle;
	public Toit() {
		// TODO Auto-generated constructor stub
	}

	public Toit(Moteur moteur) {
		super(moteur);
		// TODO Auto-generated constructor stub
	}

	public Toit(Moteur moteur, String nom) {
		super(moteur, nom);
		// TODO Auto-generated constructor stub
	}

	public Toit(Moteur moteur, double rapport) {
		super(moteur, rapport);
		// TODO Auto-generated constructor stub
	}

	public Toit(Moteur moteur, double rapport, double largeur, Bielle bielle, double correctionHauteurBielle, String nom) {
		super(moteur, rapport, nom);
		setLargeur(largeur);
		setBielle(bielle);
		setCorrectionHauteurBielle(correctionHauteurBielle);
	}
	
	public double Position() {
		double bielleCorrected = getBielle().Position()-getCorrectionHauteurBielle();
		return Round(Math.toDegrees(Math.atan(bielleCorrected/getLargeur())),2);
	}
	public double Vitesse() {
		double angleMax =  Math.toDegrees(Math.atan((getBielle().getHauteurMax()-getCorrectionHauteurBielle())/getLargeur()));
		double angleMin =  Math.toDegrees(Math.atan((getBielle().getHauteurMin()-getCorrectionHauteurBielle())/getLargeur()));
		double debattement = angleMax - angleMin;
		return Round(2.0*debattement*getBielle().Vitesse()/60.0,2);
	}
	public String VitesseLabel() {
		return "Vitesse angulaire "+getNom()+" (deg/s) : ";
	}
	public String PositionLabel() {
		return "Angle entre le sol et le "+getNom()+" (°) : ";
	}
	public Bielle getBielle() {
		return bielle;
	}

	public void setBielle(Bielle bielle) {
		this.bielle = bielle;
	}

	public double getLargeur() {
		return largeur;
	}

	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	public double getCorrectionHauteurBielle() {
		return correctionHauteurBielle;
	}

	public void setCorrectionHauteurBielle(double correctionHauteurBielle) {
		this.correctionHauteurBielle = correctionHauteurBielle;
	}

}
