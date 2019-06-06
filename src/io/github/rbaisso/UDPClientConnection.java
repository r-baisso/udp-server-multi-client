package io.github.rbaisso;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class UDPClientConnection extends Thread {

    private String recievedMessage;
    private DatagramPacket recievePacket;
    private int clientId;

    public UDPClientConnection(DatagramPacket recievePacket, int clientId) {
        this.recievePacket = recievePacket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        System.out.println("Abrindo pacote recebido");
        String recievedMessage = new String(recievePacket.getData(),
                recievePacket.getOffset(),
                recievePacket.getLength());

        InetAddress clientIP = recievePacket.getAddress();
        int clientPort = recievePacket.getPort();

        System.out.println("Pacote recebida do client " + clientIP + ":" + clientPort);
        System.out.println("Conteúdo: " + recievedMessage);
        System.out.print("\n");
    }
}
