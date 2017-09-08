package com.team3chat.client;

import java.io.BufferedReader;
import java.io.IOException;
public class ClientListener implements Runnable {
    private BufferedReader bufferedReader;
    private boolean interrupted;

    public ClientListener(BufferedReader bufferedReader, boolean interrupted) {
        this.bufferedReader = bufferedReader;
        this.interrupted = interrupted;
    }

    public void setInterrupted() {
        interrupted = true;
    }

    @Override
    public void run() {
        while (!interrupted) {
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
