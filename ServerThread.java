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
            String n = inDaClient.readLine();
            System.out.println(n);
            System.out.println("Genero carte...");
            generoCarteComprate(Integer.parseInt(n));
            
            System.out.println(Integer.parseInt(n));
           
            for (int z = 0; z <= Integer.parseInt(n) ; z++) {
                
                System.out.println(numeriComprati[z]);
                outVersoClient.writeBytes(Integer.toString(numeriComprati[z]) + "\n");
                
            }



            for (int i = 0; i < numeriVincentiDaInviare.length; i++) {
                outVersoClient.writeBytes((Integer.toString(numeriVincentiDaInviare[i]) + "\n"));
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

        public void generoCarteComprate(int nCarteComprate){
        numeriComprati = new int[nCarteComprate];
            for(int i = 0; i <= nCarteComprate; i++){
                numeriComprati[i] = gen.nextInt(90);
                System.out.println(numeriComprati[i]);
                System.out.println("CIAO");
        }
            

    }

}







