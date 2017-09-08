package com.team3chat.server;

import com.team3chat.exceptions.MessageHandlingException;
import com.team3chat.exceptions.SavingHistoryException;

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
    private HistoryDealer historyDealer;

    public Server() {
        try {
            server = new ServerSocket(6666);
            historyDealer = new HistoryDealer(new File("history.txt"));

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
                    try {
                        clientString = in.readLine();
                        if (clientString.equals("/exit")) {
                            break;
                        } else if (clientString.startsWith("/snd")) {
                            sndCommandHandling(clientString);
                        } else if (clientString.equals("/hist")) {
                            histCommandHandling();
                        } else if (clientString.startsWith("/chid")) {
                            chidCommandHandling(clientString);
                        }
                    } catch (SavingHistoryException | MessageHandlingException e) {
                        e.printStackTrace();
                        out.println(e.getMessage());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void chidCommandHandling(String clientString) throws MessageHandlingException {
            String newName = clientString.substring("/chid ".length());
            boolean noSuchName = true;
            if (newName.trim().length() == 0) {
                throw new MessageHandlingException("Tried to change name to empty string.");
            } else {
                synchronized (connections) {
                    for (Connection connection : connections) {
                        if (newName.equals(connection.userName)) {
                            out.println("This name has already been taken, try another one.");
                            noSuchName = false;
                            break;
                        }
                    }
                    if (noSuchName) {
                        userName = newName;
                        out.println("Your name was successfully changed to " + userName);
                    }
                }
            }
        }

        private void histCommandHandling() throws SavingHistoryException {
            ArrayList<String> result = historyDealer.readHistory();
            for (String s : result) {
                out.println(s);
            }
        }

        private void sndCommandHandling(String clientString) throws MessageHandlingException, SavingHistoryException {
            if (clientString.trim().length() < "/snd ".length()) {
                throw new MessageHandlingException("Tried to send empty message.");
            } else {
                clientString = historyDealer.saveHistory(userName + ": " + clientString.substring(5));
                synchronized (connections) {
                    for (Connection connection : connections) {
                        connection.out.println(clientString);
                    }
                }
            }
        }

        public void close() {
            try {
                in.close();
                out.close();
                clientSocket.close();
                connections.remove(this);
            } catch (Exception e) {
                System.out.println("threads were not closed");
            }
        }
    }
}
