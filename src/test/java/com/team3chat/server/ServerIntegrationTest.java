package com.team3chat.server;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

/**
 * Created by Java_9 on 08.09.2017.
 */
public class ServerIntegrationTest {

    @Before
    public void setUp() {
        new Thread(Server::new).start();
    }

    @Test(timeout = 100_000)
    public void shouldSendMessageAndAcceptIt() throws Exception {
        sendMessageAndRecieveIt();
    }

    private void sendMessageAndRecieveIt() {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 6666);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
        ) {
            output.println(Thread.currentThread().getName());
            output.println("/snd message");
            output.flush();
            String acceptedString = input.readLine();
            assertThat(acceptedString, containsString(Thread.currentThread().getName()));
            assertThat(acceptedString, containsString("message"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldChangeName() {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 6666);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
        ) {
            output.println(Thread.currentThread().getName());
            output.println("/chid my new id");
            output.flush();
            String changedNotification = input.readLine();
            assertThat(changedNotification, containsString("my new id"));
            output.println("/snd message");
            output.flush();
            String acceptedMessage = input.readLine();
            assertThat(acceptedMessage, containsString("message"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldDisconnectCorrectly() {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 6666);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
        ) {
            output.println(Thread.currentThread().getName());
            output.println("/exit");
            output.flush();
            assertThat(socket.getInputStream().read(), is(-1));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}
