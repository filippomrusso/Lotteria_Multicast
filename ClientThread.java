import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable{

    private String nomeServer = "localhost" ;
    private int porta = 6789 ;
    private Socket client = null;
    private BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
    private BufferedReader inDaSThread;
    private DataOutputStream outVersoSThread;
    private String[] numeriComprati;
    private String[] numeriVincenti = new String[5];
    private  int cash = 0;
    Thread processo;

    public ClientThread(){
        try {
            client = new Socket(nomeServer,porta);
            inDaSThread = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoSThread = new DataOutputStream(client.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        processo = new Thread(this);
        processo.start();
    }

    @Override
    public void run() {
        try {

            System.out.println("Quante carte vuoi comprare?");
            String n = tastiera.readLine();
            numeriComprati = new String[Integer.parseInt(n)];
            outVersoSThread.writeBytes(n + "\n");
            System.out.println("Ricevo le carte...");

            for (int i = 0; i < numeriVincenti.length; i++) {
                numeriVincenti[i] = inDaSThread.readLine() + "\n";
            }

            for (int z = 0; z < numeriComprati.length; z++) {
                numeriComprati[z] = inDaSThread.readLine() + "\n";

            }


            System.out.println("Carte ricevute!" + "\n");

            for (int p = 0; p < numeriComprati.length; p++) {
                System.out.println("Numero acquistato n" + p + " = " + numeriComprati[p]);
            }

            System.out.println("Estrazione in corso ...");

                /* for (int j = 0; j < numeriComprati.length; j++) {

                    if(numeriComprati[j] != numeriVincenti[j]
                            || numeriComprati[j] != numeriVincenti[j+1]
                            || numeriComprati[j] != numeriVincenti[j+2]
                            || numeriComprati[j] == numeriVincenti[j+3]
                            || numeriComprati[j] == numeriVincenti[j+4] ){
                            System.out.println("|    " + numeriComprati[j] + " != " + numeriVincenti[j]);
                    }

                    else{
                        System.out.println("Il numero acquistato " + numeriComprati[j] + " non ha vinto");
                    }
                }*/

            System.out.println("Hai vinto " + cash + " euro cash");


            outVersoSThread.close();
            inDaSThread.close();
            client.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientThread c = new ClientThread();

    }
}
