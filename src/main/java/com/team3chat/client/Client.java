package com.team3chat.client;

/**
 * Created by Java_12 on 08.09.2017.
 */

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private BufferedReader in;
    private PrintWriter out;
    private Socket clientSocket;

    public Client() {
        try {
            clientSocket = new Socket("127.0.0.1", 6667);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            ClientListener listener = new ClientListener(in);
            new Thread(listener).start();
            String clientString = readNickname();

            while (!clientString.equals("/exit") && !listener.isInterrupted()) {
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
