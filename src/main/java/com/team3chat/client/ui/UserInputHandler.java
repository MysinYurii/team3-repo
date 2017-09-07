package com.team3chat.client.ui;

import com.team3chat.client.parser.CommandParser;
import com.team3chat.client.parser.ParsingException;
import com.team3chat.client.sender.CommandSender;
import com.team3chat.messages.Command;
import com.team3chat.messages.DisconnectCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class UserInputHandler {
    private CommandParser commandParser;
    private final CommandSender commandSender;
    private BufferedReader inputStream;

    public UserInputHandler(CommandParser commandParser, CommandSender commandSender) {
        this.commandParser = commandParser;
        this.commandSender = commandSender;
        this.inputStream = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        Command insertedCommand = null;
        do {
            try {
                String newLine = inputStream.readLine();
                insertedCommand = commandParser.parse(newLine);
                commandSender.send(insertedCommand);
            } catch (ParsingException e) {
                System.out.println("Invalid command inputted.");
            } catch (IOException e) {
                System.out.println("Problems with reading input occured.");
            }
        } while (!(insertedCommand instanceof DisconnectCommand));
    }

}