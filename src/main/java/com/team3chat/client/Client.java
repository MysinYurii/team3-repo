package com.team3chat.client;

/**
 * Created by Java_12 on 08.09.2017.
 */

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Closeable {
    private Socket clientSocket;

    public Client() throws IOException {
        clientSocket = new Socket("127.0.0.1", 6666);
    }

    public void run() {
        try (
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            ClientListener listener = new ClientListener(input);
            Thread userInputListenerThread = new Thread(listener);
            userInputListenerThread.start();

            System.out.println("Enter name: ");
            String clientString = scan();
            System.out.println("Your name is: " + clientString);

            output.println(clientString);
            while (!"/exit".equals(clientString)) {
                clientString = scan();
                handleUserInput(output, clientString);
            }
            userInputListenerThread.interrupt();
        } catch (UnknownHostException e) {
            System.out.println("Server was not found");
        } catch (IOException e) {
            System.out.println("Failed to open connections with " + e.getMessage());
        }
    }

    private void handleUserInput(PrintWriter output, String clientString) {
        if (clientString.length() > 150) {
            System.out.println("incorrect input format. Message should be shorter than 150 symbols.");
        } else if (clientString.startsWith("/snd") ||
                clientString.startsWith("/chid") ||
                clientString.equals("/hist") ||
                clientString.equals("/exit")) {
            if (clientString.startsWith("/chid")) {
                System.out.println("Your name is changed to: " + clientString.substring(6));
            }
            output.println(clientString);
        } else {
            System.out.println("incorrect input format. Message should start with " +
                    "\"/snd\" or \"/chid\" or be equal to \"/hist\" or \"/exit\"");
        }
    }


    private static String scan() {
        Scanner scanner = new Scanner(System.in);
        String scannedString = scanner.nextLine();
        return scannedString;
    }

    @Override
    public void close() {
        try {
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(">>>>>> unable to close thread");
        }
    }
}
