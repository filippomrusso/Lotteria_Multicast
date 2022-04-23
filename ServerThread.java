import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
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
         this.handler = new Gestore(4);
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
            numeriVincentiDaInviare = handler.getNumeriVincenti();
            System.out.println("Aspetto di ricevere n carte da client");
            int n = inDaClient.read();
            System.out.println("Genero carte...");
            generoCarteComprate(n);

            boolean check1 = false;
            for (int z = 0; z <= n; z++) {
                outVersoClient.writeInt(numeriComprati[z]);
            }



            for (int i = 0; i < numeriVincentiDaInviare.length; i++) {
                outVersoClient.writeInt((numeriVincentiDaInviare[i]));
            }


            client.close();
            inDaClient.close();
            outVersoClient.close();

        }
        catch(Exception e){
            try {
                client.close();
                inDaClient.close();
                outVersoClient.close();
            }
            catch (IOException x){

            }
        }



        }

        public void generoCarteComprate(int nCarteComprate){
        numeriComprati = new int[nCarteComprate];
            for(int i = 0; i <= nCarteComprate; i++){
                numeriComprati[i] = gen.nextInt(90);
        }

    }

}







