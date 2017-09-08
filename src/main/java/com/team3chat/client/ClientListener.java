package com.team3chat.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListener implements Runnable {
    private BufferedReader bufferedReader;

    public ClientListener(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                String serverString = bufferedReader.readLine();
                while (serverString != null) {
                    System.out.println(serverString);
                    serverString = bufferedReader.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Server disconnected.");
            Thread.currentThread().interrupt();
        }
    }

    public void interrupt() {
        Thread.currentThread().interrupt();
    }

    public boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }
}
