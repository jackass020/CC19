import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class ServerUDP extends Thread {

    private int serverPort;
    private int port;
    private InetAddress local = null;

    public ServerUDP(int port,int sport){
        this.port=port;
        this.serverPort = sport;
    }

    public void run(){

        try(DatagramSocket serversocket = new DatagramSocket(serverPort)) {
            local = InetAddress.getByName("127.0.0.1");
            PDU pdu = new PDU();
            pdu.setMsg("pduTeste");
            pdu.setChecksum("CheckTeste");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(pdu);
            os.flush();
            byte [] data = outputStream.toByteArray();
            DatagramPacket datagramPacket = new DatagramPacket(data,data.length,local,port);
            serversocket.send(datagramPacket);
            serversocket.receive(datagramPacket);
            if(datagramPacket.getLength()>0){
                System.out.println("enviou");
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
