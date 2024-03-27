package edu.huflit.myapplication4.Composite;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Nofication;
import edu.huflit.myapplication4.MainActivity;

public class CardExpirationCheck implements LibraryComponent {

    @Override
    public void performTask() {
        if (MainActivity.instance.isLogin && !TextUtils.isEmpty(BookstoreProjectDatabase.libraryCard.getId())) {
            if (BookstoreProjectDatabase.libraryCard.getUseStatus()) {
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                String expirationDate = BookstoreProjectDatabase.libraryCard.getExpirationDate();
                if (expirationDate.contains("/")) {
                    String[] currentDateOfCart = expirationDate.split("/");
                    String[] formattedDateSplited = formattedDate.split("/");


                    if (Integer.valueOf(currentDateOfCart[2]) < Integer.valueOf(formattedDateSplited[2])) {
                        BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, false);
                        BookstoreProjectDatabase.AddNofication(new Nofication("Cấm", formattedDate, "Hạn sử dụng thẻ đã vượt quá thời hạn sử dụng được", BookstoreProjectDatabase.libraryCard.getId()));
                        BookstoreProjectDatabase.libraryCard.setUseStatus(false);
                    } else if (Integer.valueOf(currentDateOfCart[1]) < Integer.valueOf(formattedDateSplited[1])) {
                        BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, false);
                        BookstoreProjectDatabase.AddNofication(new Nofication("Cấm", formattedDate, "Hạn sử dụng thẻ đã vượt quá thời hạn sử dụng được", BookstoreProjectDatabase.libraryCard.getId()));
                        BookstoreProjectDatabase.libraryCard.setUseStatus(false);
                    } else if (Integer.valueOf(currentDateOfCart[0]) < Integer.valueOf(formattedDateSplited[0])) {
                        BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, false);
                        BookstoreProjectDatabase.AddNofication(new Nofication("Cấm", formattedDate, "Hạn sử dụng thẻ đã vượt quá thời hạn sử dụng được", BookstoreProjectDatabase.libraryCard.getId()));
                        BookstoreProjectDatabase.libraryCard.setUseStatus(false);
                    }
                }
            }
        }
    }

    @Override
    public void addComponent(LibraryComponent component) {

    }
}