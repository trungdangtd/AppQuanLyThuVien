package edu.huflit.myapplication4.Stagely;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Account;
import edu.huflit.myapplication4.Entity.LibraryCard;

public class StudentRegistrationStrategy implements RoleStrategy{
    @Override
    public void register(String account, String name, String role) {
        // Thực hiện logic đăng ký tài khoản cho Sinh viên
        Calendar currentCal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        currentCal.add(Calendar.DATE, 1095);
        String toDate = dateFormat.format(currentCal.getTime());

        BookstoreProjectDatabase.AddAccount(new Account(account, account, "Sinh viên"));
        BookstoreProjectDatabase.AddLibraryCard(new LibraryCard(account, name, toDate, true, false));
    }
}
