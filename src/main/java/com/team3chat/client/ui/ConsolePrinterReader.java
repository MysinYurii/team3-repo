package com.team3chat.client.ui;

import com.team3chat.client.parser.CommandParser;
import com.team3chat.client.parser.ParsingException;
import com.team3chat.messages.Command;
import com.team3chat.messages.SendMessageCommand;

import java.io.*;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class ConsolePrinterReader implements CommandPrinter, CommandReader {
    private BufferedReader dataInputStream;
    private BufferedWriter dataOutputStream;
    private CommandParser commandParser;

    public ConsolePrinterReader(CommandParser commandParser) {
        this.commandParser = commandParser;
        this.dataInputStream = new BufferedReader(new InputStreamReader(System.in));
        this.dataOutputStream = new BufferedWriter(new OutputStreamWriter(System.out));
    }


    @Override
    public void print(Command command) {
        if (command instanceof SendMessageCommand) {
            try {
                dataOutputStream.write(((SendMessageCommand) command).getMessage());
            } catch (IOException e) {
                System.out.println("");
            }
        }
    }

    @Override
    public Command readCommand() {
        while (true) {
            try {
                return commandParser.parse(dataInputStream.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParsingException e) {
                e.printStackTrace();
            }
        }
    }
}
;