package server;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        HistoryWorker historyWorker = new HistoryWorker(new File("history.txt"));
        ServerSocket serverSocket = new ServerSocket(1234);

        while (true) {
            new Thread(new Acceptor(serverSocket.accept(), historyWorker)).start();
        }
    }
}
