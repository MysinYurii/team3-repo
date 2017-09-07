package server;


import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        while (true) {
            new Thread(new Acceptor(serverSocket.accept())).start();
        }
    }
}
