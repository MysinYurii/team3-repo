package com.team3chat.server;

/**
 * Created by Java_12 on 07.09.2017.
 */

import java.io.*;
import java.net.Socket;


public class Acceptor implements Runnable {

    private Socket clientSocket;
    private BusinessLogic logicApplier;

    public Acceptor(Socket clientSocket, BusinessLogic logicApplier) {
        this.clientSocket = clientSocket;
        this.logicApplier = logicApplier;
    }

    @Override
    public void run() {
        try (
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        ) {
            System.out.println("new connection accepted");
            while (true) {
                String string;
                try {
                    string = input.readUTF();
                    System.out.println(string);
                } catch (IOException ignored) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

}
