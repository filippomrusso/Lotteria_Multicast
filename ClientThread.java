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
    private boolean[] numeriPresi;
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
            numeriPresi = new boolean[numeriComprati.length];
            outVersoSThread.writeBytes(n + "\n");
            System.out.println("Ricevo le carte..." + "\n");

            for (int i = 0; i < numeriVincenti.length; i++) {
                numeriVincenti[i] = inDaSThread.readLine() ;
                System.out.println("Numero vincente: " + numeriVincenti[i]);
            }

            for (int z = 0; z < numeriComprati.length; z++) {
                numeriComprati[z] = inDaSThread.readLine() ;

            }


            System.out.println("\n" + "Carte ricevute!" + "\n");

            for (int p = 0; p < numeriComprati.length; p++) {
                System.out.println("Numero acquistato :" + numeriComprati[p]);
            }
            System.out.println("\n");

            System.out.println("Estrazione in corso ..." + "\n");

            numeriPresi = checkNumeri(numeriComprati, numeriVincenti);

            System.out.println("\n");

            for(int x = 0; x < numeriComprati.length; x++ ){

                String s = Boolean.toString(numeriPresi[x]);
                System.out.println("|  " + s);
                System.out.println("+---------");

            }

            System.out.println("Hai vinto " + cash + " euro cash");


            outVersoSThread.close();
            inDaSThread.close();
            client.close();
        }


        catch (Exception e) {
            e.printStackTrace();
        }



        }

        boolean[] checkNumeri(String[] nCop, String[] nVinc) {

            boolean[] nPresi = new boolean[nCop.length];
            int[] nC = new int[nCop.length];
            int[] nV = new int[nVinc.length];

            for(int i = 0; i < nCop.length; i ++ ){
                nC[i] = Integer.parseInt(nCop[i]);
            }

            for(int i = 0; i < nVinc.length; i ++ ){
                nV[i] = Integer.parseInt(nVinc[i]);
            }


            for (int j = 0; j < nC.length; j++) {

                for (int i = 0; i < nV.length; i++) {

                     int nC_temp = nC[j];
                     int nV_temp = nV[i];

                     System.out.format("|  " + nC[j] + " --> " +nV[i] + "  |" );

                    if (nC[j] != nV[i]) {
                        nPresi[j] = false;
                    }

                    if (nC[j] == nV[i]) {
                        nPresi[j] = true;
                    }
                }

                System.out.println("\n");
            }

            return nPresi;
        }


    public static void main(String[] args) {
        ClientThread c = new ClientThread();

    }
}
