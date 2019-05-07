
import Connection.*;
public class Main {

    public static void main(String[] args) {
        int port = 1234;
         Cliente cliente = new Cliente(port);
         ServerUDP server  = new ServerUDP(port);

        cliente.start();
        server.start();
    }
}
