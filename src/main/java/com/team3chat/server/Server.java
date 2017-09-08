package com.team3chat.server;

import com.team3chat.exceptions.MessageHandlingException;
import com.team3chat.exceptions.SavingHistoryException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private List<Connection> connections = Collections.synchronizedList(new LinkedList<Connection>());
    private ServerSocket serverSocket;
    private HistoryDealer historyDealer;

    public Server() throws IOException {
        serverSocket = new ServerSocket(6667);
        historyDealer = new HistoryDealer(new File("history.txt"));
    }

    public void start() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Connection connection = new Connection(clientSocket);
            synchronized (connections) {
                connections.add(connection);
            }
            connection.start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
        System.out.println("Server was closed");
    }

    private class Connection extends Thread {
        public static final String CHID = "/chid";
        public static final String EXIT = "/exit";
        public static final String SND = "/snd";
        public static final String HIST = "/hist";
        public static final int MAX_MESSAGE_LENGTH = 150;
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
                clientMessageHandling();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void clientMessageHandling() throws IOException {
            String clientString;
            while (true) {
                try {
                    clientString = in.readLine();
                    if (clientString != null) {
                        if (clientString.startsWith(CHID)) {
                            chidCommandHandling(clientString);
                        } else if (clientString.length() > MAX_MESSAGE_LENGTH) {
                            System.out.println(
                                    String.format("Incorrect input format. Message should be shorter than %d symbols.",
                                            MAX_MESSAGE_LENGTH));
                        } else if (clientString.startsWith(SND)) {
                            sndCommandHandling(clientString);
                        } else if (clientString.equals(HIST)) {
                            histCommandHandling();
                        } else if (clientString.equals(EXIT)) {
                            break;
                        } else {
                            out.println("Incorrect input format. Message should start with " +
                                    SND + " or " + CHID + " or be equal to " + HIST + " or " + EXIT);
                        }
                    }
                } catch (SavingHistoryException | MessageHandlingException e) {
                    e.printStackTrace();
                    out.println(e.getMessage());
                }
            }
        }

        private void chidCommandHandling(String clientString) throws MessageHandlingException {
            String newName = clientString.substring(CHID.length()).trim();
            boolean noSuchName = true;
            if (newName.length() != 0) {
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
            } else {
                throw new MessageHandlingException("Tried to change name to empty string.");
            }
        }

        private void histCommandHandling() throws SavingHistoryException {
            List<String> result = historyDealer.readHistory();
            for (String s : result) {
                out.println(s);
            }
        }

        private void sndCommandHandling(String clientString) throws MessageHandlingException, SavingHistoryException {
            if (clientString.substring(SND.length()).trim().length() == 0) {
                throw new MessageHandlingException("Tried to send empty message.");
            } else {
                clientString = historyDealer.saveHistory(userName + ":" + clientString.substring(SND.length()));
                for (Connection connection : connections) {
                    connection.out.println(clientString);
                }
            }
        }

        void close() {
            try {
                in.close();
                out.close();
                clientSocket.close();
                connections.remove(this);
            } catch (Exception e) {
                System.out.println("Threads were not closed");
            }
        }
    }
}
