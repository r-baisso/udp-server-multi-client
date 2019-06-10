package io.github.rbaisso;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;

public class UDPClientConnection extends Thread {

    private DatagramPacket recievePacket;
    private HashMap<String,String[]> messageToken;

    public UDPClientConnection(DatagramPacket recievePacket, HashMap<String,String[]> messageToken) {
        this.recievePacket = recievePacket;
        this.messageToken = messageToken;
    }

    @Override
    public void run() {

        InetAddress clientIP = recievePacket.getAddress();
        int clientPort = recievePacket.getPort();
        String fullAdress = clientIP.getHostAddress() + ":" + clientPort;


        if(!messageToken.containsKey(clientPort)){
            messageToken.put(fullAdress,new String[5]);
        }


        System.out.println("Abrindo pacote recebido");
        String recievedMessage = new String(recievePacket.getData(),
                recievePacket.getOffset(),
                recievePacket.getLength());

        String[] commaSplit = recievedMessage.split(",");

        Integer id = Integer.valueOf(commaSplit[0].split(":")[1]);
        messageToken.get(fullAdress)[id] = commaSplit[1].split(":")[1];

        System.out.println("Pacote recebida do client " + clientIP + ":" + clientPort);
        System.out.println("Conteúdo: " + recievedMessage);
        System.out.print("\n");
    }
}
