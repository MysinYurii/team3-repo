package com.team3chat.client;

import com.team3chat.client.parser.CommandParser;
import com.team3chat.client.parser.DisconnectCommandMatcher;
import com.team3chat.client.parser.SendMessageCommandMatcher;
import com.team3chat.client.parser.ShowHistoryCommandMatcher;
import com.team3chat.client.sender.CommandSender;
import com.team3chat.client.ui.UserInputHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.util.Arrays.asList;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class ClientStartup {
    public static void main(String[] args) {
        CommandParser commandParser = new CommandParser(asList(
                new SendMessageCommandMatcher(),
                new ShowHistoryCommandMatcher(),
                new DisconnectCommandMatcher()));
        Thread userInputListeningThread = new Thread(() -> {
            try (Socket socket = new Socket("127.0.0.1", 12345)) {
                CommandSender commandsSender = new CommandSender(socket);
                UserInputHandler inputHandler = new UserInputHandler(commandParser, commandsSender);
                inputHandler.start();
            } catch (UnknownHostException e) {
                System.out.println("Client is pointed to non-existant server");
            } catch (IOException e) {
                System.out.println("Exception during connection to server occured");
            }
        });
        userInputListeningThread.start();
        Thread serverListeningThread = new Thread(() -> {

        });
        serverListeningThread.start();
    }
}
