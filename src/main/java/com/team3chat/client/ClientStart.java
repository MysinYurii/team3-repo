package com.team3chat.client;

/**
 * Created by Java_12 on 08.09.2017.
 */

import java.io.IOException;

public class ClientStart {
    public static void main(String[] args) {
        try (
            Client client = new Client();
        ) {
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
