package edu.huflit.myapplication4.ObserverPattern;

import android.content.Context;
import android.widget.Toast;

public class BorrowObserver implements Observer {
    private Context context;
    private String message;

    public BorrowObserver(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    @Override
    public void update() {
        // Hiển thị thông báo sử dụng Toast
        Toast.makeText(context, "Notification: " + message, Toast.LENGTH_SHORT).show();
    }

}

