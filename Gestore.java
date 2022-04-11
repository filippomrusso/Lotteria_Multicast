import java.net.ServerSocket;
import java.net.Socket;

public class Gestore implements Runnable{

    int numeroConnessioni;  //numero di client possibili
    ServerThread[] arrayProcessiDedicatiClient; //Array
    ServerSocket socketServer;  //Socket server
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
        }

    }

    public void comunicaNumeri(){
        for (int i = 0; i < arrayProcessiDedicatiClient.length; i++) {
            arrayProcessiDedicatiClient[i].inviaNumeriAlClient();
        }
    }
}
