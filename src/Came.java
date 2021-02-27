public class Came extends Organe{
	protected double angle;
	protected ArbreACame arbreACame;
	protected double rayonMin, rayonMax;
	
	public Came(double angle) {
		this.angle = angle;
	}
	public Came(ArbreACame abc,double angle) {
		super(abc.getMoteur());
		setArbreACame(abc);
		this.angle = angle;
	}
	public Came(ArbreACame abc,double angle,double rayonMin, double rayonMax, String nom) {
		super(abc.getMoteur(), nom);
		setArbreACame(abc);
		setAngle(angle);
		setRayonMin(rayonMin);
		setRayonMax(rayonMax);
	}
	public double Position() {
		return Round((getAngle()+getArbreACame().Position())%360,2);
	}
	public double Vitesse() {
		return Round(getArbreACame().Vitesse(),2);
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public ArbreACame getArbreACame() {
		return arbreACame;
	}
	public void setArbreACame(ArbreACame arbreACame) {
		this.arbreACame = arbreACame;
	}
	public double getRayonMin() {
		return rayonMin;
	}
	public void setRayonMin(double rayonMin) {
		this.rayonMin = rayonMin;
	}
	public double getRayonMax() {
		return rayonMax;
	}
	public void setRayonMax(double rayonMax) {
		this.rayonMax = rayonMax;
	}
}
