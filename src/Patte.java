
public class Patte extends Organe {
	Came came;
	double longueurInterne, longueurSortante, hauteur, hauteurAuSol, angleMax, angleMin, hauteurMax, hauteurMin;
	
	public Patte(Came came, String nom){
		super(came.getMoteur(), nom);
		setCame(came);
	}
	public Patte(Came came, double longueurInterne, double longueurSortante,double hauteur, double hauteurAuSol, String nom){
		super(came.getMoteur(), nom);
		setHauteur(hauteur);
		setLongueurInterne(longueurInterne);
		setLongueurSortante(longueurSortante);
		setHauteurAuSol(hauteurAuSol);
		setCame(came);
		setGeometrie();
	}

	public Came getCame() {
		return came;
	}

	public void setCame(Came came) {
		this.came = came;
	}
	public double Vitesse() {
		double debattement = angleMax - angleMin;
		return Round(2.0*debattement*getCame().Vitesse()/60.0,2);
	}
	public String VitesseLabel() {
		return "Vitesse angulaire de  "+getNom()+"(deg/sec)";
	}
	public void setGeometrie(){
		 angleMax = Math.toDegrees(Math.asin(((getHauteur()+(getCame().getRayonMax()-getCame().getRayonMin()))/getLongueurInterne())));
		 angleMin = Math.toDegrees(Math.asin(((getHauteur()+(getCame().getRayonMin()))/getLongueurInterne())));

		 hauteurMax = Math.sin(Math.toRadians(angleMax))*getLongueurSortante() + getHauteurAuSol();
		 hauteurMin=  Math.sin(Math.toRadians(angleMin))*getLongueurSortante() + getHauteurAuSol();
	}
	public double Position() {
			return Round(hauteurMin + (hauteurMax-hauteurMin)*Math.abs(Math.sin(Math.toRadians(came.Position()))),2); 
	}
	public String PositionLabel() {
		return "Debatemment en cm de la "+getNom();
	}

	public double getHauteur() {
		return hauteur;
	}

	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}
	public double getLongueurInterne() {
		return longueurInterne;
	}
	public void setLongueurInterne(double longueurInterne) {
		this.longueurInterne = longueurInterne;
	}
	public double getLongueurSortante() {
		return longueurSortante;
	}
	public void setLongueurSortante(double longueurSortante) {
		this.longueurSortante = longueurSortante;
	}
	public double getHauteurAuSol() {
		return hauteurAuSol;
	}
	public void setHauteurAuSol(double hauteurAuSol) {
		this.hauteurAuSol = hauteurAuSol;
	}
	
}
