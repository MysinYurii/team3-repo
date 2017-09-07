package server;

import java.io.*;

public class HistoryReader {
    private File historyFile;

    public HistoryReader(File historyFile) {
        this.historyFile = historyFile;
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