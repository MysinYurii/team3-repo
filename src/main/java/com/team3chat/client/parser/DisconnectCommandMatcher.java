package com.team3chat.client.parser;

import com.team3chat.messages.DisconnectCommand;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class DisconnectCommandMatcher implements CommandMatcher<DisconnectCommand> {
    private static final String PREFIX = "/quit";

    @Nullable
    @Override
    public DisconnectCommand parseCommand(String commandPattern) {
        return commandPattern.startsWith(PREFIX) ? new DisconnectCommand() : null;
    }

}
