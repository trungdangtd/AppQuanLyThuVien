package edu.huflit.myapplication4.Entity;

public class Nofication {
    private String title;
    private String dateUpdate;
    private String content;
    public String idStudent;

    public Nofication(String title, String dateUpdate, String content, String idStudent) {
        this.title = title;
        this.dateUpdate = dateUpdate;
        this.content = content;
        this.idStudent = idStudent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }
}
