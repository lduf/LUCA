import java.io.*;
import java.net.*;
import java.util.*;
public class Requete extends Recepteur implements Runnable{
	static Socket socket;
	static PrintWriter out ;
	static BufferedReader in ;
	final String IP = "127.0.0.1";
	final int PORT = 5000;
	protected String RobotName;
	String req;//Queue
	
	public Requete() {
		super();
	}
	public Requete(String RobotName) {
		super();
		setRobotName(RobotName);
	}
	public boolean setServer() {
		try{
			socket = new Socket(IP, PORT);
			System.out.println(socket);
			out = new PrintWriter(socket.getOutputStream(), true);
			
			out.println("NewRobot:"+getRobotName()); //Envoie de la commande !
			
		} catch (Exception e) {
		System.err.println(e);
		System.exit(1);
		}
		return true;
	}
	public void waitForAction() {
		try{
			System.out.println(socket);
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()
							)
					);//récup serveur
			String message = in.readLine() ;
			System.out.println(message);
			in.close();
		} catch (Exception e) {
		System.err.println(e);
		System.exit(1);
		}
		waitForAction();
	}
	public void run() {
		try {
		String message ;
		do {
			System.out.println("Waiting for request");
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()
							)
					);//récup serveur
			 message = in.readLine() ;
			System.out.println(message);
			setReq(message.replaceAll(" ",""));
		}while(!message.matches("^.*STOP.*$"));
			
				in.close();
				out.close();
				socket.close();
				System.exit(0);
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	public String getRobotName() {
		return RobotName;
	}
	public void setRobotName(String RobotName) {
		this.RobotName = RobotName;
	}
	public String getReq() {
		return req;
	}
	public void setReq(String req) {
		this.req = req;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir le nom du robot :");
		Requete r = new Requete(sc.nextLine());
		r.setServer();
	}
}
