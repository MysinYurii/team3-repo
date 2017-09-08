package com.team3chat.client;

import com.team3chat.client.parser.*;
import com.team3chat.client.sender.CommandSocketSender;
import com.team3chat.client.ui.CommandPrinter;
import com.team3chat.client.ui.ConsolePrinterReader;
import com.team3chat.client.ui.UserInputHandler;
import com.team3chat.messages.Command;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.util.Arrays.asList;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class ClientStartup {
    private static CommandParser commandParser;
    private static ConsolePrinterReader consolePrinterReader;

    public static void main(String[] args) {
        commandParser = new CommandParser(asList(
                new SendMessageCommandMatcher(),
                new ShowHistoryCommandMatcher(),
                new DisconnectCommandMatcher()));
        consolePrinterReader = new ConsolePrinterReader(commandParser);
        try (Socket serverSocket = new Socket("127.0.0.1", 1234);
             CommandSocketSender commandSocketSender = new CommandSocketSender(serverSocket, commandParser);
        ) {
            Thread userListeningThread = startUserListeningThread(commandSocketSender);
            Thread socketListeningThread = startSocketListeningThread(consolePrinterReader, commandSocketSender);
            userListeningThread.join();
            socketListeningThread.join();
        } catch (UnknownHostException e) {
            System.out.println("Client is pointed to non-existant server");
        } catch (IOException e) {
            System.out.println("Exception during connection to server occured");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread startSocketListeningThread(CommandPrinter commandPrinter, CommandSocketSender commandSocketSender) {
        Thread serverListeningThread = new Thread(() -> {
            try {
                Command command = commandSocketSender.readCommand();
                commandPrinter.print(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverListeningThread.start();
        return serverListeningThread;
    }

    private static Thread startUserListeningThread(CommandSocketSender commandSocketSender) {
        Thread userInputListeningThread = new Thread(() -> {
            UserInputHandler inputHandler = new UserInputHandler(consolePrinterReader, commandSocketSender);
            inputHandler.start();
        });
        userInputListeningThread.start();
        return userInputListeningThread;
    }
}
