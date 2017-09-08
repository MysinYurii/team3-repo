package com.team3chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Server {
    private List<Connection> connections = Collections.synchronizedList(new ArrayList<Connection>());
    private ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket(6666);

            while (true) {
                Socket clientSocket = server.accept();
                Connection connection = new Connection(clientSocket);
                connections.add(connection);
                connection.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket clientSocket;

        private String userName = "";

        public Connection(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                in = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        @Override
        public void run() {
            try {
                userName = in.readLine();
                String clientString;

                while (true) {
                    clientString = in.readLine();
                    if (clientString.equals("/exit")) {
                        break;
                    }
                    synchronized (connections) {
                        Iterator<Connection> iterator = connections.iterator();
                        while (iterator.hasNext()) {
                            iterator.next().out.println(userName + ": " + clientString);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        public void close() {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("threads were not closed");
            }
        }
    }
}
