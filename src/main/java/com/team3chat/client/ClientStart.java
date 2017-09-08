package com.team3chat.client;

import java.util.Scanner;

public class ClientStart {
    public static void main(String[] args) {
        new Client();
    }

    public static String scan() {
        Scanner scanner = new Scanner(System.in);
        String scannedString = scanner.nextLine();
        return scannedString;
    }
}
