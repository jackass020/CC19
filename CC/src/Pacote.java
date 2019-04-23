import  java.net.DatagramPacket;

public class Pacote {

    private  byte Id = 0;
    private  byte ACK = 1;
    private  byte SYN = 2;
    private  byte FIN =3;


    public byte getId() {
        return Id;
    }

    public void setId(byte id) {
        Id = id;
    }

    public byte getACK() {
        return ACK;
    }

    public byte getSYN() {
        return SYN;
    }

    public byte getFIN() {
        return FIN;
    }

    public void setACK(byte ACK) {
        this.ACK = ACK;
    }

    public void setSYN(byte SYN) {
        this.SYN = SYN;
    }

    public void setFIN(byte FIN) {
        this.FIN = FIN;
    }

}
