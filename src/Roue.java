
public class Roue extends Organe {
	final double diametre; // la roue à un diamètre unique et inchangeable en cm
	protected double course;
	public Roue(Moteur moteur, double diametre, String nom){
		super(moteur, nom);
		this.diametre = diametre;
		setCourse();
	}
	public Roue(Moteur moteur, double diametre, double rapport, String nom){
		super(moteur, rapport, nom);
		this.diametre = diametre;
		setCourse();
	}
	public double getDiametre() {
		return diametre;
	}
	public double getCourse() {
		return course;
	}
	public void setCourse(double course) {
		this.course = course;
	}
	public void setCourse() {
		this.course = getDiametre()*Math.PI;
	}
}
