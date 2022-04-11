import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable{

    private String nomeServer = "localhost" ;
    private int porta = 6789 ;
    private Socket client = null;
    private BufferedReader inDaSThread;
    private DataOutputStream outVersoSThread;
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
            outVersoSThread.writeBytes(" OKKK " + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientThread client = new ClientThread();
        client.run();
    }
}
