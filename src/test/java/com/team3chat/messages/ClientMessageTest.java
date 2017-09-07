package com.team3chat.messages;

import com.team3chat.client.ChatClient;
import com.team3chat.client.ChatClientRealisation;
import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.times;

@Ignore
public class ClientMessageTest {

    @Test
    public void shouldGetMessageWithDateAndTimeWhenSendMessage() {
        //Given
        String serverAddress = "127.0.0.1";
        int port = 1234;
        ChatClient client = new ChatClientRealisation(serverAddress, port);
        client.message("/snd message test");
        verify(client, times(1)).lastMessage("time message test");
    }
}