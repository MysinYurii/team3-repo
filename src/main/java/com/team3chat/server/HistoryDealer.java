package com.team3chat.server;

import java.io.*;
import java.util.Date;

/**
 * Created by Java_12 on 07.09.2017.
 */
public class HistoryDealer {
    private File historyFile;

    public HistoryDealer(File historyFile) {
        this.historyFile = historyFile;
    }

    public String saveHistory(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(historyFile, true))) {
            String formattedMessage = String.format("%s %s", (new Date()).toString(), message);
            bw.write(formattedMessage);
            bw.newLine();
            return formattedMessage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void readHistory() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                new FileInputStream(historyFile)), "UTF-8"))) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
