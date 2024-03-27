package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;
import edu.huflit.myapplication4.Builder.TableBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
        return inflater.inflate(R.layout.fragment_report, container, false);
    }
    TableLayout tblGeneral;
    ImageView backBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
        BookstoreProjectDatabase.LoadBooks();
        BookstoreProjectDatabase.LoadCopies();
        BookstoreProjectDatabase.LoadAccounts();
        BookstoreProjectDatabase.LoadLibraryCards();
        BookstoreProjectDatabase.LoadLoan();
        System.out.println(BookstoreProjectDatabase.libraryCards.size());

        GetIdPalletes(view);
        SetPalletes(view);
    }

    void GetIdPalletes(View view)
    {
        tblGeneral =view.findViewById(R.id.tbl_statistics);
        backBtn=view.findViewById(R.id.backBtn);
    }

    void SetPalletes(View view)
    {
        CreateGeneralTable(view);
        CreateBookWithCopyTable(view);
        CreateBookWithCopyStatusTable(view);
        backBtn.setOnClickListener(v -> BackBtn());
    }

    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }

    void CreateGeneralTable(View view) {
        TableBuilder tableBuilder = new TableBuilder(getActivity(), tblGeneral);
        String[] nameCols = {"Tổng số sách", "Tổng số bản sao", "Tổng số tài khoản", "Tổng số thẻ thư viện", "Tổng số phiếu mượn"};

        for (String nameCol : nameCols) {
            ArrayList<TextView> textViews = new ArrayList<>();

            textViews.add(tableBuilder.createTextView(nameCol, Gravity.LEFT));

            if (nameCol.equals("Tổng số sách")) {
                textViews.add(tableBuilder.createTextView(String.valueOf(BookstoreProjectDatabase.books.size()), Gravity.CENTER_HORIZONTAL));
            } else if (nameCol.equals("Tổng số bản sao")) {
                textViews.add(tableBuilder.createTextView(String.valueOf(BookstoreProjectDatabase.copies.size()), Gravity.CENTER_HORIZONTAL));
            } else if (nameCol.equals("Tổng số tài khoản")) {
                textViews.add(tableBuilder.createTextView(String.valueOf(BookstoreProjectDatabase.accounts.size()), Gravity.CENTER_HORIZONTAL));
            } else if (nameCol.equals("Tổng số thẻ thư viện")) {
                textViews.add(tableBuilder.createTextView(String.valueOf(BookstoreProjectDatabase.libraryCards.size()), Gravity.CENTER_HORIZONTAL));
            } else if (nameCol.equals("Tổng số phiếu mượn")) {
                textViews.add(tableBuilder.createTextView(String.valueOf(BookstoreProjectDatabase.loans.size()), Gravity.CENTER_HORIZONTAL));
            }

            tableBuilder.addRow(textViews);
        }
    }

    void CreateBookWithCopyTable(View view) {
        TableBuilder tableBuilder = new TableBuilder(getActivity(), tblGeneral);
        String[] nameCols = {"Sách", "Tổng số bản sao"};

        ArrayList<TextView> headerTextViews = new ArrayList<>();
        for (String nameCol : nameCols) {
            headerTextViews.add(tableBuilder.createTextView(nameCol, Gravity.CENTER_HORIZONTAL));
        }
        tableBuilder.addRow(headerTextViews);

        for (Book book : BookstoreProjectDatabase.books) {
            ArrayList<TextView> rowTextViews = new ArrayList<>();

            rowTextViews.add(tableBuilder.createTextView(book.getTitle(), Gravity.LEFT));

            ArrayList<Copy> copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "");
            rowTextViews.add(tableBuilder.createTextView(String.valueOf(copies.size()), Gravity.CENTER_HORIZONTAL));

            tableBuilder.addRow(rowTextViews);
        }
    }

    void CreateBookWithCopyStatusTable(View view) {
        TableBuilder tableBuilder = new TableBuilder(getActivity(), tblGeneral);
        String[] nameCols = {"Sách", "Còn", "Cho mượn", "Mất", "Hỏng"};

        ArrayList<TextView> headerTextViews = new ArrayList<>();
        for (String nameCol : nameCols) {
            headerTextViews.add(tableBuilder.createTextView(nameCol, Gravity.CENTER_HORIZONTAL));
        }
        tableBuilder.addRow(headerTextViews);

        int readyTotal = 0, borrowTotal = 0, lostTotal = 0, brokeTotal = 0;
        for (Book book : BookstoreProjectDatabase.books) {
            ArrayList<TextView> rowTextViews = new ArrayList<>();

            rowTextViews.add(tableBuilder.createTextView(book.getTitle(), Gravity.LEFT));

            ArrayList<Copy> copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "Còn");
            rowTextViews.add(tableBuilder.createTextView(String.valueOf(copies.size()), Gravity.CENTER_HORIZONTAL));
            readyTotal += copies.size();

            copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "Cho mượn");
            rowTextViews.add(tableBuilder.createTextView(String.valueOf(copies.size()), Gravity.CENTER_HORIZONTAL));
            borrowTotal += copies.size();

            copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "Mất");
            rowTextViews.add(tableBuilder.createTextView(String.valueOf(copies.size()), Gravity.CENTER_HORIZONTAL));
            lostTotal += copies.size();

            copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "Hỏng");
            rowTextViews.add(tableBuilder.createTextView(String.valueOf(copies.size()), Gravity.CENTER_HORIZONTAL));
            brokeTotal += copies.size();

            tableBuilder.addRow(rowTextViews);
        }

        ArrayList<TextView> totalRowTextViews = new ArrayList<>();
        totalRowTextViews.add(tableBuilder.createTextView("Tổng", Gravity.LEFT));
        totalRowTextViews.add(tableBuilder.createTextView(String.valueOf(readyTotal), Gravity.CENTER_HORIZONTAL));
        totalRowTextViews.add(tableBuilder.createTextView(String.valueOf(borrowTotal), Gravity.CENTER_HORIZONTAL));
        totalRowTextViews.add(tableBuilder.createTextView(String.valueOf(lostTotal), Gravity.CENTER_HORIZONTAL));
        totalRowTextViews.add(tableBuilder.createTextView(String.valueOf(brokeTotal), Gravity.CENTER_HORIZONTAL));

        tableBuilder.addRow(totalRowTextViews);
    }
}