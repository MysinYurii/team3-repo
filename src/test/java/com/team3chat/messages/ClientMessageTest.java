package com.team3chat.messages;

import com.team3chat.client.ChatClient;
import com.team3chat.client.ChatClientRealisation;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientMessageTest {
    private ChatClientRealisation client;

    @Before
    public void setUp() {
        //Given
        String serverAddress = "127.0.0.1";
        int port = 1234;
        client = new ChatClientRealisation(serverAddress, port);
        initMocks(this);
    }

    @Ignore
    @Test
    public void shouldGetMessageWithDateAndTimeWhenSendMessage() {
        //When
        client.message("/snd message test");

        //Then
        verify(client, times(1)).sendToServer(new SendMessageCommand("message test"));
    }

    @Ignore
    @Test
    public void shouldGetHistoryWhenPrintHistoryCommand() {
        //When
        client.message("/hist");

        //Then
        verify(client, times(1)).sendToServer(new ShowHistoryCommand());
    }
}