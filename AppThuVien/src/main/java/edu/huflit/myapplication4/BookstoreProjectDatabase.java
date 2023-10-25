package edu.huflit.myapplication4;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.huflit.myapplication4.Entity.Account;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.Entity.Genre;
import edu.huflit.myapplication4.Entity.LibraryCard;
import edu.huflit.myapplication4.Entity.Loan;
import edu.huflit.myapplication4.Fragment.AccountFragment;
import edu.huflit.myapplication4.Fragment.ManageListFragment;

public class BookstoreProjectDatabase {
    private static final String BOOK = "Book"; // Tên bảng
    private static final String COPY = "Copy"; // Tên bảng
    private static final String LOAN = "Loan"; // Tên bảng
    private static final String LIBRARYCARD = "LibraryCard"; // Tên bảng
    private static final String GENRE = "Genre"; // Tên bảng
    private static final String ACCOUNT = "Account"; // Tên bảng
    public static ArrayList<Book> books; // sách
    public static ArrayList<Genre> genres; // sách
    public static ArrayList<String> bookName; // từ khóa gợi ý khi tìm kiếm
    public static ArrayList<Copy> copies; // bản sao của sách
    public static ArrayList<Account> accounts;
    public static ArrayList<String> accNames;
    public static ArrayList<LibraryCard> libraryCards;
    public static ArrayList<Loan> loans;
    public static Account accountInfo;
    public static LibraryCard libraryCard;
    @SuppressLint("StaticFieldLeak")
    static FirebaseFirestore database;
    static CollectionReference bookCollectionRef, copyCollectionRef, loanCollectionRef, libraryCardCollectionRef, genreCollectionRef, accountCollectionRef; // Các collection trong database Bookstore

    // Kết nối với database và bảng
    public static void ConnectToFirestoreDB()
    {
        database = FirebaseFirestore.getInstance();
        System.out.println("Connected to database successfully!");

        bookCollectionRef = database.collection(BOOK);
        System.out.println("Connected to Book table successfully!");

        copyCollectionRef = database.collection(COPY);
        System.out.println("Connected to Copy table successfully!");

        loanCollectionRef = database.collection(LOAN);
        System.out.println("Connect to Loan table successfully!");

        libraryCardCollectionRef = database.collection(LIBRARYCARD);
        System.out.println("Connected to LibraryCard table successfully!");

        genreCollectionRef = database.collection(GENRE);
        System.out.println("Connected to Genre table successfully!");

        accountCollectionRef = database.collection(ACCOUNT);
        System.out.println("Connected to Account table successfully!");
    }

    public static ArrayList<Book> booksAfterSorted;

    // Tải thể loại
    public static void LoadGenre()
    {
        genres = new ArrayList<>();
        Task<QuerySnapshot> genreIds = genreCollectionRef.get();
        while (true)
        {
            if(genreIds.isSuccessful()) {
                for (DocumentSnapshot genreId : genreIds.getResult())
                    genres.add(new Genre(genreId.getId(), genreId.getString("Name")));
                break;
            }
        }
    }

    public static ArrayList<Book> GetBooks() { return books; }
    public static ArrayList<Copy> GetCopies() { return copies; }

    public static ArrayList<String> bookIDs;

    // Tải sách
    public static void LoadBooks()
    {
        books = new ArrayList<>();
        bookName = new ArrayList<>();
        bookIDs = new ArrayList<>();
        String content = "";
        Task<QuerySnapshot> bookIds = bookCollectionRef.get();
        while(true) {
            if (bookIds.isSuccessful()) {
                for (DocumentSnapshot id : bookIds.getResult()) {
                    content = "";
                    for(String arCon : (List<String>)id.get("Content"))
                    {
                        content += arCon + "\n";
                    }
                    books.add(new Book(id.getId(),
                            id.getString("Name"),
                            id.getString("Author"),
                            id.getString("Genre"),
                            content,
                            id.getString("YearPublished"),
                            id.getString("Publisher"),
                            id.getString("URL")));
                    bookName.add(id.getString("Name"));
                    bookIDs.add(id.getId());
                    System.out.println("Book name " + id.getId() + " : " + id.getString("Name"));
                }
                break;
            }
        }
    }


    public static ArrayList<Copy> LoadCopiesWithBookId(@NonNull String bookId, @NonNull String status)
    {
        ArrayList<Copy> copyArrayList = new ArrayList<>();

        Task<QuerySnapshot> copyIds = copyCollectionRef.document(bookId).collection("BookCopy").get();
        while(true) {
            if (copyIds.isSuccessful()) {
                for (DocumentSnapshot id : copyIds.getResult()) {
                    if (id.getString("Status").equals(status)) {
                        copyArrayList.add(new Copy(id.getId(),
                                bookId,
                                id.getString("Status"),
                                id.getString("Notes")));
                    }
                    else if(TextUtils.isEmpty(status))
                    {
                        copyArrayList.add(new Copy(id.getId(),
                                bookId,
                                id.getString("Status"),
                                id.getString("Notes")));
                    }
                }
                return copyArrayList;
            }
        }
    }

    // Tải sách với bản sao mà sách có tên chứa ký tự tìm kiếm
    public static void SearchBook(@NonNull String name)
    {
        books = new ArrayList<>();
        String content = "";
        ArrayList<String> nameKey = new ArrayList<>();
        for (int i = 0 ; i < name.length(); i++) {
            nameKey.add(String.valueOf(name.indexOf(i)));
        }
        Task<QuerySnapshot> bookIds = bookCollectionRef.get();//"\uf8ff"
        while(true) {
            if (bookIds.isSuccessful()) {
                for (DocumentSnapshot id : bookIds.getResult()) {
                    if (id.getString("Name").toLowerCase().contains(name.toLowerCase())) {
                        content ="";
                        for (String arCon : (List<String>) id.get("Content")) {
                            content += arCon + "\n";
                        }
                        books.add(new Book(id.getId(),
                                id.getString("Name"),
                                id.getString("Author"),
                                id.getString("Genre"),
                                content,
                                id.getString("YearPublished"),
                                id.getString("Publisher"),
                                id.getString("URL")));
                        System.out.println("Book name " + id.getId() + " : " + id.getString("Name"));
                    }
                }
                break;
            }
        }

        copies = new ArrayList<>();

        for(Book book : books)
        {
            Task<QuerySnapshot> copyIds = copyCollectionRef.document(book.getId()).collection("BookCopy").get();
            while(true) {
                if (copyIds.isSuccessful()) {
                    for (DocumentSnapshot copy : copyIds.getResult()) {
                        copies.add(new Copy(copy.getId(),
                                book.getId(),
                                copy.getString("Status"),
                                copy.getString("Notes")));
                        System.out.println("Copy id " + copy.getId() + ", book id " + book.getId());
                    }
                    break;
                }
            }
        }
    }

    // Tải sách theo thể loại
    public static void LoadBooksWithGenre(@NonNull String genreName)
    {
        books = new ArrayList<>();

        String content = "";
        Task<QuerySnapshot> bookIds = bookCollectionRef.whereEqualTo("Genre", genreName).get();
        while(true) {
            if (bookIds.isSuccessful()) {
                for (DocumentSnapshot id : bookIds.getResult()) {
                    content = "";
                    for (String arCon : (List<String>) id.get("Content")) {
                        content += arCon + "\n";
                    }
                    books.add(new Book(id.getId(),
                            id.getString("Name"),
                            id.getString("Author"),
                            id.getString("Genre"),
                            content,
                            id.getString("YearPublished"),
                            id.getString("Publisher"),
                            id.getString("URL")));
                    System.out.println("Book name " + id.getId() + " : " + id.getString("Name"));
                }
                break;
            }
        }

        copies = new ArrayList<>();

        for(Book book : books)
        {
            Task<QuerySnapshot> copyIds = copyCollectionRef.document(book.getId()).collection("BookCopy").get();
            while(true) {
                if (copyIds.isSuccessful()) {
                    for (DocumentSnapshot copy : copyIds.getResult()) {
                        copies.add(new Copy(copy.getId(),
                                book.getId(),
                                copy.getString("Status"),
                                copy.getString("Notes")));
                        System.out.println("Copy id " + copy.getId() + ", book id " + book.getId());
                    }
                    break;
                }
            }
        }
    }

    // Login
    public static void SearchAccount(@NonNull String account, @NonNull String password, @NonNull View view)
    {
        accountInfo = new Account();
        Task<QuerySnapshot> accountName = accountCollectionRef.whereEqualTo("Account", account).get();
        while(true)
        {
            if(accountName.isSuccessful())
            {
                System.out.println("accountName.getResult().size(): " + accountName.getResult().size());
                if(accountName.getResult().size() > 0) {
                    for (DocumentSnapshot name : accountName.getResult()) {
                        if (name.getString("Password").equals(password) && !name.getBoolean("isLogin")) {
                            accountInfo = new Account(name.getString("Account"), name.getString("Password"), name.getString("Role"));
                            accountCollectionRef.document(account).update("isLogin", true);
                            System.out.println("accountInfo.getRole(): " + accountInfo.getRole());
                            break;
                        }
                    }
                    if(TextUtils.isEmpty(accountInfo.getAccount()))
                    {
                        Snackbar.make(view, "Tài khoản hoặc mật khẩu bị sai", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    break;
                }
                if(accountName.getResult().size() == 0) {
                    Snackbar.make(view, "Tài khoản hoặc mật khẩu bị sai", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                }
            }
        }
        if(!TextUtils.isEmpty(accountInfo.getRole())) {
            if(accountInfo.getRole().equals("Sinh viên")) {
                Task<QuerySnapshot> libraryCardInfo = libraryCardCollectionRef.whereEqualTo("Id", accountInfo.getAccount()).get();
                while (true) {
                    if (libraryCardInfo.isSuccessful()) {
                        for (DocumentSnapshot idCart : libraryCardInfo.getResult()) {
                            libraryCard = new LibraryCard(accountInfo.getAccount(),
                                    idCart.getString("Name"),
                                    idCart.getString("ExpirationDate"),
                                    idCart.getBoolean("Status"),
                                    idCart.getBoolean("Borrow"));
                        }
                        Snackbar.make(view, "Đăng nhập thành công", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        MainActivity.instance.currentFragment = new AccountFragment();
                        MainActivity.instance.ReplaceFragment(-1);
                        MainActivity.instance.isLogin = true;
                        System.out.println("chuyển trang " + accountInfo.getRole());
                        break;
                    }
                }
            }
            else if(accountInfo.getRole().equals("Quản lý")|| accountInfo.getRole().equals("Thủ kho") || accountInfo.getRole().equals("Thủ thư"))
            {
                MainActivity.instance.currentFragment = new ManageListFragment();
                MainActivity.instance.ReplaceFragment(-1);
                MainActivity.instance.isLogin = true;
                System.out.println("chuyển trang " + accountInfo.getRole());
            }
        }
    }

    // Tải Tài khoản - Manager
    public static void LoadAccounts(String roleName)
    {
        accNames = new ArrayList<>();
        accounts = new ArrayList<>();
        Task<QuerySnapshot> accountNames = null;
        if(BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý") || BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ thư"))
            accountNames = accountCollectionRef.whereEqualTo("Role", roleName).get();

        while(true)
        {
            if(accountNames.isSuccessful())
            {
                for (DocumentSnapshot accountName : accountNames.getResult()) {
                    if (!accountName.getString("Role").equals("Quản lý")||!accountName.getString("Role").equals("Thủ thư")) {
                        accNames.add(accountName.getString("Account"));
                        accounts.add(new Account(accountName.getString("Account"), accountName.getString("Password"), accountName.getString("Role")));
                        System.out.println(accountName.getString("Account"));
                    }
                }
                break;
            }
        }
    }



    // Thêm sách - Manager
    public static void AddBook(@NonNull Book book)
    {
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

    // cập nhập sách - Manager
    public static void UpdateBook(@NonNull Book book)
    {
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

    // xóa sách - Manager
    public static void DeleteBook(@NonNull String id)
    {
        bookCollectionRef.document(id).delete();
    }



    public static void DeleteBookCopy(@NonNull String BookId)
    {

        copyCollectionRef.document(BookId).delete();
    }

    // Thêm thẻ thư viện - Manager
    public static boolean AddLibraryCard(@NonNull LibraryCard libraryCard)
    {

        HashMap<String, Object> libraryCardData = new HashMap<>();

        libraryCardData.put("Borrow", libraryCard.getBorrowStatus());
        libraryCardData.put("ExpirationDate", libraryCard.getExpirationDate());
        libraryCardData.put("Id", libraryCard.getId());
        libraryCardData.put("Name", libraryCard.getName());
        libraryCardData.put("Status", libraryCard.getUseStatus());

        libraryCardCollectionRef.document(libraryCard.getId()).set(libraryCardData);
        return true;
    }
    // cập nhật thẻ thư viện
    public static void UpdateLibraryCard(@NonNull LibraryCard libraryCard, boolean borrowStatus, boolean useStatus)
    {
        libraryCardCollectionRef.document(libraryCard.getId()).update("Borrow", borrowStatus);
        libraryCardCollectionRef.document(libraryCard.getId()).update("Status", useStatus);
    }

    // cập nhật thẻ thư viện
    public static void UpdateLibraryCard(@NonNull LibraryCard libraryCard, boolean useStatus, String ExpirationDate)
    {
        libraryCardCollectionRef.document(libraryCard.getId()).update("ExpirationDate", ExpirationDate);
        libraryCardCollectionRef.document(libraryCard.getId()).update("Status", useStatus);
    }

    // xóa thẻ thư viện - Manager
    public static void DeleteLibraryCard(@NonNull String id)
    {
        libraryCardCollectionRef.document(id).delete();
    }

    // Thêm tài khoản - Manager
    public static boolean AddAccount(@NonNull Account account)
    {

        //  if(accountCollectionRef.whereEqualTo("Account", account.getAccount()))
        HashMap<String, Object> newAccount = new HashMap<>();
        newAccount.put("Account", account.getAccount());
        newAccount.put("Password", account.getPassword());
        newAccount.put("Role", account.getRole());
        newAccount.put("isLogin", false);

        accountCollectionRef.document(account.getAccount()).set(newAccount);
        return true;
    }
    // Cập nhật tài khoản - Manager
    public static boolean UpdateAccount(@NonNull String account, @NonNull String password) {
        Task<QuerySnapshot> accId = accountCollectionRef.whereEqualTo("Account", account).get();
        while (true)
        {
            if(accId.isSuccessful())
            {
                if (accId.getResult().size() != 0) {
                    accountCollectionRef.document(account).update("Password", password);
                    return true;
                }
                else
                    return false;
            }
        }
    }

    // Cập nhật tài khoản - Manager
    public static boolean UpdateAccount(@NonNull String account, @NonNull boolean isLogin) {
        Task<QuerySnapshot> accId = accountCollectionRef.whereEqualTo("Account", account).get();
        while (true)
        {
            if(accId.isSuccessful())
            {
                if (accId.getResult().size() != 0) {
                    accountCollectionRef.document(account).update("isLogin", isLogin);
                    return true;
                }
                else
                    return false;
            }
        }
    }

    // Xóa tài khoản - Manager
    public static void DeleteAccount(@NonNull String nameAccount)
    {
        accountCollectionRef.document(nameAccount).delete();
    }

}

class Tuple<X, Y> {
    public final X x;
    public final Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}

