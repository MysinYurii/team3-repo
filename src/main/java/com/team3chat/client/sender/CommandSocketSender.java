package com.team3chat.client.sender;

import com.team3chat.client.ui.CommandPrinter;
import com.team3chat.client.ui.CommandReader;
import com.team3chat.messages.Command;
import com.team3chat.messages.SendMessageCommand;

import java.io.*;
import java.net.Socket;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class CommandSocketSender implements CommandPrinter, CommandReader {
    private Socket socket;

    public CommandSocketSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void print(Command command) {
        try (ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            output.writeObject(command);
            output.flush();
        } catch (IOException e) {
            System.out.println("Error during message sending");
        }
    }

    @Override
    public Command readCommand() {
        while (true) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))) {
                String newMessage = inputStream.readUTF();
                return new SendMessageCommand(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
