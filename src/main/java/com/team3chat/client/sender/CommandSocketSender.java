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
    private Socket socket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;

    public CommandSocketSender(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new PrintWriter(socket.getOutputStream(), true);
        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void print(Command command) {
        outputStream.println(command.getRepresentation() + System.lineSeparator() + System.lineSeparator());
        outputStream.flush();
    }

    @Override
    public Command readCommand() {
        while (true) {
            try {
                String newMessage = inputStream.readLine();
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
