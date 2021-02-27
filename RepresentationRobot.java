import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class RepresentationRobot extends JFrame implements ActionListener{ //vitesse en ms-1/angle/distance totale parcourue
	
	protected JPanel pvitesse, pangle, pdistance;
	protected JLabel lvitesse, langle,ldistance;
	protected JLabel vitesse, angle, distance;
	protected double distanceTotale, angleT;
	double largeur = 21.7; 
	protected Roue rG, rD;
	protected Moteur mG, mD;
	protected int temps = 200;
	protected Timer timer = new Timer(temps, this);
	
	public RepresentationRobot(Moteur mG, Moteur mD, Roue rG, Roue rD){
		super("Etat du robot");
		//Définit sa taille : 400 pixels de large et 400 pixels de haut
		this.setSize(400, 400);
		//Nous demandons maintenant à notre objet de se positionner au centre
		this.setLocation(100, 370);
		//Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Layout pour organiser proprement les informations
		this.setLayout(new GridLayout(3,1));
		this.mG = mG;
		this.mD = mD;
		this.rG = rG;
		this.rD = rD;
		init();
		timer.start();
		this.setVisible(true);
	}
	
	public void init() {
			pvitesse = new JPanel();
			lvitesse = new JLabel("Vitesse générale du robot en cm/s : ");
			vitesse = new JLabel();
			pvitesse.add(lvitesse);
			pvitesse.add(vitesse);
			this.add(pvitesse);
			
			pangle = new JPanel();
			langle = new JLabel("Angle du robot : ");
			angle = new JLabel();
			pangle.add(langle);
			pangle.add(angle);
			this.add(pangle);
			
			pdistance = new JPanel();
			ldistance = new JLabel("Distance parcourue par le robot (m) : ");
			distance = new JLabel();
			pdistance.add(ldistance);
			pdistance.add(distance);
			this.add(pdistance);
	}
	public void actionPerformed(ActionEvent ae) {
		double distanceG = rG.Vitesse()*temps/(60.0*1000.0)*rG.getCourse();
		double distanceD = rD.Vitesse()*temps/(60.0*1000.0)*rD.getCourse();
		angleT += (distanceD-distanceG)*180.0/(largeur*Math.PI);
		
		setVitesse(Round(((rG.Vitesse()+rD.Vitesse())/2.0)/60.0*(rG.getCourse()+rD.getCourse())/2.0,2));
		setDistance(Round((distanceG+distanceD)/200.0,2));
		setAngle(Round(angleT%360,2));
	}
	public double Round(double d, int decimal) {
		  return(double) Math.round(d * Math.pow(10, decimal))/Math.pow(10, decimal);
		}  

	public void setAngle(double angle) {
		this.angle.setText(String.valueOf(angle)); 
	}
	public void setDistance(double distance) {
		distanceTotale+=Math.abs(distance);
		this.distance.setText(String.valueOf(Round(distanceTotale,4))); 
	}
	public void setVitesse(double vitesse) {
		this.vitesse.setText(String.valueOf(vitesse)); 
	}
	
}
