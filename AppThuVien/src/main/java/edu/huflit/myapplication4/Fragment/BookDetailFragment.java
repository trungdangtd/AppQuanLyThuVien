package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.huflit.myapplication4.Adapter.BookAdapter;
import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailFragment extends Fragment implements TextWatcher {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Book book;

    public BookDetailFragment() {
        // Required empty public constructor
    }
    public BookDetailFragment(Book book) {
        // Required empty public constructor
        this.book = book;
        BookstoreProjectDatabase.LoadBooksWithGenre(book.getGenre());
        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetailFragment newInstance(String param1, String param2) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

    ImageView backBtn, homeBtn, cartBtn;
    AutoCompleteTextView searchBar_ACTV;
    ImageView bookImage;
    TextView bookTitle;
    RecyclerView bookList;
    TextView watchMoreBtn;
    TextView bookId, bookAuthor, bookGenre, bookYearPublished, bookPublisher, bookContent, watchMoreContent;
    TextView borrowBookBtn;
    ArrayList<Book> books;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        books = new ArrayList<>();
        GetIDPalletes(view);
        SetPalletes(view);

        LoadBookList();

    }

    // Gọi các pallete có trong layout
    void GetIDPalletes(View view)
    {
        backBtn =view.findViewById(R.id.backBtn);
        homeBtn =view.findViewById(R.id.backHome);
        cartBtn =view.findViewById(R.id.backCart);
        searchBar_ACTV = (AutoCompleteTextView)view.findViewById(R.id.myautocomplete);
        bookList = view.findViewById(R.id.BookList);
        watchMoreBtn = view.findViewById(R.id.WatchMore);

        bookImage = view.findViewById(R.id.BookImage);
        bookTitle = view.findViewById(R.id.BookTitle);

        bookId = view.findViewById(R.id.BookId);
        bookAuthor = view.findViewById(R.id.BookAuthor);
        bookGenre = view.findViewById(R.id.BookGenre);
        bookYearPublished = view.findViewById(R.id.BookYearPublished);
        bookPublisher = view.findViewById(R.id.BookPublisher);
        bookContent = view.findViewById(R.id.BookContent);
        watchMoreContent = view.findViewById(R.id.WatchMoreContent);

        borrowBookBtn = view.findViewById(R.id.BorrowBookBtn);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        backBtn.setOnClickListener(v -> BackToPage());
        homeBtn.setOnClickListener(v -> BackToHome());
        cartBtn.setOnClickListener(v -> BackToCart());
        watchMoreBtn.setOnClickListener(v -> WatchMoreBtn());
        SearchBar();

        if(!book.getUrlImage().equals("Unknown"))
        {
            Glide.with(view) // In your activity or fragment, initialize Glide with the context
                    .load(book.getUrlImage()) // Use the load() method to specify the image URL
                    .into(bookImage); // Use the into() method to specify the ImageView to load the image into
        }
        else
        {
            bookImage.setImageResource(R.drawable.unknownbook);
        }
        bookTitle.setText(book.getTitle());
        bookId.setText(book.getId());
        bookAuthor.setText(book.getAuthor());
        bookGenre.setText(book.getGenre());
        bookYearPublished.setText(book.getYearPublished());
        bookPublisher.setText(book.getPublisher());
        bookContent.setText(book.getContent());

        watchMoreContent.setOnClickListener(v -> ShowMoreContent());

        if(MainActivity.instance.isLogin && BookstoreProjectDatabase.accountInfo.getRole().equals("Sinh viên"))
            borrowBookBtn.setOnClickListener(v -> BorrowBtn(view));
    }
    void BorrowBtn(View view)
    {
        if(MainActivity.instance.bookCart.size() > MainActivity.maxAmount)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Số sách có thể mượn trong 1 lần là 3 cuốn sách! - Số sách hiện tại tron giỏ hàng: " + MainActivity.instance.amount, Toast.LENGTH_LONG).show();
        }
        else {
            MainActivity.instance.bookCart.add(book);
            MainActivity.instance.currentFragment = new CartFragment();
            MainActivity.instance.ReplaceFragment(-1);
        }
    }

    Boolean isPress = false;
    void ShowMoreContent()
    {
        if(!isPress) {
            watchMoreContent.setText("Thu gọn");
            bookContent.setMaxLines(100);
            isPress = true;
        }
        else
        {
            watchMoreContent.setText("Xem thêm");
            bookContent.setMaxLines(7);
            isPress = false;
        }
    }

    void BackToHome()
    {
        MainActivity.instance.currentFragment = new HomePageFragment();
        MainActivity.instance.ReplaceFragment(-1);
    }

    void BackToCart()
    {
        MainActivity.instance.currentFragment = new CartFragment();
        MainActivity.instance.ReplaceFragment(-1);
    }
    void BackToPage()
    {
        getFragmentManager().popBackStack();
    }
    void LoadBookList()
    {
        ArrayList<Book> randomBooks = new ArrayList();
        if(BookstoreProjectDatabase.books.size() > 10) {
            for (int i = 0; i < 10; i++)
                randomBooks.add(BookstoreProjectDatabase.books.get(i));
        }
        else
        {
            for (int i = 0; i < BookstoreProjectDatabase.books.size(); i++)
                randomBooks.add(BookstoreProjectDatabase.books.get(i));
        }
        bookList.setLayoutManager(new LinearLayoutManager(MainActivity.instance, RecyclerView.HORIZONTAL, false));
        bookList.setAdapter(new BookAdapter(getActivity().getApplicationContext(), randomBooks));
    }

    void WatchMoreBtn()
    {
        MainActivity.instance.currentFragment = new BookListFragment("", bookGenre.getText().toString());
        MainActivity.instance.ReplaceFragment(-1);
    }
    void SearchBar()
    {
        searchBar_ACTV.addTextChangedListener(this);
        searchBar_ACTV.setAdapter(new ArrayAdapter<>(MainActivity.instance, android.R.layout.simple_dropdown_item_1line, BookstoreProjectDatabase.bookName));
        searchBar_ACTV.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                MainActivity.instance.currentFragment = new BookListFragment(searchBar_ACTV.getText().toString(), "");
                MainActivity.instance.ReplaceFragment(-1);
                searchBar_ACTV.setText("");
                return true;
            }
            return false;
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}