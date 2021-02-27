import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class reqServeur implements Runnable{
	ServerSocket serverSocket;
	BufferedReader in ;
	PrintWriter out ;
	final String IP = "127.0.0.1";
	final int PORT = 5000;
	HashMap<String,Socket> robots = new HashMap<String, Socket>();
	String req;
	Socket Emetteur = null;
	PrintWriter EmetteurOut = null;
	public reqServeur() {
		
	}
	public void run() {
	System.out.println("test");
		try{
			serverSocket = new ServerSocket(PORT, 100, InetAddress.getByName(IP));//Création du serveur 
				while(1==1) {
					
					System.out.println("Server on :");
					System.out.println("**********");
					Socket clientSocket = serverSocket.accept();//En attente d'une entrée
					System.out.println("Wainting for entry ....");
					out=new PrintWriter(clientSocket.getOutputStream(),true);
					if(clientSocket == Emetteur) {
						System.out.println("Requete en provenance de l'emetteur :");
						out.println("ok");
					}
					
					in = new BufferedReader(
								new InputStreamReader(
										clientSocket.getInputStream()));
						
						req = in.readLine();
						System.out.println("Recieved "+req);
						
						
						
						
						
						
					if(req.matches("^NewEmetteur$")) {//Nouvel Emetteur
						System.out.println("• Parametrisation de l'Emetteur");
						Emetteur = clientSocket;
						EmetteurOut = new PrintWriter(clientSocket.getOutputStream(),true);
						EmetteurOut.println("Emetteur set");
					}
						
					Matcher matcher = Pattern.compile("^NewRobot:(.+)").matcher(req);//Recherche de la commande d'un nouveau robot
					while(matcher.find()) {
						if(EmetteurOut != null) {
							System.out.println("• Sortie Emetteur non nulle, ajout à la liste de robot");
							String rName = String.valueOf(matcher.group(1));
							System.out.println(rName);
							robots.put(rName, clientSocket);
							System.out.println("• Envoie à l'emetteur ");
							EmetteurOut.println("NewRobot:"+rName);
						}
						else{
							out.print("Erreur : L'emetteur doit être lancé");
						}
					}
					
					if(req.matches("^([\\S]*):(\\w+):(\\d*)$")) {//Recherche de la commande d'un nouveau robot
						System.out.println("Envoie d'une confirmation à l'emetteur");
						out.println("Req received"); // Confirmation à l'Emetteur
								String[] parts = req.split(":");
								String robotName = parts[0];
								String action = parts[1];
								int valeur;
								if(parts.length >2) {
									valeur = Integer.valueOf(parts[2]);
								}else {
									valeur = 0;
								}
								System.out.println(robotName+" "+action+" "+valeur);
								if(robotName.matches("\\*")) {//Si le nom du robot est * alors j'applique à tous les robots
									robots.forEach((robot, client)->{
										System.out.println("Envoie de la requete au robot : "+robot);
										PrintWriter o;
										try {
											o = new PrintWriter(client.getOutputStream(),true);
											System.out.println("Send : \n Action : "+action+"\n Valeur : "+valeur);
											o.println(action+" : "+valeur);
										} catch (IOException e) {
											e.printStackTrace();
										}
									});
								}
								if(robots.containsKey(robotName)) {// Si c'est le nom d'un robot, j'envoie la requete au robot (1 seul)
									System.out.println("Envoie de la requete au robot : "+robotName);
									clientSocket = robots.get(robotName); 
									out=new PrintWriter(clientSocket.getOutputStream(),true);
									System.out.println("Send : \n Action : "+action+"\n Valeur : "+valeur);
									out.println(action+" : "+valeur);
								}
						}
					
				}
				
			
		} catch (Exception e) {
			
			System.err.println(e);
		}
	}
}
