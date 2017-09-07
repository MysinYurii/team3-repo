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
public class CommandSocketSender implements CommandPrinter, CommandReader, Closeable {
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public CommandSocketSender(Socket socket) throws IOException {
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.inputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void print(Command command) {
        try {
            outputStream.writeUTF(command.getRepresentation());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Command readCommand() {
        while (true) {
            try {
                String newMessage = inputStream.readUTF();
                return new SendMessageCommand(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
