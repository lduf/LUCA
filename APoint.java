
public class APoint {

    // Attributs

    public double x;
    public double y;

    // Constructeur

    public APoint(double ax, double ay){
        x = ax;
        y = ay;
    }

    // Méthode permettant de calcules la distance entre deux points

    public double distance( APoint otherPoint ) {
        double dx = x - otherPoint.x;
        double dy = y - otherPoint.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    //Méthode toString

    public String toString() {
        String res ="";
        res="[x=" + x + ",y=" + y + "]";
        return res;
    }

    public void setCord(double x, double y){ // définir les coordonées d'un point
        this.x = x;
        this.y = y;
    }
}
