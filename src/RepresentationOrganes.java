import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RepresentationOrganes extends JFrame implements ActionListener{
	ArrayList<Organe> organes = new ArrayList<Organe>();
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	HashMap<Class, Color> colors = new HashMap<Class, Color>();
	int declenchement = 200;
	Timer chrono = new Timer(declenchement, this);	
	
	public RepresentationOrganes(ArrayList<Organe> organes){
		super();
		setOrganes(organes);
		this.setTitle("Parametres organes");
		//Définit sa taille : 700 pixels de large et 800 pixels de haut
		this.setSize(700, 800);
		//Nous demandons maintenant à notre objet de se positionner au centre
		this.setLocation(1220, 50);
		//Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Layout pour organiser proprement les informations
		this.setLayout(new GridLayout(getOrganes().size(), 2));
		
		init();
		chrono.start();
		this.setVisible(true);
		this.repaint();
		
	}
	
	public void init() {
		for(Organe a : this.getOrganes()) {
			Color color;
			if(colors.containsKey(a.getClass())) {
				 color = colors.get(a.getClass());
			}else {
				Random rand = new Random();
				Color randomColor;
				do {
					 randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), (float) 0.3);
				}while(colors.containsValue(randomColor));
				colors.put(a.getClass(), randomColor);
				color = randomColor;
			}
			
			JPanel panelVitesse = new JPanel(){
			    protected void paintComponent(Graphics g)
			    {
			        g.setColor( getBackground() );
			        g.fillRect(0, 0, getWidth(), getHeight());
			        super.paintComponent(g);
			    }
			};
			JLabel labelVitesse = new JLabel(a.VitesseLabel());
			labelVitesse.setBackground(color);
			JLabel vitesse = new JLabel();
			vitesse.setBackground(color);
			labels.add(vitesse);
			panelVitesse.add(labelVitesse);
			panelVitesse.add(vitesse);
			panelVitesse.setBorder(BorderFactory.createLineBorder(Color.black));
			panelVitesse.setBackground(color);
			panelVitesse.setOpaque(false);
			this.add(panelVitesse);
			
			JPanel panelPosition = new JPanel(){
			    protected void paintComponent(Graphics g)
			    {
			        g.setColor( getBackground() );
			        g.fillRect(0, 0, getWidth(), getHeight());
			        super.paintComponent(g);
			    }
			};
			JLabel labelPosition = new JLabel(a.PositionLabel());
			JLabel position = new JLabel();
			labels.add(position);
			panelPosition.add(labelPosition);
			panelPosition.add(position);
			panelPosition.setBackground(color);
			panelPosition.setBorder(BorderFactory.createLineBorder(Color.black));
			panelPosition.setOpaque(false);
			this.add(panelPosition);
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		int i =0;
		for(Organe a : organes) {
			labels.get(i++).setText(String.valueOf(a.Vitesse()));
			labels.get(i++).setText(String.valueOf(a.Position()));
		}
	}
	public ArrayList<Organe> getOrganes() {
		return organes;
	}

	public void setOrganes(ArrayList<Organe> organes) {
		this.organes = organes;
	}
	
}
