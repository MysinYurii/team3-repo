package com.team3chat.client.parser;

import com.team3chat.messages.Command;

import java.util.List;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class CommandParser {

    private List<CommandMatcher<? extends Command>> commandMatchers;

    public CommandParser(List<CommandMatcher<? extends Command>> commandMatchers) {
        this.commandMatchers = commandMatchers;
    }

    public Command parse(String userInput) throws ParsingException {
        for (CommandMatcher<? extends Command> matcher : commandMatchers) {
            Command parsedCommand = matcher.parseCommand(userInput);
            if (parsedCommand != null) {
                return parsedCommand;
            }
        }
        throw new ParsingException();
    }

}
