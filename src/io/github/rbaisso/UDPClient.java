package io.github.rbaisso;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UDPClient extends Thread {

    private DatagramSocket clientSocket;
    private int clientPort;
    private InetAddress serverIP;
    private byte[] sendData;
    private DatagramPacket clientPacket;


    public UDPClient(int clientPort) {
        this.clientPort = clientPort;
    }

    public static void main(String[] args) {

        UDPClient client1 = new UDPClient(3001);
        client1.start();


        UDPClient client2 = new UDPClient(3002);
        client2.start();

    }

    @Override
    public void run() {

        //endereco do servidor
        String server = "127.0.0.1";
        int serverPort = 30001;
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(0,"ola", 4));
        messages.add(new Message(1,"sou", 4));
        messages.add(new Message(2,"o", 4));
        messages.add(new Message(3,"client " + this.clientPort, 4));
        try {

            System.out.println("Construindo conexão com servidor " + server + ":" + serverPort);
            serverIP = InetAddress.getByName(server);

            System.out.println("Instanciando Socket de comunicação");
            //canal de comunicacao sem conexao entre client e servidor
            clientSocket = new DatagramSocket(clientPort);

            for (Message message : messages) {
                sendData = message.getBytes();
                System.out.println("Instanciando datagrama");
                //datagrama
                clientPacket = new DatagramPacket(sendData, sendData.length, serverIP, serverPort);

                System.out.println("Enviando datagrama para servidor");
                clientSocket.send(clientPacket);
            }


            byte[] serverBuffer = new byte[1024];
            DatagramPacket recievePacket = new DatagramPacket(serverBuffer, serverBuffer.length);

            while(true) {
                System.out.println("Aguardando pacote...");
                clientSocket.receive(recievePacket);

                byte[] recievePacketData = recievePacket.getData();
                Message returnMessage = new Message(recievePacketData);

                int indexMissMessage = messages.indexOf(returnMessage);
                sendData = messages.get(indexMissMessage).getBytes();

                clientPacket.setData(sendData);
                clientSocket.send(clientPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
