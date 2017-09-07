package com.team3chat.client;

import com.team3chat.client.parser.CommandParser;
import com.team3chat.client.parser.DisconnectCommandMatcher;
import com.team3chat.client.parser.SendMessageCommandMatcher;
import com.team3chat.client.parser.ShowHistoryCommandMatcher;
import com.team3chat.client.sender.CommandSocketSender;
import com.team3chat.client.ui.CommandPrinter;
import com.team3chat.client.ui.ConsolePrinterReader;
import com.team3chat.client.ui.UserInputHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.util.Arrays.asList;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class ClientStartup {
    private static CommandParser commandParser;
    private static ConsolePrinterReader consolePrinterReader;
    private static CommandSocketSender staticCommandSocketSender;

    public static void main(String[] args) {
        commandParser = new CommandParser(asList(
                new SendMessageCommandMatcher(),
                new ShowHistoryCommandMatcher(),
                new DisconnectCommandMatcher()));
        consolePrinterReader = new ConsolePrinterReader(commandParser);
        try (Socket serverSocket = new Socket(InetAddress.getLocalHost(), 1234);
             CommandSocketSender commandSocketSender = new CommandSocketSender(serverSocket);
        ) {
            serverSocket.setKeepAlive(true);
            staticCommandSocketSender = commandSocketSender;
            Thread userListeningThread = startUserListeningThread();
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
//            Command command = commandSocketSender.readCommand();
//            commandPrinter.print(command);
//            System.out.println(Thread.currentThread().getName());
        });
        serverListeningThread.start();
        return serverListeningThread;
    }

    private static Thread startUserListeningThread() {
        Thread userInputListeningThread = new Thread(() -> {
            UserInputHandler inputHandler = new UserInputHandler(consolePrinterReader, staticCommandSocketSender);
            inputHandler.start();
            System.out.println(Thread.currentThread().getName());
        });
        userInputListeningThread.start();
        return userInputListeningThread;
    }


}
