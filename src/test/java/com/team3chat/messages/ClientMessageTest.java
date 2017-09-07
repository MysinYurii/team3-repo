package com.team3chat.messages;

import com.team3chat.client.ChatClient;
import com.team3chat.client.ChatClientRealisation;
import com.team3chat.client.parser.CommandParser;
import com.team3chat.client.sender.CommandSender;
import com.team3chat.client.ui.UserInputHandler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientMessageTest {
    private ChatClientRealisation client;
    @Mock
    private CommandParser commandParser;
    @Mock
    private CommandSender commandSender;
    private UserInputHandler userInputHandler;

    @Before
    public void setUp() {
        //Given
        String serverAddress = "127.0.0.1";
        /*int port = 1234;
        client = new ChatClientRealisation(serverAddress, port);*/
        initMocks(this);
    }

    @Ignore
    @Test
    public void shouldGetMessageWithDateAndTimeWhenSendMessage() {
        //Given
        //print("/snd message test");
        userInputHandler = new UserInputHandler(commandParser, commandSender);

        //Then
        verify(commandSender, times(1)).send(new SendMessageCommand("message test"));
    }

    @Ignore
    @Test
    public void shouldGetHistoryWhenPrintHistoryCommand() {
        //Given
        //print("/hist");
        userInputHandler = new UserInputHandler(commandParser, commandSender);

        //Then
        verify(commandSender, times(1)).send(new ShowHistoryCommand());
    }
}