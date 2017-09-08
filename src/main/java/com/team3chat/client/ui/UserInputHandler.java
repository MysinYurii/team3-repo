package com.team3chat.client.ui;

import com.team3chat.messages.Command;
import com.team3chat.messages.DisconnectCommand;

import java.io.IOException;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class UserInputHandler {
    private final CommandReader commandReader;
    private final CommandPrinter commandPrinter;

    public UserInputHandler(CommandReader commandReader, CommandPrinter commandPrinter) {
        this.commandReader = commandReader;
        this.commandPrinter = commandPrinter;
    }

    public void start() {
        Command insertedCommand = null;
        do {
            try {
                insertedCommand = commandReader.readCommand();
                commandPrinter.print(insertedCommand);
            } catch (IOException ignored) {
            }
        } while (!(insertedCommand instanceof DisconnectCommand));
    }

}