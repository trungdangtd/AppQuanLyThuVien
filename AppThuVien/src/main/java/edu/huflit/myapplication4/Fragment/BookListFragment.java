    package edu.huflit.myapplication4.Fragment;

    import android.content.Context;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.viewmodel.CreationExtras;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.text.Editable;
    import android.text.TextUtils;
    import android.text.TextWatcher;
    import android.view.Gravity;
    import android.view.KeyEvent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.WindowManager;
    import android.view.inputmethod.EditorInfo;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.Button;
    import android.widget.CompoundButton;
    import android.widget.ImageView;
    import android.widget.PopupWindow;
    import android.widget.RadioButton;
    import android.widget.TextView;

    import java.util.ArrayList;

    import edu.huflit.myapplication4.Adapter.BookAdapter;
    import edu.huflit.myapplication4.BookstoreProjectDatabase;
    import edu.huflit.myapplication4.Entity.Book;
    import edu.huflit.myapplication4.MainActivity;
    import edu.huflit.myapplication4.R;
    import edu.huflit.myapplication4.Singleton.LoggingBookList;

    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link BookListFragment#newInstance} factory method to
     * create an instance of this fragment.
     */
    public class BookListFragment extends Fragment implements TextWatcher {

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;
        private LoggingBookList loggingBookList;
        public BookListFragment() {
            // Required empty public constructor
        }
        ArrayList<Book> books;
        String genreName;
        public BookListFragment(String keyword, String genreName) {
            books = new ArrayList<>();
            if(TextUtils.isEmpty(keyword) && TextUtils.isEmpty(genreName)) {
                BookstoreProjectDatabase.LoadBooks();
            }
            else if(!TextUtils.isEmpty(keyword) && TextUtils.isEmpty(genreName)){
                BookstoreProjectDatabase.SearchBook(keyword);
            }
            else if(TextUtils.isEmpty(keyword) && !TextUtils.isEmpty(genreName))
            {
                BookstoreProjectDatabase.LoadBooksWithGenre(genreName);
            }
            this.genreName = genreName;
            this.loggingBookList = LoggingBookList.getInstance();
            books = BookstoreProjectDatabase.GetBooks();
            MainActivity.instance.menuBNV.setVisibility(View.GONE);
            MainActivity.instance.menuBNV.setEnabled(false);
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookListFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static BookListFragment newInstance(String param1, String param2) {
            BookListFragment fragment = new BookListFragment();
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
            return inflater.inflate(R.layout.fragment_book_list, container, false);
        }
        AutoCompleteTextView searchBar_ACTV;
        RecyclerView bookListRV;
        ImageView backBtn, sortBtn;
        TextView nofiMessage, sortTitle;

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            GetIDPalletes(view);
            SetPalletes(view);
            LoadBookList();

            nofiMessage.setEnabled(false);
            if(books.size() == 0) {
                nofiMessage.setVisibility(View.VISIBLE);
            }
            else
                nofiMessage.setVisibility(View.INVISIBLE);
        }

        void GetIDPalletes(View view)
        {
            searchBar_ACTV = (AutoCompleteTextView)view.findViewById(R.id.myautocomplete);
            bookListRV = view.findViewById(R.id.BookList);
            backBtn = view.findViewById(R.id.backBtn);
            nofiMessage = view.findViewById(R.id.message);
            sortBtn = view.findViewById(R.id.sortBtn);
            sortTitle = view.findViewById(R.id.sortTitle);
        }

        void SetPalletes(View view)
        {
            sortTitle.setText("Chưa chọn sắp xếp");
            sortBtn.setOnClickListener(v -> SortBtn(view));
            backBtn.setOnClickListener(v -> BackToPage());
            SearchBar();
        }

        void LoadBookList()
        {
            bookListRV.setLayoutManager(new GridLayoutManager(MainActivity.instance, 2));
            bookListRV.setAdapter(new BookAdapter(getActivity().getApplicationContext(), books));
            loggingBookList.logBooksLoaded(books.size());
        }

        void BackToPage()
        {
            getFragmentManager().popBackStack();
        }

        void SearchBar()
        {
            searchBar_ACTV.addTextChangedListener(this);
            searchBar_ACTV.setAdapter(new ArrayAdapter<String>(MainActivity.instance, android.R.layout.simple_dropdown_item_1line, BookstoreProjectDatabase.bookName));
            searchBar_ACTV.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        MainActivity.instance.currentFragment = new BookListFragment(searchBar_ACTV.getText().toString(), "");
                        MainActivity.instance.ReplaceFragment(-1);
                        searchBar_ACTV.setText("");
                        return true;
                    }
                    return false;
                }
            });
        }

        void SortBtn(View view)
        {
            // Create a new PopupWindow instance
            PopupWindow popup = new PopupWindow(view.getContext().getApplicationContext());

            // Inflate your custom layout
            LayoutInflater inflater = (LayoutInflater) view.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.sortoption, null);

            RadioButton sortNameAscBtn = layout.findViewById(R.id.TenAsc);
            RadioButton sortNameDescBtn = layout.findViewById(R.id.TenDesc);
            RadioButton sortYearPublishedAscBtn = layout.findViewById(R.id.NamXBAsc);
            RadioButton sortYearPublishedDescBtn = layout.findViewById(R.id.NamXBDesc);
            Button closeBtn = layout.findViewById(R.id.closeBtn);

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });

            sortNameAscBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    BookstoreProjectDatabase.SortBookWithName(true, !TextUtils.isEmpty(genreName) ? genreName : "");
                    books = BookstoreProjectDatabase.GetBooks();
                    sortNameDescBtn.setChecked(false);
                    sortYearPublishedAscBtn.setChecked(false);
                    sortYearPublishedDescBtn.setChecked(false);
                    bookListRV.setAdapter(new BookAdapter(getActivity().getApplicationContext(), books));
                    sortTitle.setText("Tên tăng dần");
                    popup.dismiss();
                }
            });

            sortNameDescBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    BookstoreProjectDatabase.SortBookWithName(false, !TextUtils.isEmpty(genreName) ? genreName : "");
                    books = BookstoreProjectDatabase.GetBooks();
                    sortNameAscBtn.setChecked(false);
                    sortYearPublishedAscBtn.setChecked(false);
                    sortYearPublishedDescBtn.setChecked(false);
                    bookListRV.setAdapter(new BookAdapter(getActivity().getApplicationContext(), books));
                    sortTitle.setText("Tên giảm dần");
                    popup.dismiss();
                }
            });

            sortYearPublishedAscBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    BookstoreProjectDatabase.SortBookWithYearPublished(true, !TextUtils.isEmpty(genreName) ? genreName : "");
                    books = BookstoreProjectDatabase.GetBooks();
                    sortNameDescBtn.setChecked(false);
                    sortNameAscBtn.setChecked(false);
                    sortYearPublishedDescBtn.setChecked(false);
                    bookListRV.setAdapter(new BookAdapter(getActivity().getApplicationContext(), books));
                    sortTitle.setText("Năm xuất bản tăng dần");
                    popup.dismiss();
                }
            });

            sortYearPublishedDescBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    BookstoreProjectDatabase.SortBookWithYearPublished(false, !TextUtils.isEmpty(genreName) ? genreName : "");
                    books = BookstoreProjectDatabase.GetBooks();
                    sortNameDescBtn.setChecked(false);
                    sortYearPublishedAscBtn.setChecked(false);
                    sortNameAscBtn.setChecked(false);
                    bookListRV.setAdapter(new BookAdapter(getActivity().getApplicationContext(), books));
                    sortTitle.setText("Năm xuất bản giảm dần");
                    popup.dismiss();
                }
            });

            // Set the custom layout as the content view for the popup window
            popup.setContentView(layout);

            // Set the width and height of the popup window
            popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
            popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

            // Show the popup window
            popup.showAtLocation(view, Gravity.CENTER, 0, 0);
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