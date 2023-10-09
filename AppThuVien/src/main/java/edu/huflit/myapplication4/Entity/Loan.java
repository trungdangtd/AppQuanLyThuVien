package edu.huflit.myapplication4.Entity;

public class Loan {
    String bookId;
    String cardId;
    String copyId;
    String dateLoaned;
    String dateDue;

    public Loan(String bookId, String cardId, String copyId, String dateLoaned, String dateDue) {
        this.bookId = bookId;
        this.cardId = cardId;
        this.copyId = copyId;
        this.dateLoaned = dateLoaned;
        this.dateDue = dateDue;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public String getDateLoaned() {
        return dateLoaned;
    }

    public void setDateLoaned(String dateLoaned) {
        this.dateLoaned = dateLoaned;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }
}
