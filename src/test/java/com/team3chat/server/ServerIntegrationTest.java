package com.team3chat.server;

import org.junit.After;
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

    private Server server;

    @Before
    public void setUp() {
        new Thread(() -> {
            try {
                server = new Server();
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test(timeout = 10_000)
    public void shouldSendMessageAndAcceptIt() throws Exception {
        sendMessageAndRecieveIt();
    }

    private void sendMessageAndRecieveIt() {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 6667);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        ) {
            output.println(Thread.currentThread().getName());
            output.println("/snd message");
            String acceptedString = input.readLine();
            System.out.println(acceptedString);
            assertThat(acceptedString, containsString(Thread.currentThread().getName()));
            assertThat(acceptedString, containsString("message"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test(timeout = 10_000)
    public void shouldChangeName() {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 6667);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        ) {
            output.println(Thread.currentThread().getName());
            output.println("/chid my new id");
            String changedNotification = input.readLine();
            assertThat(changedNotification, containsString("my new id"));
            output.println("/snd message");
            String acceptedMessage = input.readLine();
            assertThat(acceptedMessage, containsString("message"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test(timeout = 10_000)
    public void shouldDisconnectCorrectly() {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 6667);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        ) {
            output.println(Thread.currentThread().getName());
            output.println("/exit");
            assertThat(socket.getInputStream().read(), is(-1));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws IOException {
        if (server != null) {
            server.stop();
        }
    }

}
