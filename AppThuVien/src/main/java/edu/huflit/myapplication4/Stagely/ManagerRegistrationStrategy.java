package edu.huflit.myapplication4.Stagely;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Account;

public class ManagerRegistrationStrategy implements RoleStrategy{
    @Override
    public void register(String account, String name, String role) {
        BookstoreProjectDatabase.AddAccount(new Account(account, account, role));
    }
}
