package com.team3chat.client.parser;

import com.team3chat.messages.Command;
import com.team3chat.messages.SendMessageCommand;
import com.team3chat.messages.ShowHistoryCommand;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static java.util.Arrays.asList;

public class CommandParserTest {
    CommandParser commandParser ;
    @Before
    public void setUp() {
        commandParser = new CommandParser(asList(
                new SendMessageCommandMatcher(), new ShowHistoryCommandMatcher()));
    }

    @Test
    public void shouldParseToCommandWhenGivingSendCommandString() throws Exception {
        Command command = commandParser.parse("/snd some message");
        if (command instanceof SendMessageCommand) {
            SendMessageCommand other = (SendMessageCommand) command;
            assertTrue(other.getMessage().equals("some message"));
        }
        else {
            fail();
        }
    }

    @Test
    public void shouldParseToCommandWhenGivingHistoryCommandString() throws Exception {
        Command command = commandParser.parse("/hist");
        assertTrue(command instanceof ShowHistoryCommand);
    }
}