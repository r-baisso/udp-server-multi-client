package io.github.rbaisso;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient extends Thread{

    private  DatagramSocket clientSocket;
    private  int clientPort;
    private  InetAddress serverIP;
    private  byte[] sendData;
    private  DatagramPacket clientPacket;

    public static void main(String[] args) throws Exception {new UDPClient().run();}

    @Override
    public void run() {

        //endereco do servidor
        String server = "127.0.0.1";
        int serverPort = 30001;
        try {

            System.out.println("Construindo conexão com servidor " + server + ":" + serverPort);
            serverIP = InetAddress.getByName(server);

            System.out.println("Instanciando Socket de comunicação");
            //canal de comunicacao sem conexao entre client e servidor
            clientPort = 8001;
            clientSocket = new DatagramSocket(clientPort);

            System.out.println("Construindo buffer e seu conteúdo");
            //buffer de envio e seu conteudo
            sendData = new byte[1024];
            sendData = "olá server".getBytes();

            System.out.println("Instanciando datagrama");
            //datagrama
            clientPacket = new DatagramPacket(sendData, sendData.length, serverIP, serverPort);

            System.out.println("Enviando datagrama para servidor");
            clientSocket.send(clientPacket);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
