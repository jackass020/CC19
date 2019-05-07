package Connection;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import Packets.*;

public class ServerUDP extends Thread {

    private int serverPort;
    private InetAddress local = null;
    private Pacote pac;
    private DatagramSocket socket;


    public ServerUDP(int sport){

        try {
            this.serverPort = sport;
            this.socket = new DatagramSocket(sport);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void run(){

        byte type = -5;

        try{
            DatagramPacket packet = new DatagramPacket(new byte[1],1);
            while (type !=Pacote.SYN){
                socket.receive(packet);
                type = Pacote.readType(packet);
            }
            System.out.println("Servidor: Recebido pacote SYN...");

            //Identifica cliente
            InetAddress addr = packet.getAddress();
            int clientPort = packet.getPort();

            //Envia um ACk
            ACKPacote ack = new ACKPacote(0,addr,clientPort);
            System.out.println("Servidor: Enviar Confirmação a " + addr.getHostAddress() + " porta " + clientPort);
            socket.send(ack.getPacote());

            //Envia data
            byte buff [] = new byte[1024];
            String st = "teste dados";
            buff = st.getBytes();
            DatagramPacket dados = new DatagramPacket(buff,buff.length,addr,clientPort);
            socket.send(dados);

            File f = new File("/home/joao/Desktop/exemplo.txt");
            int tam = (int) f.length();
            byte data [] = new byte[tam];

            FileInputStream in = new FileInputStream(f);
            int bytes_read = 0, n;
            do { // loop until we've read it all
                n = in.read(data, bytes_read, tam - bytes_read);
                bytes_read += n;
            } while ((bytes_read < tam) && (n != -1));

            DataPacote pac = new DataPacote(1,data,addr,clientPort);
            socket.send(pac.getPacote());



            //Envia Fyn
            FINPacote fin = new FINPacote(addr,clientPort);
            socket.send(fin.getPacote());



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
