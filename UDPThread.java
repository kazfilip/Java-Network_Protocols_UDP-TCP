import java.io.IOException;
import java.net.*;

public class UDPThread implements Runnable{

    static DatagramSocket datagramSocket;
    static DatagramPacket datagramPacket;
    int port;


    public UDPThread(int port){
        this.port = port;
    }

    @Override
    public void run() {
        try{
            datagramSocket = new DatagramSocket(port);
            datagramSocket.setBroadcast(true);
            System.out.println("Server started");
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        try {
            while(true){
                byte[] buffer = new byte[1024];
                datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                if (message.startsWith("CCS DISCOVER")){
                    System.out.println("Received message: " + message);
                    InetAddress address = datagramPacket.getAddress();
                    int port = datagramPacket.getPort();
                    byte[] data = "CCS FOUND".getBytes();
                    DatagramPacket response = new DatagramPacket(data, data.length, address, port);
                    datagramSocket.send(response);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
