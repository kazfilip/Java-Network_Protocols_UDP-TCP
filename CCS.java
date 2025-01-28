import java.io.IOException;
import java.net.*;

public class CCS {
    public static void main(String[] args){
        if (args.length<1){
            System.out.println("To run the program provide a <port> value: > java -jar CCS.jar <port> ");
            System.exit(0);
        }

        int port;
        try{
            port = Integer.parseInt(args[0]);
            Thread udpThread = new Thread(new UDPThread(port));
            udpThread.start();
            Thread tcpThread = new Thread(new TCPThread(port));
            tcpThread.start();

        }catch (NumberFormatException e){
            System.out.println("Provide a number as argument");
            System.exit(0);
        }

    }
}
