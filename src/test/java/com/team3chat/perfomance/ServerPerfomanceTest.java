package com.team3chat.perfomance;

import com.team3chat.server.Server;
import org.junit.After;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ServerPerfomanceTest {
    private final double sSeconds = 0.2;
    private Server server;


    @Test(timeout = 100_000)
    public void responseShouldBeFasterWhen_s_SecondsWhenSendingSomething() throws Exception {
        new Thread(() -> {
            try {
                server = new Server();
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        List<Future<Double>> list = new LinkedList<>();
        int clientsNumber = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(clientsNumber);
        for (int i = 0; i < clientsNumber; ++i) {
            list.add(executorService.submit(() -> fireServer("absdhfbjg")));
        }
        Double reducedSum = list.stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                fail(e.getMessage());
                return 50_000.0;
            }
        }).reduce(0.0, (aDouble, aDouble2) -> aDouble2 + aDouble);
        System.out.println(reducedSum / clientsNumber);
        assertTrue(reducedSum / clientsNumber < sSeconds);
    }

    private double fireServer(String message) {
        double sum = 0.0;
        int count = 30;
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 6667);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                ) {
            output.println(Thread.currentThread().getName());
            for (int i = 0; i < count; i++) {
                output.println("/snd " + message);
                output.flush();
                long sentTimestamp = System.currentTimeMillis();
                String line = input.readLine();
                while (!line.contains(Thread.currentThread().getName())) {
                    line = input.readLine();
                }
                sum += (System.currentTimeMillis() - sentTimestamp) / 1000;
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return sum / count;
    }

    @After
    public void tearDown() throws IOException {
        if (server != null) {
            server.stop();
        }
    }

}