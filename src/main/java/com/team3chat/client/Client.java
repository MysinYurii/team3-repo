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
            Socket clientSocket = new Socket("127.0.0.1", 6666);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            ClientListener listener = new ClientListener(in, false);
            new Thread(listener).start();

            System.out.println("Enter name: ");
            String clientString = ClientStart.scan();
            System.out.println("Your name is: " + clientString);

            out.println(clientString);
            while (!clientString.equals("/exit")) {
                clientString = ClientStart.scan();
                if (clientString.length() > 150) {
                    System.out.println("incorrect input format. Message should be shorter than 150 symbols.");
                } else if (clientString.startsWith("/snd") ||
                        clientString.startsWith("/chid") ||
                        clientString.equals("/hist") ||
                        clientString.equals("/exit")) {
                    if (clientString.startsWith("/chid")) {
                        System.out.println("Your name is changed to: " + clientString.substring(6));
                    }
                    out.println(clientString);
                } else {
                    System.out.println("incorrect input format. Message should start with " +
                            "\"/snd\" or \"/chid\" or be equal to \"/hist\" or \"/exit\"");
                }
            }
            listener.setInterrupted();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
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
