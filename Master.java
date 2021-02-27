public class Master{
    public static void main(String args[]){
    
    	Thread serveur = new Thread(new reqServeur());
    	
        serveur.start();
    	
    	Thread remote = new Thread(new RequeteEmetteur());
        
    	remote.start();
    	
    	//Thread machine = new Thread(new Machine());// Machine machine = new Machine();
        
        //machine.start();
        
       // while(true);
        
    }
}