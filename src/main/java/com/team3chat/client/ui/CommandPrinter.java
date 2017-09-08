package com.team3chat.client.ui;

import com.team3chat.messages.Command;

import java.io.IOException;

/**
 * Created by Java_9 on 07.09.2017.
 */
public interface CommandPrinter {
    void print(Command command) throws IOException;
}
