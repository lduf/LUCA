import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;



/*
Nous allons créer une carte de 5m par 5m ou poura circuler le robot en question
*/


public class RepresentationSol extends JFrame{
        //paramètre pour representer le robot
       BufferedImage imageRobot;
       BufferedImage fond;
       APoint posRect;
       private double angle; //en radian, angle du robot
       double largeur; // largeur du robot en cm
       double longueur; // longueur du robot en cm
       double L; //largeur du robot en pixel
       double l; // longueur du robot en pixel
       int tailleCarte;


    public RepresentationSol(double largeur, double longueur){ //définies en cm
        tailleCarte = 400; //en cm
        this.setSize(700, 700); // taille en pixel, ajustable, meme si la carte mesure toujours 5m
        angle = 0;
        this.largeur = largeur;
        this.longueur = longueur;
        // pour avoir la longueur et la largeur du robot à l'échelle de la carte sur l'ordinateur
        l = ((double)(largeur*this.getWidth())/tailleCarte);
        L = ((double)(longueur*this.getHeight())/tailleCarte);
         try{
                imageRobot = ImageIO.read(new File("robot.png"));
                fond = ImageIO.read(new File("fond.png"));
             }catch(IOException e){e.printStackTrace();}
                catch(Exception e){e.printStackTrace();}

        //Définit un titre pour notre fenêtre
    	this.setTitle("Carte au sol");
   		//Nous demandons maintenant à notre objet de se positionner au centre
   		this.setLocation(510, 50);
   		//Termine le processus lorsqu'on clique sur la croix rouge
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //posRect = new APoint(this.getWidth()-(int)((double)(largeur*this.getWidth())/tailleCarte),this.getHeight()-(int)((double)(longueur*this.getHeight())/tailleCarte));
    	posRect = new APoint(0,0);

    	this.setVisible(true);
    }

        public void paint(Graphics g){

            Graphics2D g2 = (Graphics2D) g;

            //taille et position du robot
            l = (double)(largeur*this.getWidth())/tailleCarte;
            L = (double)(longueur*this.getHeight())/tailleCarte;

            AffineTransform transform = new AffineTransform();
            //deplace l'image à la position souhaitée
            transform.translate(posRect.x-(int)(l/2), posRect.y-(int)(L/2));
            //oriente l'image avec l'angle souhaité
            transform.rotate(-angle,(int)(l/2),(int)(L/2));
            //mets l'image à la bonne taille
            transform.scale((l/(double)imageRobot.getWidth()),(L/(double)imageRobot.getHeight()));

//TODO : améliorer le rendu de l'affichage pour ne pas avoir à réécrire une image par dessus à chaque fois


            //redessine le fond pour effacer l'image présente
            g.drawImage(fond,0,0, this.getWidth(),this.getHeight(),this);
            //redessine le robot
            g2.drawImage(imageRobot, transform, this);


        }
    public void setAngle(double a){
        this.angle = a*Math.PI/180;
    }
    public double getAngle(){
    return angle;
    }

    public boolean setPosRect(double x, double y) {
    	
    	if ((Math.abs(x) >= ((this.tailleCarte)-(longueur/2)-5)) || (Math.abs(y) >= ((this.tailleCarte)-(longueur/2))-5 || (Math.abs(x) <= 12) || (Math.abs(y) <= 24) ) ) {
    		posRect.x = posRect.x;
    		posRect.y = posRect.y;
    		return false;
    	} else {
    	posRect.x = (double)(x*this.getWidth())/tailleCarte;
        posRect.y = (double)(y*this.getHeight())/tailleCarte;
        return true;
    	}
    	
    }
}