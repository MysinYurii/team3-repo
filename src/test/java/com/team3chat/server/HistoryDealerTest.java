package com.team3chat.server;

import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class HistoryDealerTest {
    private HistoryDealer historyDealer;

    @Before
    public void setUp() {
        historyDealer = new HistoryDealer(null);
    }

    @Test
    public void shouldAddDateWhenSavingHistory() throws Exception {
        String message = "complex message";
        String result = historyDealer.formatMessage(message);
        Pattern pattern = Pattern.compile(".*" + message);
        Matcher matcher = pattern.matcher(result);
        assertTrue(matcher.matches());
    }
}

