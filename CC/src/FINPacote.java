import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class FINPacote extends Pacote {

    public FINPacote(InetAddress addr, int port) throws IOException {
        super(FIN);
        byte[] buffer = {type};
        pacote= new DatagramPacket(buffer, 1, addr, port);
    }

}