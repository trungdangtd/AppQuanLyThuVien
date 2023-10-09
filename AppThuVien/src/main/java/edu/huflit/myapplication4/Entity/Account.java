package edu.huflit.myapplication4.Entity;

public class Account {
    private String account;
    private String password;
    private String role;

    public Account()
    {
        this.account = "";
        this.password = "";
        this.role = "";
    }

    public Account(String account, String password, String role) {
        this.account = account;
        this.password = password;
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
