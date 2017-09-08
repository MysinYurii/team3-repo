package com.team3chat.server;

import com.team3chat.exceptions.SavingHistoryException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Java_12 on 07.09.2017.
 */
public class HistoryDealer {
    private File historyFile;

    public HistoryDealer(File historyFile) {
        this.historyFile = historyFile;
    }

    public String formatMessage(String message) {
        return String.format("%s | %s", (new Date()).toString(), message);
    }

    public synchronized String saveHistory(String message) throws SavingHistoryException {
        try (BufferedWriter bufferedWriterToFile = new BufferedWriter(new FileWriter(historyFile, true))) {
            String formattedMessage = formatMessage(message);
            bufferedWriterToFile.write(formattedMessage);
            bufferedWriterToFile.newLine();
            return formattedMessage;
        } catch (FileNotFoundException e) {
            throw new SavingHistoryException("File not found while writing history", e);
        } catch (IOException e) {
            throw new SavingHistoryException("IOException while writing history", e);
        }
    }

    public synchronized ArrayList<String> readHistory() throws SavingHistoryException {
        ArrayList<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                new FileInputStream(historyFile)), "UTF-8"))) {
            String in;
            while ((in = reader.readLine()) != null) {
                history.add(in);
            }
        } catch (FileNotFoundException e) {
            throw new SavingHistoryException("File not found while reading history", e);
        } catch (IOException e) {
            throw new SavingHistoryException("IOException while reading history", e);
        }
        return history;
    }
}
