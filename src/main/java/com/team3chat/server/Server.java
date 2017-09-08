package com.team3chat.server;

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
                    clientString = in.readLine();
                    if (clientString.equals("/exit")) {
                        break;
                    } else if (clientString.startsWith("/snd")) {
                        clientString = historyDealer.saveHistory(userName + ": " + clientString.substring(5));
                        synchronized (connections) {
                            Iterator<Connection> iterator = connections.iterator();
                            while (iterator.hasNext()) {
                                iterator.next().out.println(clientString);
                            }
                        }
                    } else if (clientString.equals("/hist")) {
                        ArrayList<String> result = historyDealer.readHistory();
                        for (String s : result) {
                            out.println(s);
                        }
                    } else if (clientString.startsWith("/chid")) {
                        String newName = clientString.substring(6);
                        boolean noSuchName = true;
                        synchronized (connections) {
                            Iterator<Connection> iterator = connections.iterator();
                            while (iterator.hasNext()) {
                                if (newName.equals(iterator.next().userName)) {
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SavingHistoryException e) {
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
                connections.remove(this);
            } catch (Exception e) {
                System.out.println("threads were not closed");
            }
        }
    }
}
