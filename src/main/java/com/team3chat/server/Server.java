package com.team3chat.server;

import com.team3chat.messages.Command;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private Set<Handler> handlersSet;
    private final int port = 1234;
    Server() throws IOException {
        handlersSet = new HashSet<>();
    }

    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    private void start() throws IOException {
        HistoryDealer historyDealer = new HistoryDealer(new File("history.txt"));
        BusinessLogic logicApplier = new BusinessLogic(historyDealer);
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Handler handler = new Handler(this, clientSocket, logicApplier);
            synchronized (handlersSet) {
                handlersSet.add(handler);
            }
            new Thread(handler).start();
        }
    }

    public void closeHandler(Handler handler) {
        synchronized (handlersSet) {
            handlersSet.remove(handler);
        }
    }

    public void sendAll(Command c) {
        synchronized (handlersSet) {
            for (Handler handler : handlersSet) {
                handler.send(c);
            }
        }
    }
}
