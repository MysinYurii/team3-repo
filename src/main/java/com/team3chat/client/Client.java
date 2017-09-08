package com.team3chat.client;

/**
 * Created by Java_12 on 08.09.2017.
 */

import java.io.*;
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

                out.println(clientString);
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
