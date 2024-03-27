package edu.huflit.myapplication4.Builder;

import android.widget.TextView;

import java.util.ArrayList;

public interface TableBuilderInterface {
    TextView createTextView(String text, int gravity);
    void addRow(ArrayList<TextView> textViews);
}
