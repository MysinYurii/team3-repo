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
    int port;
    Server(int port) throws IOException {
        handlersSet = new HashSet<>();
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        new Server(1234).start();
    }

    private void start() throws IOException {
        HistoryDealer historyDealer = new HistoryDealer(new File("history.txt"));
        BusinessLogic logicApplier = new BusinessLogic(historyDealer);
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Handler handler = new Handler(this, clientSocket, logicApplier);
            handlersSet.add(handler);
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
