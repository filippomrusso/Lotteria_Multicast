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

            for (int i = 0; i < numeriVincentiDaInviare.length; i++) {
                outVersoClient.writeBytes((numeriVincentiDaInviare[i]) + "\n");
            }





            generoCarteComprate(Integer.parseInt(n));

          
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
            System.out.println("+------------------------------------+");
            System.out.println("| Acquisti di client da client " + client.getPort() + " |");
            System.out.println("+------------------------------------+");
            for(int i = 0; i <= nCarteComprate; i++){

                numeriComprati[i] = gen.nextInt(90);

                if(numeriComprati[i] >= 10 && numeriComprati[i] <= 90 ){
                    System.out.println("|             Carta "+ i +": " + numeriComprati[i] + "            |");
                }
                else {
                    System.out.println("|             Carta "+ i +": " + numeriComprati[i] + "             |");
                }

                System.out.println("+------------------------------------+");

                outVersoClient.writeBytes((numeriComprati[i]) + "\n");
        }

            

    }

}







