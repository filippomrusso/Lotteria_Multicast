import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Gestore implements Runnable{

    private Random gen = new Random();
    private int[] numeriVincenti = new int[5];
    final static int numeroConnessioni = 8;
    private ServerThread[] arrayProcessiDedicatiClient;
    private Thread[] processiServer;
    private ServerSocket socketServer;
    boolean loop = true;
    Thread processo;

    public Gestore(){
        this.gen = gen;
        this.numeriVincenti = numeriVincenti();
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

    public int[] numeriVincenti(){

        for(int i = 0; i < numeriVincenti.length; i++){
           numeriVincenti[i] = gen.nextInt(90);
        }

        return numeriVincenti;
    }

    public int[] getNumeriVincenti() {
        return numeriVincenti;
    }

    public static void main(String[] args) {
        Gestore g = new Gestore();
        g.numeriVincenti();
    }

}
