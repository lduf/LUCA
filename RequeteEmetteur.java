import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class RequeteEmetteur extends JFrame implements Runnable{
	JPanel lower;
	JPanel upper;
	JTextField cmd;
	JButton btn;
	JTextArea histo;
	static Socket socket;
	static PrintWriter out ;
	static BufferedReader in ;
	final String IP = "127.0.0.1";
	final int PORT = 5000;
	protected JComboBox<String> jrobots, jcommandes;
	protected ArrayList<String> robots = new ArrayList<String>();
	protected ArrayList<String> cmdList = new ArrayList<String>();
	protected String[] commandes = {"Avancer", "Reculer", "Droite", "Gauche", "Pause", "STOP"};
	
	public RequeteEmetteur() {
		super("Boite de commande des robots");
		this.setSize(400,300);
		this.setLocation(100,50);
		setServer();
		init();
		paint();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		do {
			try{
				in = new BufferedReader(
						new InputStreamReader(
								socket.getInputStream()
								)
						);//récup serveur
				String message = in.readLine() ;
				System.out.println(message);
				Matcher matcher = Pattern.compile("^NewRobot:(.+)").matcher(message);//Recherche de la commande d'un nouveau robot
				while(matcher.find()) {
						String rName = String.valueOf(matcher.group(1));
						robots.add(rName);
						paint();
				}
				
			} catch (Exception e) {
				System.err.println(e);
				System.exit(1);
			}
		}while(true);
	}
	public void run() {
		RequeteEmetteur re = new RequeteEmetteur();
	}
	public void init() {
		upper = new JPanel();
		lower = new JPanel();
		this.setLayout(new GridLayout(2,0));
		
		robots.add("*"); 
		
		jrobots = new JComboBox<String>();
		jrobots.setModel(new DefaultComboBoxModel<String>(robots.toArray(new String[0])));
		jcommandes = new JComboBox<String>(commandes);
		
		cmd = new JTextField("");
		histo = new JTextArea("");
		
		btn = new JButton("Envoyer !");
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String preCmd = cmd.getText();
				String rgx1 = "^(\\d+)$"; // Pour commander les actionneurs indépendamment
				if(preCmd.matches(rgx1) || String.valueOf(jcommandes.getSelectedItem()).matches("^STOP$")|| String.valueOf(jcommandes.getSelectedItem()).matches("^Pause$")){ //JOptionPane.showMessageDialog(null, "Requete envoyée au Serveur");
					//cmdList.add(String.valueOf("/"+jrobots.getSelectedItem())+" "+String.valueOf(jcommandes.getSelectedItem())+" : "+cmd.getText());
					preCmd=String.valueOf(jrobots.getSelectedItem())+":"+String.valueOf(jcommandes.getSelectedItem())+":"+preCmd;
					cmdList.add(preCmd);
					send(preCmd);
					cmd.setText("");
				}
				else{
					JOptionPane.showMessageDialog(null, "Erreur ! Nous attendions un Int !");
				}
				paint();
			}
		});
	}
	public void paint() {
		upper.setLayout(new GridLayout(2,2));
		lower.setLayout(new CardLayout());
		
		jrobots.setModel(new DefaultComboBoxModel<String>(robots.toArray(new String[0])));
		Collections.reverse(cmdList);
		histo.setText(getList(cmdList));
		Collections.reverse(cmdList);
		upper.add(jrobots);
		upper.add(jcommandes);
		upper.add(cmd);
		upper.add(btn);
		lower.add(histo);
		this.add(upper);
		this.add(lower);
		
	}
	public void send(String command) {
		// TODO Auto-generated method stub
		
				try{
					socket = new Socket(IP, PORT);
					
					out = new PrintWriter(socket.getOutputStream(), true);
						
						
					
					out.println(command); //Envoie de la commande !
					System.out.println("Commande envoyée au serveur !");
					System.out.println("Attente d'une réponse du serveur");
					in = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()
									)
							);//récup serveur
					String message = in.readLine() ;
					System.out.println(message);
					out.close();
					
					
				} catch (Exception e) {
				System.err.println(e);
				System.exit(1);
				}
		
	}
	public void setServer() {
		try {
			socket = new Socket(IP, PORT);
			
			out = new PrintWriter(socket.getOutputStream(), true);
			
			out.println("NewEmetteur"); //Envoie de la commande !
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()
							)
					);//récup serveur
			String message = in.readLine() ;
			System.out.println(message);
			
		}
		catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
	
	public String getList(ArrayList<String> cl) {
		String li = new String();
		for(int i=0; i<cl.size(); i++) {
			li += (String)(cl.get(i)+"\n");
		}
		return li;
	}
	/*
	public static void main(String[] args) {
		RequeteEmetteur re = new RequeteEmetteur();
	}*/
}
