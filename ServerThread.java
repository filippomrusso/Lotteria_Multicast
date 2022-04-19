import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class ServerThread implements Runnable{

    Gestore handler;
    Thread processo;
    private Random gen = new Random();
    private Socket client = null;
    private BufferedReader inDaClient;
    private DataOutputStream outVersoClient;
    private int[] numeriComprati;
    private int[] numeriVincentiDaInviare;

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

        for(;;){
        try {
            numeriVincentiDaInviare = handler.getNumeriVincenti();
            System.out.println("Aspetto di ricevere n carte da client");
            String n = inDaClient.readLine();
            int nReale = Integer.parseInt(n);
            System.out.println("Genero carte...");

            for (int z = 0; z < nReale; z++) {
                numeriComprati[z] = gen.nextInt(90);
                System.out.println(numeriComprati[z]);
                outVersoClient.writeInt((numeriComprati[z]));
            }

            for (int i = 0; i < numeriVincentiDaInviare.length; i++) {
                outVersoClient.writeBytes(String.valueOf(numeriVincentiDaInviare[i] + "\n"));
            }

            inDaClient.close();
            outVersoClient.close();
            client.close();

        }
        catch(Exception e){}
        }



    }

}
