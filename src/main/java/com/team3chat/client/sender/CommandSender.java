package com.team3chat.client.sender;

import com.team3chat.messages.Command;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class CommandSender {
    private Socket socket;

    public CommandSender(Socket socket) {
        this.socket = socket;
    }

    public void send(Command command) {
        try (ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            output.writeObject(command);
            output.flush();
        } catch (IOException e) {
            System.out.println("Error during message sending");
        }
    }
}
