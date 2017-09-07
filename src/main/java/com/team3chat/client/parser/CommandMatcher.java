package com.team3chat.client.parser;

import com.team3chat.messages.Command;

/**
 * Created by Java_9 on 07.09.2017.
 */
public interface CommandMatcher<T extends Command> {
    T parseCommand(String commandPattern);
}
