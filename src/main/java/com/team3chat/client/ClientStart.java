package com.team3chat.client;

import java.io.IOException;
import java.util.Scanner;

public class ClientStart {
    private ClientStart() {
    }

    public static void main(String[] args) {
        try {
            new Client().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String scan() {
        Scanner scanner = new Scanner(System.in);
        String scannedString = scanner.nextLine();
        return scannedString;
    }
}
