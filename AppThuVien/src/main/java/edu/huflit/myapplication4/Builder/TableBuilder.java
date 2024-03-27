package edu.huflit.myapplication4.Builder;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import edu.huflit.myapplication4.R;

public class TableBuilder implements TableBuilderInterface{
    private Context context;
    private TableLayout tableLayout;

    public TableBuilder(Context context, TableLayout tableLayout) {
        this.context = context;
        this.tableLayout = tableLayout;
    }


    @Override
    public TextView createTextView(String text, int gravity) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setBackgroundResource(R.drawable.cotlibrarycard);
        textView.setGravity(gravity);
        return textView;
    }

    @Override
    public void addRow(ArrayList<TextView> textViews) {
        TableRow row = new TableRow(context);
        for (TextView textView : textViews) {
            row.addView(textView);
        }
        tableLayout.addView(row);
    }
}
