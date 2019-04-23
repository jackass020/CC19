import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Cliente extends Thread {

    private int port;

    public Cliente(int port){
        this.port=port;
    }

    public void run(){
        try(DatagramSocket clientsockt = new DatagramSocket(port)) {
            byte[] buffer = new byte[1024];
            clientsockt.setSoTimeout(5000);
            while (true){
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
                clientsockt.receive(datagramPacket);
                byte data [] = datagramPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                clientsockt.send(datagramPacket);

                try{
                    PDU pdu =(PDU) is.readObject();
                    System.out.println(pdu.toString());

                } catch (ClassNotFoundException e) {
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
