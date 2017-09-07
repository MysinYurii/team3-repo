package com.team3chat.client.parser;

import com.team3chat.messages.SendMessageCommand;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class SendMessageCommandMatcher implements CommandMatcher<SendMessageCommand> {
    private static final String PREFIX = "/snd";

    @Override
    public @Nullable SendMessageCommand parseCommand(String commandPattern) {
        if (!commandPattern.startsWith(PREFIX)) {
            return null;
        }
        int beginIndex = PREFIX.length();
        int endIndex = commandPattern.length();
        String rawMessage = commandPattern.substring(beginIndex, endIndex);
        return new SendMessageCommand(rawMessage);
    }
}
