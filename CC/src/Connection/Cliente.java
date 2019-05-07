package Connection;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

import Packets.*;


public class Cliente extends Thread implements  Serializable{

    private int port;
    private DatagramSocket csocket;
    private InetAddress addr ;
    private int serverport = 5000;
    private static final int SIZE=10000;
    private Map<Integer,byte []> pacotesRecebidos;


    public Cliente(int port){

        try {
            this.pacotesRecebidos = new HashMap<>();
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
                pacotesRecebidos.put(seqN,DataPacote.readData(packet));
                System.out.println("antes: "+pacotesRecebidos.get(1).length);

            }

            System.out.println("antes: "+pacotesRecebidos.get(1).length);


            int offset = 0;
            int size= 0;


            for(int i =1; i<=pacotesRecebidos.size();i++){
                size +=pacotesRecebidos.get(i).length;
            }

            System.out.println("size " + size);

            byte fulldata[] = new byte[size];

            for(int i =1; i<=pacotesRecebidos.size();i++){

                System.out.println("offset: "+offset);
                System.arraycopy(pacotesRecebidos.get(i), 0, fulldata, offset,pacotesRecebidos.get(i).length);
                offset+=pacotesRecebidos.get(i).length;
            }

            try {

                FileOutputStream f = new FileOutputStream("/home/joao/Desktop/CC19/CC/src/nandhag.txt");
                f.write(fulldata);
                f.flush();
                f.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }


            System.out.println("Servidor: Conexão terminada");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
