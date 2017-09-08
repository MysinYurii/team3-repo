package com.team3chat.server;

/**
 * Created by Java_12 on 07.09.2017.
 */

import com.team3chat.messages.Command;
import com.team3chat.exceptions.SavingHistoryException;
import com.team3chat.messages.Command;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {

    private Socket clientSocket;
    private BusinessLogic logicApplier;
    private Server server;

    public Handler(Server server, Socket clientSocket, BusinessLogic logicApplier) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.logicApplier = logicApplier;
    }

    @Override
    public void run() {
        try (
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        ) {
            while (true) {
                String command = input.readUTF();
                System.out.println(command);
                logicApplier.receiveCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SavingHistoryException e) {
            e.printStackTrace();
        }
        close();
    }

    private void close() {
        server.closeHandler(this);
    }

    public void sendAll(Command c) {
        server.sendAll(c);
    }

    public void send(Command c) {
    }
}
