package com.team3chat.client.ui;

import com.team3chat.messages.Command;
import com.team3chat.messages.DisconnectCommand;

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
            insertedCommand = commandReader.readCommand();
            commandPrinter.print(insertedCommand);
        } while (!(insertedCommand instanceof DisconnectCommand));
    }

}