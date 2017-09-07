package com.team3chat.server;

/**
 * Created by Java_12 on 07.09.2017.
 */

import com.team3chat.messages.Command;
import com.team3chat.messages.SendMessageCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ) {
            while (true) {
                Object command = input.readObject();
                if (command instanceof SendMessageCommand) {
                    logicApplier.applyCommand((SendMessageCommand) command);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
