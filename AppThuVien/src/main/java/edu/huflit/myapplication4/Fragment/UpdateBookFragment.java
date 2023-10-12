package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Book book;

    public UpdateBookFragment(Book book) {
        this.book = book;
        BookstoreProjectDatabase.LoadGenre();
        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
    }
    public UpdateBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateBookFragment newInstance(String param1, String param2) {
        UpdateBookFragment fragment = new UpdateBookFragment();
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
        return inflater.inflate(R.layout.fragment_update_book, container, false);
    }

    EditText bookNameInput, bookAuthorInput, bookPublisherInput, bookContentInput, bookURLInput;
    Spinner genreInput, yearPublishedInput;
    ImageView backBtn;
    Button updateBookBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetIDPalletes(view);
        SetPalletes(view);
    }

    void GetIDPalletes(View view)
    {
        backBtn = view.findViewById(R.id.backBtn);

        yearPublishedInput = view.findViewById(R.id.bookYearPublishedInput);
        genreInput = view.findViewById(R.id.bookGenreInput);
        bookNameInput = view.findViewById(R.id.bookNameInput);
        bookAuthorInput = view.findViewById(R.id.bookAuthorInput);
        bookPublisherInput = view.findViewById(R.id.bookPublisherInput);
        bookContentInput = view.findViewById(R.id.bookContentInput);
        bookURLInput = view.findViewById(R.id.bookURLInput);

        updateBookBtn = view.findViewById(R.id.updatebutton);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        backBtn.setOnClickListener(v -> BackBtn());
        updateBookBtn.setOnClickListener(v -> UpdateBookBtn(view));
        GenreInput();
        YearPublishedInput();

        bookNameInput.setText(book.getTitle());
        bookAuthorInput.setText(book.getAuthor());
        bookPublisherInput.setText(book.getPublisher());
        bookContentInput.setText(book.getContent());
        bookURLInput.setText(book.getUrlImage());
        int index = 0;
        for(int i = 0; i < genreInput.getAdapter().getCount(); i++)
        {
            if(genreInput.getAdapter().getItem(i).equals(book.getGenre()))
            {
                index = i;
            }
        }
        genreInput.setSelection(index);

        index = 0;
        for(int i = 0; i < yearPublishedInput.getAdapter().getCount(); i++)
        {
            if(yearPublishedInput.getAdapter().getItem(i).equals(book.getYearPublished()))
            {
                index = i;
            }
        }
        yearPublishedInput.setSelection(index);
    }
    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }
    void UpdateBookBtn(View view)
    {
        if(TextUtils.isEmpty(bookNameInput.getText().toString())) {
            bookNameInput.setError("Không được để trống tên sách");
            return;
        }
        if(TextUtils.isEmpty(bookAuthorInput.getText().toString())) {
            bookAuthorInput.setError("Không được để trống tên tác giả");
            return;
        }
        if(TextUtils.isEmpty(bookPublisherInput.getText().toString())) {
            bookPublisherInput.setError("Không được để trống nhà xuất bản");
            return;
        }
        if(TextUtils.isEmpty(bookContentInput.getText().toString())) {
            bookContentInput.setError("Không được để trống nội dung tóm tắt sách");
            return;
        }
        if(TextUtils.isEmpty(bookURLInput.getText().toString())) {
            bookURLInput.setError("Không được để trống link ảnh của sách");
            return;
        }

        BookstoreProjectDatabase.UpdateBook(new Book(book.getId(),
                bookNameInput.getText().toString(),
                bookAuthorInput.getText().toString(),
                genreInput.getSelectedItem().toString(),
                bookContentInput.getText().toString(),
                yearPublishedInput.getSelectedItem().toString(),
                bookPublisherInput.getText().toString(),
                bookURLInput.getText().toString()
        ));
        getFragmentManager().popBackStack();
    }

    void YearPublishedInput()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        ArrayList<String> years = new ArrayList<>();
        for(int i = year - 10; i <= year; i++)
            years.add(String.valueOf(i));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.instance, android.R.layout.simple_spinner_dropdown_item, years);
        //set the spinners adapter to the previously created one.
        yearPublishedInput.setAdapter(adapter);
    }

    void GenreInput()
    {
        ArrayList<String> genreList = new ArrayList<>();
        for(int i = 0; i < BookstoreProjectDatabase.genres.size(); i++)
            genreList.add(BookstoreProjectDatabase.genres.get(i).getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.instance, android.R.layout.simple_spinner_dropdown_item, genreList);
        //set the spinners adapter to the previously created one.
        genreInput.setAdapter(adapter);
    }
}