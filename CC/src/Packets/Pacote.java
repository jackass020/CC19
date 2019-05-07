package Packets;

import java.io.Serializable;
import java.net.DatagramPacket;


public class Pacote implements Serializable {

    public static final byte I = 0;
    public static final byte ACK = 1;
    public static final byte SYN = 2;
    public static final byte FIN = 3;

    protected final byte type;
    protected DatagramPacket pacote;

    public Pacote(byte type) { this.type = type; }

    public DatagramPacket getPacote() { return pacote; }

    public static byte readType(DatagramPacket pacote) {
        byte[] buffer = pacote.getData();
        return buffer[0];
    }



    /*Métodos auxiliares*/

    /**
     * Método auxiliar que permite converter um inteiro num array de bytes
     * @param value inteiro a converter
     * @return array de bytes correspondente
     */

    protected static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

    /**
     * Método auxiliar que permite converter um array de bytes para um inteiro
     * @param b array a converter
     * @return inteiro correspondente
     */
    protected static int byteArrayToInt(byte [] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
    }
}