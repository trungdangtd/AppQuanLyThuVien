package edu.huflit.myapplication4.Singleton;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.huflit.myapplication4.Entity.Book;

public class LoggingManager {
    private static LoggingManager instance;

    private LoggingManager() {
        // private constructor to prevent instantiation
    }

    public static LoggingManager getInstance() {
        if (instance == null) {
            instance = new LoggingManager();
        }
        return instance;
    }

    public void logBookView(Book book) {
        // Ghi log khi một cuốn sách được xem
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timestamp = formatter.format(date);
        System.out.println("[" + timestamp + "] Book viewed: " + book.getTitle());

    }
}
