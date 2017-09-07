package com.team3chat.server;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        HistoryDealer historyDealer = new HistoryDealer(new File("history.txt"));
        ServerSocket serverSocket = new ServerSocket(1234);
        BusinessLogic logicApplier = new BusinessLogic(historyDealer);

        while (true) {
            new Thread(new Acceptor(serverSocket.accept(), logicApplier)).start();
        }
    }
}
