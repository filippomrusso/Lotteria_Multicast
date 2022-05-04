import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Gestore implements Runnable{

    private Random gen = new Random();
    private int[] numeriVincenti = new int[5];
    final static int numeroConnessioni = 4;                       //numero di client possibili
    ServerThread[] arrayProcessiDedicatiClient;
    Thread[] processiServer;                    //Array
    ServerSocket socketServer;                  //Socket server
    boolean loop = true;
    Thread processo;

    public Gestore(){
        this.gen = gen;
        this.numeriVincenti();
        processiServer = new Thread[numeroConnessioni];
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
                for (int i = 0; i <= 4; i++) {
                    Socket t = null;
                    t = socketServer.accept();
                    System.out.println("Client connesso = " + t.getPort());
                    arrayProcessiDedicatiClient[i] = new ServerThread(t,this);
                    processiServer[i] = new Thread(arrayProcessiDedicatiClient[i]);
                    processiServer[i].start();
                }
            }
            catch(Exception e){
                e.printStackTrace();
                loop = false;
            }
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
        Gestore g = new Gestore();
        g.numeriVincenti();
    }

}
