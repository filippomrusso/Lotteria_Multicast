import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable{

    Gestore handler;
    Thread processo;
    private Socket client;
    private BufferedReader inDaClient;
    private DataOutputStream outVersoClient;

    public ServerThread(Socket client, Gestore handler)
     {
         this.handler = new Gestore(5);
         this.client = client;
         try{
             inDaClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
             outVersoClient = new DataOutputStream(client.getOutputStream());
         }
         catch (Exception e){
             e.printStackTrace();
         }

         processo = new Thread(this);
         processo.start();
    }

    @Override
    public void run() {
        try {
            outVersoClient.writeBytes("Ciao");
            System.out.println("Connessione :" + inDaClient.readLine());
        }
        catch(Exception e){}
    }

    public void inviaNumeriAlClient(){

    }
}
