package com.team3chat.server;

import java.io.*;

/**
 * Created by Java_12 on 07.09.2017.
 */
public class HistoryDealer {
    private File historyFile;

    public HistoryDealer(File historyFile) {
        this.historyFile = historyFile;
    }

    public void saveHistory(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(historyFile, true))) {
            bw.write(message);
            bw.newLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHistory() {
        try (
                RandomAccessFile far = new RandomAccessFile(historyFile, "rw");
        ) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream(historyFile)), "UTF-8"))) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
