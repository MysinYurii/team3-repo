package com.team3chat.server;

/**
 * Created by Java_12 on 07.09.2017.
 */

import com.team3chat.exceptions.SavingHistoryException;

import java.io.DataOutputStream;
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
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        ) {
            while (true) {
                Object command = input.readObject();
                logicApplier.receiveCommand(command);
                out.writeUTF(logicApplier.getFormattedMessage());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SavingHistoryException e) {
            System.out.println("Saving History ");
        }
    }
}
