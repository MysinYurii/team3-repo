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
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String command;
            while (true) {
                while ((command = input.readLine()) != null) {
                    System.out.println(command);
                    System.out.flush();
                    logicApplier.receiveCommand(command);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

}
