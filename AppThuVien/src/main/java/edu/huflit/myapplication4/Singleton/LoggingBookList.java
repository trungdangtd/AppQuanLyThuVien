package edu.huflit.myapplication4.Singleton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingBookList {
    private static LoggingBookList instance;

    private LoggingBookList() {
        // private constructor to prevent instantiation
    }

    public static LoggingBookList getInstance() {
        if (instance == null) {
            instance = new LoggingBookList();
        }
        return instance;
    }

    public void logBooksLoaded(int numberOfBooks) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timestamp = formatter.format(date);
        System.out.println("[" + timestamp + "] Loaded " + numberOfBooks + " books into the book list");
        // Các hoạt động khác sau khi ghi log
    }
}
