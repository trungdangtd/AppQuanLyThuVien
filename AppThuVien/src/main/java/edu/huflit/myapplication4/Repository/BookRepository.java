package edu.huflit.myapplication4.Repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import edu.huflit.myapplication4.Entity.Book;

public class BookRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference bookCollectionRef = db.collection("Book");

    public static void addBook(Book book) {
        ArrayList<String> contentParts = new ArrayList<>();
        String[] parts =  book.getContent().split("\n");

        for(String part : parts)
        {
            contentParts.add(part);
        }


        HashMap<String, Object> newBook = new HashMap<>();
        newBook.put("Author", book.getAuthor());
        newBook.put("Content", contentParts);
        newBook.put("Genre", book.getGenre());
        newBook.put("Id", book.getId());
        newBook.put("Name", book.getTitle());
        newBook.put("Publisher", book.getPublisher());
        newBook.put("URL", book.getUrlImage());
        newBook.put("YearPublished", book.getYearPublished());

        bookCollectionRef.document(book.getId()).set(newBook);
        System.out.println("Thêm sách thành công: " + newBook);
    }
    public static void updateBook(Book book) {
        ArrayList<String> contentParts = new ArrayList<>();
        String[] parts =  book.getContent().split("\n");

        for(String part : parts)
        {
            contentParts.add(part);
        }


        HashMap<String, Object> newBook = new HashMap<>();
        newBook.put("Author", book.getAuthor());
        newBook.put("Content", contentParts);
        newBook.put("Genre", book.getGenre());
        newBook.put("Name", book.getTitle());
        newBook.put("Publisher", book.getPublisher());
        newBook.put("URL", book.getUrlImage());
        newBook.put("YearPublished", book.getYearPublished());

        bookCollectionRef.document(book.getId()).update(newBook);
        System.out.println("Chỉnh sửa sách thành công: " + newBook);
    }
    public static void deleteBook(String bookId) {
        bookCollectionRef.document(bookId).delete();
    }
}
