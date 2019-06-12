package io.github.rbaisso.server;

import io.github.rbaisso.common.Message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Consumer extends Thread {

    private HashMap<String, String[]> messageToken;
    private DatagramSocket serverSocket;
    private boolean isFinished = false;

    public Consumer(HashMap<String, String[]> messageToken, DatagramSocket serverSocket) {
        this.messageToken = messageToken;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        loopRun:
        while (true) {
            loopClient:
            try {
                //Aguarda 1 segundo antes de comecar a consumir as mensagens
                Thread.sleep(5000L);
                for (Map.Entry<String, String[]> pair : messageToken.entrySet()) {
                    String[] clientMessages = pair.getValue();
                    //percorre o array que contem todas as mensagens de um clients e consome
                    //caso uma das mesagens esteja faltando dá um retorno ao client para reenviar
                    loopMessages:
                    for (int i = 0; i < clientMessages.length; i++) {
                        //acessando mensagens do client
                        String[] clientAdress = pair.getKey().split(":");
                        Integer clientPort = Integer.valueOf(clientAdress[1]);
                        String clientHost = clientAdress[0];

                        System.out.println("Consumindo mensagem do Client - " + pair.getKey());
                        if (clientMessages[i] != null) {
                            System.out.println("Mensagem de id=" + i);
                            System.out.println("Mensagem: " + clientMessages[i]);
                        } else {
                            //tratamento de mensagem perdida
                            System.out.println("Mensagem de id=" + i + " não encontrada nos pacotes recebidos");
                            System.out.println("Enviando retorno de alerta para o client");

                            InetAddress clientIp = InetAddress.getByName(clientHost);

                            Message message = new Message(i, "reenviar ", 1);
                            byte[] sendData = message.getBytes();

                            DatagramPacket clientPacket = new DatagramPacket(sendData, sendData.length, clientIp, clientPort);
                            serverSocket.send(clientPacket);
                            break loopMessages;

                        }
                        boolean isNull = arrayIsNull(clientMessages);
                        if (i == clientMessages.length - 1 && !isNull) {
                            messageToken.remove(pair.getKey());
                            break loopClient;
                        }
                    }
                    System.out.print("\n");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean arrayIsNull(String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) return false;
        }
        return false;
    }

}
