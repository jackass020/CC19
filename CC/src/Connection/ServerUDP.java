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
            File f = new File("/home/joao/Desktop/exemplo.txt");
            int tam = (int) f.length();

            FileInputStream in = new FileInputStream(f);

            int bytes_read = 0, n=0;
            int i = 0,j=1,aux=1024;
            byte data [] = new byte[1024];
            // loop until we've read it all
            for(i=0;i<(Math.ceil(tam/1024.0));i++){
                if(tam-bytes_read < aux){
                    aux =tam-bytes_read;
                    data = new byte[aux];
                }
                n = in.read(data, bytes_read,aux);
                bytes_read += n;
                DataPacote pac = new DataPacote(j,data,addr,clientPort);
                socket.send(pac.getPacote());
                j++;

            }


            //Envia Fyn
            FINPacote fin = new FINPacote(addr,clientPort);
            socket.send(fin.getPacote());



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
