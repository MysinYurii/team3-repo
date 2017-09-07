package com.team3chat.client.parser;

import com.sun.istack.internal.Nullable;
import com.team3chat.messages.ShowHistoryCommand;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class ShowHistoryCommandMatcher implements CommandMatcher<ShowHistoryCommand> {
    private static final String PREFIX = "/hist";

    @Override
    public @Nullable ShowHistoryCommand parseCommand(String commandPattern) {
        return commandPattern.startsWith(PREFIX) ? new ShowHistoryCommand() : null;
    }
}