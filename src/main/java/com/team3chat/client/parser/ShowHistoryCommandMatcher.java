package com.team3chat.client.parser;

import com.team3chat.messages.ShowHistoryCommand;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class ShowHistoryCommandMatcher implements CommandMatcher<ShowHistoryCommand> {
    public static final String PREFIX = "/hist";

    @Override
    public ShowHistoryCommand parseCommand(String commandPattern) {
        return commandPattern.startsWith(PREFIX) ? new ShowHistoryCommand() : null;
    }
}
