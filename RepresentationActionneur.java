import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class RepresentationActionneur extends JFrame implements ActionListener{
	
	ArrayList<Actionneur> actionneurs = new ArrayList<Actionneur>();
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	int declenchement = 200;
	Timer chrono = new Timer(declenchement, this);	
	
	public RepresentationActionneur(ArrayList<Actionneur> actionneurs){
		super();
		setActionneur(actionneurs);
		this.setTitle("Paramètre actionneur");
		//Définit sa taille : 400 pixels de large et 400 pixels de haut
		this.setSize(500, 270);
		//Nous demandons maintenant à notre objet de se positionner au centre
		this.setLocation(605, 760);
		//Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Layout pour organiser proprement les informations
		this.setLayout(new GridLayout(getActionneurs().size(), 2));
		
		init();
		chrono.start();
		this.setVisible(true);
	}
	
	public void init() {
		for(Actionneur a : this.getActionneurs()) {
			JPanel panelVitesse = new JPanel();
			JLabel labelVitesse = new JLabel("Vitesse "+a.getNom());
			JLabel vitesse = new JLabel();
			labels.add(vitesse);
			panelVitesse.add(labelVitesse);
			panelVitesse.add(vitesse);
			panelVitesse.setBorder(BorderFactory.createLineBorder(Color.black));
			this.add(panelVitesse);
			
			JPanel panelPosition = new JPanel();
			JLabel labelPosition = new JLabel("Position "+a.getNom());
			JLabel position = new JLabel();
			labels.add(position);
			panelPosition.add(labelPosition);
			panelPosition.add(position);
			panelPosition.setBorder(BorderFactory.createLineBorder(Color.black));
			this.add(panelPosition);
		}
	}
	public void actionPerformed(ActionEvent ae) {
		int i =0;
		for(Actionneur a : actionneurs) {
			labels.get(i++).setText(String.valueOf(a.getVitesse()));
			labels.get(i++).setText(String.valueOf(a.getPositionAngulaire()));
		}
	}
	public ArrayList<Actionneur> getActionneurs() {
		return actionneurs;
	}

	public void setActionneur(ArrayList<Actionneur> actionneurs) {
		this.actionneurs = actionneurs;
	}
}
