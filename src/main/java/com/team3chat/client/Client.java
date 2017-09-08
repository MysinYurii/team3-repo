package com.team3chat.client;

/**
 * Created by Java_12 on 08.09.2017.
 */

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
            clientSocket = new Socket("127.0.0.1", 6666);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            ClientListener listener = new ClientListener(in, false);
            new Thread(listener).start();

            String clientString = "";
            while (clientString.trim().length() == 0) {
                System.out.println("Enter name: ");
                clientString = ClientStart.scan();
            }
            System.out.println("Your name is: " + clientString);
            out.println(clientString);

            while (!clientString.equals("/exit")) {
                clientString = ClientStart.scan();
                primaryMessageHandling(clientString);
            }
            listener.setInterrupted();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void primaryMessageHandling(String clientString) {
        if (clientString.length() > 150) {
            System.out.println("Incorrect input format. Message should be shorter than 150 symbols.");
        } else if (clientString.startsWith("/snd") || clientString.startsWith("/chid") ||
                clientString.equals("/hist") || clientString.equals("/exit")) {
            out.println(clientString);
        } else {
            System.out.println("Incorrect input format. Message should start with " +
                    "\"/snd\" or \"/chid\" or be equal to \"/hist\" or \"/exit\"");
        }
    }

    private void close() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(">>>>>> unable to close thread");
        }
    }
}
