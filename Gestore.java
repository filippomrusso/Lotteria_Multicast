import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Gestore implements Runnable{

    private Random gen = new Random();
    private int[] numeriVincenti = new int[5];
    int numeroConnessioni;                       //numero di client possibili
    ServerThread[] arrayProcessiDedicatiClient;
    Thread[] processiServer;                    //Array
    ServerSocket socketServer;                  //Socket server
    boolean loop = true;
    Thread processo;

    public Gestore(int numeroConnessioni){
        this.gen = gen;
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

        while(loop){
            try {
                for (int i = 0; i <= numeroConnessioni; i++) {
                    Socket t = null;
                    t = socketServer.accept();
                    arrayProcessiDedicatiClient[i] = new ServerThread(t,this);
                    processiServer[i] = new Thread(arrayProcessiDedicatiClient[i]);
                    processiServer[i].start();
                }
            }
            catch(Exception e){
                loop = false;
            }


            //Gestore g = new Gestore(4);
            //g.numeriVincenti();


        }

    }

    public void numeriVincenti(){
        for(int i = 0; i < numeriVincenti.length; i++){
           numeriVincenti[i] = gen.nextInt(90);
        }
    }

    public int[] getNumeriVincenti() {
        return numeriVincenti;
    }

    public static void main(String[] args) {
        Gestore g = new Gestore(4);
        Thread handler = new Thread(g);
        g.numeriVincenti();
        handler.start();
    }

}
