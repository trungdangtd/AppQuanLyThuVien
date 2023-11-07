package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.huflit.myapplication4.Adapter.BookAdapter;
import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuggestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Book> bookSuggests;
    public SuggestFragment() {

        bookSuggests = new ArrayList<>();
        bookSuggests = BookstoreProjectDatabase.GetBooks();

        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestFragment newInstance(String param1, String param2) {
        SuggestFragment fragment = new SuggestFragment();
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
        return inflater.inflate(R.layout.fragment_suggest, container, false);
    }
    RecyclerView bookListRV;
    TextView nofiMessage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetIDPalletes(view);
        SetPalletes(view);
        LoadBookList();

        nofiMessage.setEnabled(false);
        if(bookSuggests.size() == 0) {
            nofiMessage.setVisibility(View.VISIBLE);
        }
        else
            nofiMessage.setVisibility(View.INVISIBLE);
    }

    void GetIDPalletes(View view)
    {
        bookListRV = view.findViewById(R.id.BookList);
        nofiMessage = view.findViewById(R.id.message);
    }

    void SetPalletes(View view)
    {
    }

    void LoadBookList()
    {
        BookstoreProjectDatabase.LoadBooksSortedWithCopies();
        ArrayList<Book> randomTopReadBooks = new ArrayList<>();
        for(int i = 0; i < 5; i++)
            randomTopReadBooks.add(BookstoreProjectDatabase.booksAfterSorted.get(i));

        bookListRV.setLayoutManager(new GridLayoutManager(MainActivity.instance, 2));
        bookListRV.setAdapter(new BookAdapter(getActivity().getApplicationContext(), randomTopReadBooks));
    }
}