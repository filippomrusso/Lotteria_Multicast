import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Gestore implements Runnable{

    private Random gen;
    private int[] numeriVincenti = new int[5];
    int numeroConnessioni;                       //numero di client possibili
    ServerThread[] arrayProcessiDedicatiClient; //Array
    ServerSocket socketServer;                  //Socket server
    boolean loop = true;
    Thread processo;

    public Gestore(int numeroConnessioni){
        this.numeroConnessioni = numeroConnessioni;
        arrayProcessiDedicatiClient = new ServerThread[numeroConnessioni];
        processo = new Thread(this);
        processo.start();
    }

    @Override
    public void run() {
        try{
            socketServer = new ServerSocket(6789);
        }
        catch (Exception e){loop=false;}

        if(loop){
            try {
                for (int i = 0; i < arrayProcessiDedicatiClient.length; i++) {
                    Socket t = null;
                    t = socketServer.accept();
                    arrayProcessiDedicatiClient[i] = new ServerThread(t,this);

                }
            }
            catch(Exception e){}

            try {
                processo.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Gestore g = new Gestore(4);
            g.numeriVincenti();
        }

    }

    public void numeriVincenti(){
        int temp;
        for(int i = 0; i < numeriVincenti.length; i++){
            // da migliorare
           temp = gen.nextInt(90);
           numeriVincenti[i] = temp;
        }
    }

    public int[] getNumeriVincenti() {
        return numeriVincenti;
    }

}
