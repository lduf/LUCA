public abstract class Actionneur{
	protected String nom;
	public Actionneur() {
		this.setNom("");
	}
	public Actionneur(String nom) {
		this.setNom(nom);
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public abstract double getVitesse();
	public abstract double getPositionAngulaire();
}