package com.team3chat.client;

/**
 * Created by Java_12 on 08.09.2017.
 */

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
