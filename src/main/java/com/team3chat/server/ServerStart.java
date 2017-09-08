package com.team3chat.server;

import java.io.IOException;

/**
 * Created by Java_12 on 08.09.2017.
 */
public class ServerStart {
    public static void main(String[] args) {
        try (Server server = new Server()) {
            server.start();
        } catch (IOException e) {
            System.out.println("Server failed to start " + e.getMessage());
        }
    }
}
