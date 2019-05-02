import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Cliente extends Thread {

    private int port;
    private Pacote pac;

    public Cliente(int port){

        this.port=port;
        this.pac = new Pacote();
    }

    public void run(){

        try(DatagramSocket clientsockt = new DatagramSocket(port)) {
            InetAddress  local = InetAddress.getByName("127.0.0.1");
            byte[] buffer = new byte[1024];
            clientsockt.setSoTimeout(5000);
            while (true){
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
                clientsockt.receive(datagramPacket);


                byte data [] = datagramPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                clientsockt.send(datagramPacket);


                try {
                    PDU pdu = (PDU) is.readObject();
                    System.out.println(pdu.toString());
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Conexão terminou");
        }
    }
}
