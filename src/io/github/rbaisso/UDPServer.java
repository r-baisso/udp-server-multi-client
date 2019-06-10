package io.github.rbaisso;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;

public class UDPServer {

    private static HashMap<String,String[]> messageToken;

    public static void main(String[] args) throws Exception {

        int port = 30001;
        System.out.println("Instanciando socket de comunicação na porta: " + port);
        DatagramSocket serverSocket = new DatagramSocket(port);

        messageToken = new HashMap<>();

        while (true) {
            System.out.println("Instanciando Buffer");
            byte[] serverBuffer = new byte[1024];

            System.out.println("Instanciando datagrama de recebimento");
            DatagramPacket recievePacket = new DatagramPacket(serverBuffer, serverBuffer.length);

            System.out.println("Aguardando pacote...");
            serverSocket.receive(recievePacket);

            new UDPClientConnection(recievePacket, messageToken).run();

        }
    }

}
