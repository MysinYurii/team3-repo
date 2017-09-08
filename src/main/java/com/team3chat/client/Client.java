package com.team3chat.client;

/**
 * Created by Java_12 on 08.09.2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private BufferedReader in;
    private PrintWriter out;
    private Socket clientSocket;

    public Client() throws IOException {
        clientSocket = new Socket(InetAddress.getLocalHost(), 6667);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void start() {
        try {
            ClientListener listener = new ClientListener(in);
            new Thread(listener).start();
            String clientString = readNickname();

            while (!Objects.equals(clientString, "/exit") && !listener.isInterrupted()) {
                clientString = ClientStart.scan();
                out.println(clientString);
            }
            listener.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }


    private String readNickname() {
        String clientString = "";
        while (clientString.isEmpty()) {
            System.out.println("Enter name: ");
            clientString = ClientStart.scan();
        }
        System.out.println("Your name is: " + clientString);
        out.println(clientString);
        return clientString;
    }

    private void close() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unable to close thread");
        }
    }
}
