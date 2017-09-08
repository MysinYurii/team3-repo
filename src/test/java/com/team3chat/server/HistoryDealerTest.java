package com.team3chat.server;

import com.team3chat.exceptions.SavingHistoryException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HistoryDealerTest {
    private HistoryDealer historyDealer;
    private File file;

    @Before
    public void setUp() throws IOException {
        file = new File("testfile.txt");
        file.createNewFile();
        historyDealer = new HistoryDealer(file);
    }

    @Test
    public void shouldAddDateWhenSavingHistory() throws Exception {
        String message = "complex message";
        String result = historyDealer.formatMessage(message);
        Pattern pattern = Pattern.compile(".*" + message);
        Matcher matcher = pattern.matcher(result);
        assertTrue(matcher.matches());
    }

    @Test
    public void shouldReturnLines() throws IOException, SavingHistoryException {
        List<String> lines = asList("line1", "line2");
        Files.write(file.toPath(), lines, StandardOpenOption.WRITE);
        List<String> readLines = historyDealer.readHistory();
        assertEquals(lines, readLines);
    }

    @Test(expected = SavingHistoryException.class)
    public void shouldThrowExceptionWhenFileIsNotFound() throws SavingHistoryException {
        file.delete();
        historyDealer.readHistory();
    }


    @After
    public void tearDown() {
        file.delete();
    }
}

