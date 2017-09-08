package com.team3chat.server;

import com.team3chat.exceptions.SavingHistoryException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server implements Closeable {
    private final List<Connection> connections = Collections.synchronizedList(new ArrayList<Connection>());
    private ServerSocket serverSocket;
    private HistoryDealer historyDealer;

    public Server() throws IOException {
        serverSocket = new ServerSocket(6666);
        historyDealer = new HistoryDealer(new File("history.txt"));
    }

    public void start() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            try {
                Connection connection = new Connection(clientSocket);
                connections.add(connection);
                connection.start();
            } catch (RuntimeException e) {
                clientSocket.close();
                System.out.println("Failed to handle new connection: " + e.getMessage());
            }
        }
    }

    private void publishToAll(String publishedMessage) {
        connections.forEach(connection -> connection.out.println(publishedMessage));
    }

    @Override
    public void close() throws IOException {
        connections.forEach(Connection::close);
        serverSocket.close();
    }

    private class Connection extends Thread implements Closeable {
        private final BufferedReader in;
        private final PrintWriter out;
        private Socket clientSocket;
        private String userName = "";

        public Connection(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
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
                    } else if (clientString.startsWith("/snd")) {
                        clientString = historyDealer.saveHistory(userName + ": " + clientString.substring(5));
                        publishToAll(clientString);
                    } else if (clientString.equals("/hist")) {
                        ArrayList<String> result = historyDealer.readHistory();
                        result.forEach(out::println);
                    } else if (clientString.startsWith("/chid")) {
                        userName = clientString.substring(6);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SavingHistoryException e) {
                System.out.println("Failed to save history: " + e.getMessage());
            } finally {
                close();
            }
        }

        @Override
        public void close() {
            try {
                clientSocket.close();
            } catch (RuntimeException | IOException e) {
                System.out.println("Thread " + this.getName() + " was not closed successfully: " + e.getMessage());
            }
        }
    }
}
