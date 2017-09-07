package com.team3chat.client;

import com.team3chat.client.parser.CommandParser;
import com.team3chat.client.parser.DisconnectCommandMatcher;
import com.team3chat.client.parser.SendMessageCommandMatcher;
import com.team3chat.client.parser.ShowHistoryCommandMatcher;
import com.team3chat.client.sender.CommandSocketSender;
import com.team3chat.client.ui.CommandPrinter;
import com.team3chat.client.ui.CommandReader;
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
        try (Socket serverSocket = new Socket("127.0.0.1", 1234)) {
            Thread userListeningThread = startUserListeningThread(serverSocket);
            Thread socketListeningThread = startSocketListeningThread(serverSocket, consolePrinterReader);
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

    private static Thread startSocketListeningThread(Socket socket, CommandPrinter commandPrinter) {
        Thread serverListeningThread = new Thread(() -> {
            CommandReader reader = new CommandSocketSender(socket);
            Command command = reader.readCommand();
            commandPrinter.print(command);
        });
        serverListeningThread.start();
        return serverListeningThread;
    }

    private static Thread startUserListeningThread(Socket socket) {
        Thread userInputListeningThread = new Thread(() -> {
            CommandSocketSender commandSocketSender = new CommandSocketSender(socket);
            UserInputHandler inputHandler = new UserInputHandler(consolePrinterReader, commandSocketSender);
            inputHandler.start();
        });
        userInputListeningThread.start();
        return userInputListeningThread;
    }


}
