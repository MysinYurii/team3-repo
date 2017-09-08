package com.team3chat.server;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
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
        String result = historyDealer.saveHistoryService(new BufferedWriter(new NullWriter()),message);
        Pattern pattern = Pattern.compile(".*" + message);
        Matcher matcher = pattern.matcher(result);
        assertTrue(matcher.matches());
    }

    private class NullWriter extends Writer {
        @Override
        public void write(@NotNull char[] cbuf, int off, int len) throws IOException {

        }

        @Override
        public void flush() throws IOException {

        }

        @Override
        public void close() throws IOException {

        }
    }
}

