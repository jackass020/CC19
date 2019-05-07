package Connection;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import Packets.*;


public class Cliente extends Thread implements  Serializable{

    private int port;
    private DatagramSocket csocket;
    private InetAddress addr ;
    private int serverport = 5000;
    private static final int SIZE=10000;


    public Cliente(int port){

        try {
            this.port=port;
            this.csocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run(){

        try {
            byte type = -5;
            this.addr = InetAddress.getByName("127.0.0.1");

            //Envia o SYN
            SYNPacote teste = new SYNPacote(addr,port);
            csocket.send(teste.getPacote());

            //Espera pelo ACK
            DatagramPacket packet = new DatagramPacket(new byte[1],1);
            while (type != Pacote.ACK){
                csocket.receive(packet);
                type = Pacote.readType(packet);
            }

            System.out.println("Cliente: Recebido pacote ACK... Ligação Iniciada!\n");
            //Recebe Dados
            packet=new DatagramPacket(new byte [SIZE],SIZE);
            while (true) {
                /*
                byte[] buffer = new byte[512];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                csocket.receive(response);

                String quote = new String(buffer, 0, response.getLength());

                System.out.println(quote);
                System.out.println();
*/

                csocket.receive(packet);
                if(Pacote.readType(packet) == Pacote.FIN) break;
                int seqN = DataPacote.readSeqN(packet);



                byte data[] = DataPacote.readData(packet);
                try {

                    FileOutputStream f = new FileOutputStream("/home/joao/Desktop/nandhag.txt");
                    f.write(data);
                    f.flush();
                    f.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }


            }
            System.out.println("Servidor: Conexão terminada");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
