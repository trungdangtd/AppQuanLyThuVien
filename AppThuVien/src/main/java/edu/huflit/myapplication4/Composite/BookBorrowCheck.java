package edu.huflit.myapplication4.Composite;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Nofication;
import edu.huflit.myapplication4.MainActivity;

public class BookBorrowCheck implements LibraryComponent {
    @Override
    public void performTask() {
        if (MainActivity.instance.isLogin && !TextUtils.isEmpty(BookstoreProjectDatabase.libraryCard.getId())) {
            if (BookstoreProjectDatabase.libraryCard.getBorrowStatus()){
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                Calendar currentCal = new GregorianCalendar();
                Calendar currentCal1 = new GregorianCalendar();

                String[] splitBorrowDate = BookstoreProjectDatabase.libraryCard.getDateBorrow().split("/");
                currentCal1.setTime(new Date(Integer.valueOf(splitBorrowDate[2]) - 1900, Integer.valueOf(splitBorrowDate[1]) - 1, Integer.valueOf(splitBorrowDate[0])));

                System.out.println("currentCal => " + currentCal.getTime());
                System.out.println("currentCal1 => " + currentCal1.getTime());

                System.out.println("Days= " + daysBetween(currentCal1.getTime(), currentCal.getTime()));

                if (daysBetween(currentCal1.getTime(), currentCal.getTime()) >= 15) {
                    BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, false);
                    BookstoreProjectDatabase.AddNofication(new Nofication("Cấm", formattedDate, "Hạn sử dụng thẻ đã vượt quá thời hạn sử dụng được", BookstoreProjectDatabase.libraryCard.getId()));
                    BookstoreProjectDatabase.libraryCard.setUseStatus(false);
                }
            }
        }

    }

    @Override
    public void addComponent(LibraryComponent component) {

    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}

