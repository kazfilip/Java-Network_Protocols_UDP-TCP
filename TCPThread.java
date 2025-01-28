
import java.io.IOException;


import java.net.ServerSocket;
import java.net.Socket;



public class TCPThread implements Runnable{

    int port;

    public TCPThread(int port){
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(port);
            while(true){

                socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
                new Thread(new PrintStats(ClientHandler.getStats())).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
