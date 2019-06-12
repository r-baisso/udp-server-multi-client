package io.github.rbaisso.client;

import java.util.Scanner;

public class ClientStarter {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Bem-vindo ao simulador de Clients UDP para servidor Multi-Thread");
        System.out.println("Opções para simulação de 2 clients");
        System.out.println("[1] - Simulação com mensagens ordenadas sequencialmente");
        System.out.println("[2] - Simulação com mensagens mensagens fora de ordem");
        System.out.println("[3] - Simulação com mensagens mensagens perdidas");
        System.out.print("Digite uma das opções acima:");
        int option = reader.nextInt(); // Scans the next token of the input as an int.
        reader.close();
        System.out.println("Iniciando Client 1!");
        UDPClient client1 = new UDPClient(3001, option);
        client1.start();

        System.out.println("Iniciando Client 2!");
        UDPClient client2 = new UDPClient(3002, option);
        client2.start();
    }
}
