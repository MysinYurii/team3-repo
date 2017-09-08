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
        while (!Thread.interrupted()) {
            try {
                String serverString = bufferedReader.readLine();
                while (serverString != null) {
                    System.out.println(serverString);
                    serverString = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
