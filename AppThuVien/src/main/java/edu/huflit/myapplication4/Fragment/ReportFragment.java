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
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

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

    void CreateGeneralTable(View view)
    {
        String[] nameCols = {"Tổng số sách", "Tổng số bản sao", "Tổng số tài khoản", "Tổng số thẻ thư viện", "Tổng số phiếu mượn"};

        for(int i = 0; i < nameCols.length; i++)
        {
            TableRow row1 = new TableRow(getActivity());
            //row1.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100));

            TextView txtCol = new TextView(getActivity().getApplicationContext());
            txtCol.setText(nameCols[i]);
            txtCol.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol.setGravity(Gravity.LEFT);
            row1.addView(txtCol);

            if(nameCols[i].equals("Tổng số sách"))
            {
                TextView txtCol1 = new TextView(getActivity().getApplicationContext());
                txtCol1.setText(String.valueOf(BookstoreProjectDatabase.books.size()));
                txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
                txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
                row1.addView(txtCol1);
            }
            else if(nameCols[i].equals("Tổng số bản sao"))
            {
                TextView txtCol1 = new TextView(getActivity().getApplicationContext());
                txtCol1.setText(String.valueOf(BookstoreProjectDatabase.copies.size()));
                txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
                txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
                row1.addView(txtCol1);
            }
            else if(nameCols[i].equals("Tổng số tài khoản"))
            {
                TextView txtCol1 = new TextView(getActivity().getApplicationContext());
                txtCol1.setText(String.valueOf(BookstoreProjectDatabase.accounts.size()));
                txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
                txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
                row1.addView(txtCol1);
            }
            else if(nameCols[i].equals("Tổng số thẻ thư viện"))
            {
                TextView txtCol1 = new TextView(getActivity().getApplicationContext());
                txtCol1.setText(String.valueOf(BookstoreProjectDatabase.libraryCards.size()));
                txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
                txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
                row1.addView(txtCol1);
            }
            else if(nameCols[i].equals("Tổng số phiếu mượn"))
            {
                TextView txtCol1 = new TextView(getActivity().getApplicationContext());
                txtCol1.setText(String.valueOf(BookstoreProjectDatabase.loans.size()));
                txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
                txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
                row1.addView(txtCol1);
            }

            tblGeneral.addView(row1);
            //add a new line to the TableLayout:
            final View vline = new View(getActivity().getApplicationContext());

            vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            //vline.setBackgroundColor(Color.BLUE);
            tblGeneral.addView(vline);
        }
    }

    void CreateBookWithCopyTable(View view)
    {
        //add a new line to the TableLayout:
        final View vline = new View(getActivity().getApplicationContext());

        vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 40));
        //vline.setBackgroundColor(Color.BLUE);
        tblGeneral.addView(vline);
        String[] nameCols = {"Sách", "Tổng số bản sao"};
        TableRow row1 = new TableRow(getActivity());
        //row1.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100));
        for(int i = 0; i < nameCols.length; i++)
        {
            TextView txtCol = new TextView(getActivity().getApplicationContext());
            txtCol.setText(nameCols[i]);
            txtCol.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol);
        }
        tblGeneral.addView(row1);
        //add a new line to the TableLayout:
        final View vline1 = new View(getActivity().getApplicationContext());

        vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        //vline.setBackgroundColor(Color.BLUE);
        tblGeneral.addView(vline1);
        for(Book book : BookstoreProjectDatabase.books)
        {
            ArrayList<Copy> copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "");
            row1 = new TableRow(getActivity());
            //row1.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100));

            TextView txtCol1 = new TextView(getActivity().getApplicationContext());
            txtCol1.setText(book.getTitle());
            txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol1.setGravity(Gravity.LEFT);
            row1.addView(txtCol1);

            TextView txtCol2 = new TextView(getActivity().getApplicationContext());
            txtCol2.setText(String.valueOf(copies.size()));
            txtCol2.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol2.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol2);

            tblGeneral.addView(row1);
            //add a new line to the TableLayout:
            final View vline2 = new View(getActivity().getApplicationContext());

            vline2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            //vline.setBackgroundColor(Color.BLUE);
            tblGeneral.addView(vline2);
        }
    }

    void CreateBookWithCopyStatusTable(View view)
    {
        //add a new line to the TableLayout:
        final View vline = new View(getActivity().getApplicationContext());

        vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 40));
        //vline.setBackgroundColor(Color.BLUE);
        tblGeneral.addView(vline);

        String[] nameCols = {"Sách", "Còn", "Cho mượn", "Mất", "Hỏng"};
        TableRow row1 = new TableRow(getActivity());
        //row1.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100));
        for(int i = 0; i < nameCols.length; i++)
        {
            TextView txtCol = new TextView(getActivity().getApplicationContext());
            txtCol.setText(nameCols[i]);
            txtCol.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol);
        }
        tblGeneral.addView(row1);
        //add a new line to the TableLayout:
        final View vline1 = new View(getActivity().getApplicationContext());

        vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        //vline.setBackgroundColor(Color.BLUE);
        tblGeneral.addView(vline1);
        int readyTotal, borrowTotal, lostTotal, brokeTotal;
        readyTotal = 0;
        borrowTotal = 0;
        lostTotal = 0;
        brokeTotal = 0;
        for(Book book : BookstoreProjectDatabase.books)
        {
            row1 = new TableRow(getActivity());
            //row1.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100));

            TextView txtCol = new TextView(getActivity().getApplicationContext());
            txtCol.setText(book.getTitle());
            txtCol.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol.setGravity(Gravity.LEFT);
            row1.addView(txtCol);

            ArrayList<Copy> copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "Còn");
            TextView txtCol1 = new TextView(getActivity().getApplicationContext());
            txtCol1.setText(String.valueOf(copies.size()));
            txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol1);
            readyTotal += copies.size();

            copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "Cho mượn");
            TextView txtCol2 = new TextView(getActivity().getApplicationContext());
            txtCol2.setText(String.valueOf(copies.size()));
            txtCol2.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol2.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol2);
            borrowTotal += copies.size();

            copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "Mất");
            TextView txtCol3 = new TextView(getActivity().getApplicationContext());
            txtCol3.setText(String.valueOf(copies.size()));
            txtCol3.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol3.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol3);
            lostTotal += copies.size();

            copies = BookstoreProjectDatabase.LoadCopiesWithBookId(book.getId(), "Hỏng");
            TextView txtCol4 = new TextView(getActivity().getApplicationContext());
            txtCol4.setText(String.valueOf(copies.size()));
            txtCol4.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol4.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol4);
            brokeTotal += copies.size();

            tblGeneral.addView(row1);
            //add a new line to the TableLayout:
            final View vline2 = new View(getActivity().getApplicationContext());

            vline2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            //vline.setBackgroundColor(Color.BLUE);
            tblGeneral.addView(vline2);
        }


        row1 = new TableRow(getActivity());
        //row1.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100));

        TextView txtCol = new TextView(getActivity().getApplicationContext());
        txtCol.setText("Tổng");
        txtCol.setBackgroundResource(R.drawable.cotlibrarycard);
        txtCol.setGravity(Gravity.LEFT);
        row1.addView(txtCol);

        TextView txtCol1 = new TextView(getActivity().getApplicationContext());
        txtCol1.setText(String.valueOf(readyTotal));
        txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
        txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.addView(txtCol1);

        TextView txtCol2 = new TextView(getActivity().getApplicationContext());
        txtCol2.setText(String.valueOf(borrowTotal));
        txtCol2.setBackgroundResource(R.drawable.cotlibrarycard);
        txtCol2.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.addView(txtCol2);

        TextView txtCol3 = new TextView(getActivity().getApplicationContext());
        txtCol3.setText(String.valueOf(lostTotal));
        txtCol3.setBackgroundResource(R.drawable.cotlibrarycard);
        txtCol3.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.addView(txtCol3);

        TextView txtCol4 = new TextView(getActivity().getApplicationContext());
        txtCol4.setText(String.valueOf(brokeTotal));
        txtCol4.setBackgroundResource(R.drawable.cotlibrarycard);
        txtCol4.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.addView(txtCol4);

        tblGeneral.addView(row1);
        //add a new line to the TableLayout:
        final View vline2 = new View(getActivity().getApplicationContext());

        vline2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        //vline.setBackgroundColor(Color.BLUE);
        tblGeneral.addView(vline2);
    }
}