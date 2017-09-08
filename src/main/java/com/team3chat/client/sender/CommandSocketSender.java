package com.team3chat.client.sender;

import com.team3chat.client.parser.CommandParser;
import com.team3chat.client.parser.ParsingException;
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
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private CommandParser commandParser;

    public CommandSocketSender(Socket socket, CommandParser commandParser) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.commandParser = commandParser;
    }

    @Override
    public Command readCommand() throws IOException {
        while (true) {
            try {
                return commandParser.parse(dataInputStream.readUTF());
            } catch (ParsingException ignored) {
            }
        }
    }

    @Override
    public void print(Command command) throws IOException {
        dataOutputStream.writeUTF(command.getSerializedMessage());
        dataOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
