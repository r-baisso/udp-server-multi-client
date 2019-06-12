package io.github.rbaisso.client;

import io.github.rbaisso.common.Message;
import io.github.rbaisso.common.MessagesBuilder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class UDPClient extends Thread {

    private DatagramSocket clientSocket;
    private int clientPort;
    private InetAddress serverIP;
    private byte[] sendData;
    private DatagramPacket clientPacket;
    private int option;

    public UDPClient(int clientPort, int option) {
        this.option = option;
        this.clientPort = clientPort;
    }


    @Override
    public void run() {

        //endereco do servidor
        String server = "127.0.0.1";
        int serverPort = 30001;

        try {

            System.out.println("Construindo conexão com servidor " + server + ":" + serverPort);
            serverIP = InetAddress.getByName(server);

            System.out.println("Instanciando Socket de comunicação");
            clientSocket = new DatagramSocket(clientPort);
            List<Message> messages = new ArrayList<>();
            if (option == 1) {
                 messages = MessagesBuilder.getSorted();
            } else if(option == 2){
                messages = MessagesBuilder.getUnsorted();
            } else if (option == 3){
                messages = MessagesBuilder.getMissPosition();
            } else {
                throw new UnsupportedOperationException();
            }
            for (Message message : messages) {
                sendData = message.getBytes();

                clientPacket = new DatagramPacket(sendData, sendData.length, serverIP, serverPort);

                System.out.println("Enviando mensagem");
                clientSocket.send(clientPacket);
                System.out.println("Envio bem sucedido!");
            }


            byte[] serverBuffer = new byte[1024];
            DatagramPacket recievePacket = new DatagramPacket(serverBuffer, serverBuffer.length);
            //para a perda de mensagens, precisamos de uma lista completa para saber qual mensagem foi perdida
            messages = MessagesBuilder.getSorted();

            while(true) {
                System.out.println("Aguardando retorno para mensagens perdidas...");
                clientSocket.receive(recievePacket);

                System.out.println("Resposta recebida");
                byte[] recievePacketData = recievePacket.getData();
                Message returnMessage = new Message(recievePacketData);

                System.out.println("Mensagem da posição " + returnMessage.getId() + " foi perdida");
                int indexMissMessage = messages.indexOf(returnMessage);
                sendData = messages.get(indexMissMessage).getBytes();

                System.out.println("Reenviando");
                clientPacket.setData(sendData);
                clientSocket.send(clientPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
