package io.github.rbaisso.server;

import io.github.rbaisso.common.Message;

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

        System.out.println("Abrindo pacote recebido");
        Message message = new Message(recievePacket.getData());

        //inicializa uma chave para guardar o buffer do client
        if(!messageToken.containsKey(fullAdress)){
            messageToken.put(fullAdress,new String[message.getPacketSize()]);
        }

        Integer id = message.getId();
        //acessa mensagens do client
        String [] messageArray = messageToken.get(fullAdress);
        //tratamento de duplicados
        if(messageArray[id] == null) {
            messageArray[id] = message.getValue();
            //colocando novos valores
            messageToken.replace(fullAdress, messageArray);
        }

    }
}
