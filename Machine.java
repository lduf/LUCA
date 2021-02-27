import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
/*
pour le moment, nous considèrerons que le robot peut soit avancer, donc sa position evolue mais sont angle reste constant
soit tourner, sa position reste constate et son angle varie.

on essayera de representer le robot à l'aide de son centre qui aura les coordonées pos et de l'angle qui aura la valeur de l'angle.
selon les valeurs différentes du moteur, ces paramètres seront influencés.
pour la partie graphique, le robot sera donc 4 traits reliés entre eux permettant ainsi de représenter ces deux paramètres.
 */


public class Machine implements ActionListener{
	int temps = 100; //echantillonage
	Timer monChrono = new Timer(temps,this);
//	LinkedList<double[]> instructions = new LinkedList<double[]>(); // liste des intruction à effectuer contient deux valeurs, la vitesse du moteur gauche en premier et la vitesse du moteur droit en deuxième
//	boolean operer = false; // définit si nous pouvons acceder aux instructions ou non
	APoint pos; // position du robot
	double angle;// l'angle represente l'écart angulaire du moteur par rapport à la vertivale, en degré.
	boolean RotationEnCours;
	//fenetres graphiques
	//RepresentationMoteur representationMoteur;
	RepresentationSol representationSol;
	RepresentationRobot representationRobot;
	

	
	
	//Déclaration des moteurs et des organes
	Moteur mG = new Moteur("moteur gauche", 110, 0.70);//RPM max à 110, tourne à 70% de la vitesse max
	Moteur mD = new Moteur("moteur droit",110, 0.70);//RPM max à 110, tourne à 70% de la vitesse max
	Roue rG = new Roue(mG, 6.5, 0.9, "roue gauche"); //moteur associé, diamiètre en cm, faction de démultipilication
	Roue rD = new Roue(mD, 6.5, 0.9, "roue droite");//moteur associé, diamiètre en cm, faction de démultipilication
	ArbreACame abc = new ArbreACame(mD, 1.5, "arbre à came");//Moteur moteur, double rapport,  String nom
	Bielle bielle = new Bielle(mG, 1, 5.292, 3.292, "bielle manivelle");//Moteur moteur, double rapport, double hauteurMax, double hauteurMin, String nom)
	Toit toit = new Toit(mG, 1,16.2 ,bielle, 0.9,"toit");//Moteur moteur, double rapport, double largeur, Bielle bielle, double correctionHauteurBielle, String nom)

	ArrayList<Patte> pattes = new ArrayList<Patte>();
	ArrayList<Came> cames = new ArrayList<Came>(); //5 est le nombre de Came
	ArrayList<Actionneur> actionneurs = new ArrayList<Actionneur>();
	ArrayList<Organe> organes = new ArrayList<Organe>();
	
	//propre au sublissime L U C A
		int nbDeCame = 5;//Il y a 5 cames dans notre robot
		double rayonMinDeLaCame =  0.265;
		double rayonMaxDeLaCame =  1.265;
		double longueurInternePatte = 9;
		double longueurSortantePatte = 13.13;
		double hauteurPatte = 3.18;
		double HauteurAuSol = 8.43;
		double longueur = 16.8; // longueur du robot
		double largeur = 21.7; // largeur du robot
		double rapport = 1.5;// multiplcation par la chaine d'engrenage
		
	Requete r;
	Thread t;
	int i = 0;

	public Machine() {
		actionneurs.add(mG);
		actionneurs.add(mD);
		
		organes.add(rG);
		organes.add(rD);
		organes.add(abc);
		organes.add(bielle);
		organes.add(toit);
		
		for(int i =0; i<nbDeCame; i++) {
			Came c = new Came(abc, i*360.0/(double)(nbDeCame),rayonMinDeLaCame, rayonMaxDeLaCame, "came "+(i+1));//Arbre a came associé, angle,rayon min, rayon max, nom
			Patte p = new Patte(c, longueurInternePatte, longueurSortantePatte, hauteurPatte, HauteurAuSol, "patte "+(i+1));//Came assocociée, longueurDeLaPatte, nom
			cames.add(c);
			pattes.add(p);
			organes.add(c);
			organes.add(p);
		}
		
		System.out.println("MACHINE : new Requete");
		r = new Requete("LUCA");
		System.out.println("MACHINE : r.setServer");
		r.setServer();
		t = new Thread(r);
		t.start();
		
		pos = new APoint(100,100); // placement initial du robot à 1m (100cm) du bords haut gauche
		
		
		
		//instanciation des afficheurs graphiques
		RepresentationActionneur  rpzAct =new RepresentationActionneur(actionneurs);
		RepresentationOrganes  rpzOrg =new RepresentationOrganes(organes);
		representationSol = new RepresentationSol(largeur,longueur);
		representationRobot = new RepresentationRobot(mG, mD, rG, rD);
		monChrono.start();
		
		pos.x = representationSol.tailleCarte/2;
		pos.y = representationSol.tailleCarte/2;
	}
/*
Liste des commandes à pouvoir etre effectuées
les methodes des commandes transforment les commandes en une liste d'opération ajoutées à la liste d'instruction
les angles sont donnés en degré et les distances en cm
 */

	//1 fait avancer le robot en ligne droite
	public void avancer(Integer vitesseMoteur){
		mD.setVitesse((Math.abs(vitesseMoteur) > 255)?255:Math.abs(vitesseMoteur));
		mG.setVitesse((Math.abs(vitesseMoteur) > 255)?255:Math.abs(vitesseMoteur));
		RotationEnCours=false;
		
		
	}
	//2 fait reculer le robot en ligne droite
	public void reculer(Integer vitesseMoteur){
		mD.setVitesse((Math.abs(vitesseMoteur) > 255)?-255:-Math.abs(vitesseMoteur));
		mG.setVitesse((Math.abs(vitesseMoteur) > 255)?-255:-Math.abs(vitesseMoteur));
		RotationEnCours=false;
	}
	//3 fait pivoter le robot de l'angle choisi à droite
	
	public void droite(Integer angle){  
	 /*   double distance  = (double)(angle*Math.PI/180*largeur); 
	  * // convertir l'angle en longueur d'arc (distance)   //idéee   
	  * /*    * En 1 tour de moteur, combien de tour de roue ? -> 1 car moteur directement sur la roue ?   
	  *  * Distance à parcourir -> en arc de cercle = distance   
	  *   * combien de tour de moteur ? => distance/diametère des roues   
	  *    * On fixe la vitesse du moteur donc ->  on peut récupérer un temps de parcours pour l'arc et donc actualiser l'angle du moteur    
	  *    *     -> temps (ms) => nombre de tour / RPM (m.getVitesse) *60*1000    
	  *    * => on appelle un Timer qui tous les n ms actualise la postion qui a bougé    
	  *    */   
		mG.setAngleR(angle, true);//true pour définir une nouvelle rotation
		mD.setAngleR(angle, true);
		mG.setVitesse((mG.getVitesse() != 0)?mG.getVitesse(true):mG.getVitesse()+100);   
		mD.setVitesse((mD.getVitesse() != 0)?-mD.getVitesse(true):mD.getVitesse()-100);  
		RotationEnCours=true;
	}
	
	/*
	//4 fait pivoter le robot de l'angle choisi à gauche
	 * */
	 
	public void gauche(Integer angle){  
	 /*   double distance  = (double)(angle*Math.PI/180*largeur); 
	  * // convertir l'angle en longueur d'arc (distance)   //idéee   
	  * /*    * En 1 tour de moteur, combien de tour de roue ? -> 1 car moteur directement sur la roue ?   
	  *  * Distance à parcourir -> en arc de cercle = distance   
	  *   * combien de tour de moteur ? => distance/diametère des roues   
	  *    * On fixe la vitesse du moteur donc ->  on peut récupérer un temps de parcours pour l'arc et donc actualiser l'angle du moteur    
	  *    *     -> temps (ms) => nombre de tour / RPM (m.getVitesse) *60*1000    
	  *    * => on appelle un Timer qui tous les n ms actualise la postion qui a bougé    
	  *    */   
		mG.setAngleR(angle, true);//true pour définir une nouvelle rotation
		mD.setAngleR(angle, true);
		mG.setVitesse((mG.getVitesse() != 0)?-mG.getVitesse(true):mG.getVitesse()-100);   
		mD.setVitesse((mD.getVitesse() != 0)?mD.getVitesse(true):mD.getVitesse()+100);  
		RotationEnCours=true;
	}
	
	//5 stoppe la liste d'instruction
	public void pause(Integer causeItMustBeThere){
		mD.setVitesse(0);
		mG.setVitesse(0);
	}

	public void stop(Integer causeItMustBeThere){
		System.exit(0);
	}
	//actualise l'angle et la position du robot visible sur la carte de l'afficheur graphique
	public void actualisePosition(){
		//distance parcourue par le robot pour une actualisation
		double distanceG = rG.Vitesse()*temps/(60.0*1000.0)*rG.getCourse();
		double distanceD = rD.Vitesse()*temps/(60.0*1000.0)*rD.getCourse();
		
		pos.x += (distanceD+distanceG)/2*Math.sin(angle*Math.PI/180);
		pos.y += (distanceD+distanceG)/2*Math.cos(angle*Math.PI/180);
			//si les vitesses du robot sont diférentes, il va pivoter sur lui meme
		angle += (distanceD-distanceG)*180/(largeur*Math.PI);
		
		if(RotationEnCours) {
			if(mD.rotation(Math.abs(distanceD-distanceG)*180/(largeur*Math.PI))  || mG.rotation(Math.abs(distanceD-distanceG)*180/(largeur*Math.PI))) {
				avancer(Math.max((int) Math.abs(mD.getVitesse(true)), (int) Math.abs(mG.getVitesse(true))));
			}
		}
		
		
			mD.setPositionAngulaire(mD.getPositionAngulaire() + mD.getVitesse() * 360.0/60.0 * temps/1000.0); 
			mG.setPositionAngulaire(mG.getPositionAngulaire() + mG.getVitesse() * 360.0/60.0 * temps/1000.0); 
			
			
		if(!representationSol.setPosRect(pos.x, pos.y)) {
			rG.setK(0.02);
			rD.setK(0.02);
			abc.setK(0.02);
			bielle.setK(0.02);
			pos.x -= (distanceD+distanceG)/2*Math.sin(angle*Math.PI/180);
			pos.y -= (distanceD+distanceG)/2*Math.cos(angle*Math.PI/180);
		}else {
			rG.setK(0.9);
			rD.setK(0.9);
			abc.setK(1.5);
			bielle.setK(1);
		}
		representationSol.setAngle(angle);
		representationSol.repaint();
		
	}
	
	//methodes qui retournent la vitesse au sol du robot en fonction de ses caractéristiques, propre à chaque robot
	
	//Timer pour aller récupérer les infos de l'émition et mettre à jour le tout
	public void actionPerformed(ActionEvent e){
		
	//	r.run();
		String req = r.getReq();
		java.lang.reflect.Method method;
		try {
			if(req.matches("^(\\w+):(\\d*)$")) {//Décodage de la commande reçue
				r.setReq("");
				System.out.println("Commande match !");
				String[] parts = req.split(":");
				String methodName = parts[0].toLowerCase();
				Integer value;
				if(parts.length >1) {
					value =Integer.valueOf(parts[1]);
				}else {
					value = 0;
				}
				System.out.println("Appel de la méthode "+methodName+" avec la valeur "+value);
				method = this.getClass().getMethod(methodName, (value).getClass());//methode en a appeler (methodName)
				method.invoke(this, (int)value);//appel de la methode
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		actualisePosition();
		
		
	}
	
	 public static void main(String args[]){
		 
	        Machine machine = new Machine();
	        while(true);
	    }
	   
}
