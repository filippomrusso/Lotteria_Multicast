import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class ServerThread implements Runnable{

    Gestore handler;
    private Random gen = new Random();
    private Socket client = null;
    private BufferedReader inDaClient;
    private DataOutputStream outVersoClient;
    private int[] numeriComprati;
    private int[] numeriVincentiDaInviare;

    public ServerThread(Socket client, Gestore handler)
     {
         this.handler = new Gestore();
         this.client = client;
         try{
             inDaClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
             outVersoClient = new DataOutputStream(client.getOutputStream());
         }
         catch (Exception e){
             e.printStackTrace();
         }
    }

    @Override
    public void run() {

System.out.println("Sono stato creato");

        try {
            numeriVincentiDaInviare = handler.getNumeriVincenti();
            System.out.println("Aspetto di ricevere n carte da client");
            String n = inDaClient.readLine();
            System.out.println(n);
            System.out.println("Genero carte...");
            generoCarteComprate(Integer.parseInt(n));



            for (int i = 0; i < numeriVincentiDaInviare.length; i++) {
                outVersoClient.writeBytes(Integer.toString(numeriVincentiDaInviare[i]) + "\n");
            }


          
            inDaClient.close();
            outVersoClient.close();
            client.close();

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

        public void generoCarteComprate(int nCarteComprate) throws IOException {
        numeriComprati = new int[nCarteComprate];
        System.out.println("Acquistate da client " + client.getPort());
            for(int i = 0; i <= nCarteComprate; i++){
                numeriComprati[i] = gen.nextInt(90);
                System.out.println("Carta "+ i +": " +numeriComprati[i]);
                outVersoClient.writeBytes(Integer.toString(numeriComprati[i]));
        }
            

    }

}







