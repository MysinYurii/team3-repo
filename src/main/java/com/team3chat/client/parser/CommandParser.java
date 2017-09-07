package com.team3chat.client.parser;

import com.team3chat.messages.Command;
import com.team3chat.messages.ConnectCommand;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class CommandParser {

    public Command parse(String userInput) {
        return new ConnectCommand();
    }

}
