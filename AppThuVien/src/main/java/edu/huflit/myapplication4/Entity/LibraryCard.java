package edu.huflit.myapplication4.Entity;

public class LibraryCard {
    String id;
    String name;
    String expirationDate;
    Boolean useStatus;
    Boolean borrowStatus;
    String dateBorrow;

    public LibraryCard()
    {
        this.id ="";
        this.name ="";
        this.expirationDate ="";
        this.useStatus =false;
        this.borrowStatus =false;
    }
    public LibraryCard(String id, String name, String expirationDate, Boolean useStatus, Boolean borrowStatus, String dateBorrow) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
        this.useStatus = useStatus;
        this.borrowStatus = borrowStatus;
        this.dateBorrow = dateBorrow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Boolean useStatus) {
        this.useStatus = useStatus;
    }

    public Boolean getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Boolean borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(String dateBorrow) {
        this.dateBorrow = dateBorrow;
    }
}
