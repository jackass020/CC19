package Packets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class SYNPacote extends Pacote {

    public SYNPacote(InetAddress addr, int port) throws IOException {
        super(SYN);
        byte[] buffer = {type};
        pacote = new DatagramPacket(buffer, 1, addr, port);
    }

}