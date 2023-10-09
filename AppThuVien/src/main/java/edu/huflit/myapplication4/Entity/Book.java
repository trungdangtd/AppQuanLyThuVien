package edu.huflit.myapplication4.Entity;

public class Book {
    String id;
    String title;
    String author;
    String genre;
    String content;
    String yearPublished;
    String publisher;
    String urlImage;

    public Book(String id, String title, String author, String genre, String content, String yearPublished, String publisher, String urlImage) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.content = content;
        this.yearPublished = yearPublished;
        this.publisher = publisher;
        this.urlImage = urlImage;
    }

    public Book(String title, String author, String genre, String content, String yearPublished, String publisher, String urlImage) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.content = content;
        this.yearPublished = yearPublished;
        this.publisher = publisher;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
