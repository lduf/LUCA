import javax.swing.*;

public class Moteur extends Actionneur{
	protected double vitesse, positionAngulaire, facteurDeCharge; //vitesse et vitesse max données en tour par minute, position en degré
	protected int rpmAVide, reelRpmMax;
	protected Timer monChrono;
	protected double temps;
	protected int AngleR; //angle total à tourner 
	protected double angleRotater; //angle tourne depuis le début de la rotation

	public Moteur(String nom) {
		super(nom);
		positionAngulaire = 0;
		setVitesse(0);
	}
	public Moteur(String nom, int reelRpmMax){
		this(nom);
		positionAngulaire =0;
		setReelRpmMax(reelRpmMax);
	}
	
	public Moteur(String nom, int rpmAVide, double facteurDeCharge){
		this(nom);
		positionAngulaire =0;
		setRpmAVide(rpmAVide);
		setFacteurDeCharge(facteurDeCharge);
		setReelRpmMax((int)(getRpmAVide()*getFacteurDeCharge()));
	}

	public int getReelRpmMax() { 
		return reelRpmMax; 
	}

	public void setReelRpmMax(int reelRpmMax) {
		this.reelRpmMax = reelRpmMax;
	}

	public double getVitesse() {
		return Round(vitesse);
	}
	public double getVitesse(boolean ArduinoVitesse) { //vitesse donnée par arduino (entre -255 et 225)
		return vitesse*(255.0)/getReelRpmMax();
	}
	public void setVitesse(double vitesse) {
		System.out.println(vitesse);
		this.vitesse = getReelRpmMax()/255.0*(double)vitesse;
	}

	public double getPositionAngulaire() {
		return Round(this.positionAngulaire);
	}

	public void setPositionAngulaire(double positionAngulaire) {
		this.positionAngulaire = positionAngulaire;
	}
	public double Round(double d) {
		return(double) Math.round(d * 100)/100;
	}
	public double getFacteurDeCharge() {
		return facteurDeCharge;
	}
	public void setFacteurDeCharge(double facteurDeCharge) {
		this.facteurDeCharge = facteurDeCharge;
	}
	public int getRpmAVide() {
		return rpmAVide;
	}
	public void setRpmAVide(int rpmAVide) {
		this.rpmAVide = rpmAVide;
	}
	
	public int getAngleR() {
		return AngleR;
	}
	public void setAngleR(int d) {
		this.AngleR = d;
	}
	public void setAngleR(int d, boolean newRotation) {
		if(newRotation) {
			setAngleRotater(0);
		}
		this.AngleR = d;
	}
	public double getAngleRotater() {
		return angleRotater;
	}
	public void setAngleRotater(double angleRotater) {
		this.angleRotater = angleRotater;
	}
	public boolean rotation(double angleRajout) {
		setAngleRotater(getAngleRotater()+angleRajout);
		return (getAngleR()-getAngleRotater() <= 0);
	}
	
}
