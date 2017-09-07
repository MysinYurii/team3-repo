package com.team3chat.server;

/**
 * Created by Java_12 on 07.09.2017.
 */

import com.team3chat.messages.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ) {
            while (true) {
                Object command = input.readObject();
                logicApplier.receiveCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
