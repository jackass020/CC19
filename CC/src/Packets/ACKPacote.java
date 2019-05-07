package Packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ACKPacote extends Pacote {

    private int seqN;

    public ACKPacote(int seqN, InetAddress addr, int port) throws IOException {
        super(ACK); this.seqN = seqN;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(type);
        outputStream.write(intToByteArray(seqN));

        byte[] buffer = outputStream.toByteArray();
        pacote = new DatagramPacket(buffer, buffer.length, addr, port);
    }

    public int getSeqN() { return seqN; }

    public static int readSeqN(DatagramPacket packet) {
        byte[] buffer = packet.getData();
        byte[] intBuffer = new byte[4];
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
        inputStream.skip(1);
        inputStream.read(intBuffer,0,4);
        return byteArrayToInt(intBuffer);

    }
}