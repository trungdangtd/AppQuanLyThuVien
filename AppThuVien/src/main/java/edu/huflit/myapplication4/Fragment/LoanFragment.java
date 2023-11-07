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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Loan;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoanFragment newInstance(String param1, String param2) {
        LoanFragment fragment = new LoanFragment();
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
        return inflater.inflate(R.layout.fragment_loan, container, false);
    }
    TableLayout tbl;
    ImageView backBtn;
    FloatingActionButton addLoanBtn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
        BookstoreProjectDatabase.LoadLoan();
        System.out.println("LoanSize: " + BookstoreProjectDatabase.loans.size());
        GetIdPalletes(view);
        SetPalletes(view);
    }
    void GetIdPalletes(View view)
    {
        tbl=view.findViewById(R.id.tbl_statistics);
        backBtn=view.findViewById(R.id.backBtn);
        addLoanBtn=view.findViewById(R.id.fab);
    }

    void SetPalletes(View view)
    {
        CreateTable(view);
        backBtn.setOnClickListener(v -> BackBtn());
        addLoanBtn.setOnClickListener(v -> AddLoanBtn(view));
    }

    void AddLoanBtn(View view)
    {
        Snackbar.make(view, "Thêm phiếu mượn", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        MainActivity.instance.currentFragment = new AddLoanFragment();
        MainActivity.instance.ReplaceFragment(-1);
    }

    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }
    void CreateTable(View view)
    {
        String[] nameCols = {"Mã sách", "MSSV", "Mã kiểm soát bản sao", "Ngày mượn sách", "Ngày hạn trả sách"};
        TableRow row1 = new TableRow(getActivity());
        //row1.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        for(int i = 0; i < nameCols.length; i++)
        {

            TextView txtCol = new TextView(getActivity().getApplicationContext());
            txtCol.setText(nameCols[i]);
            txtCol.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol);

        }
        tbl.addView(row1);
        //add a new line to the TableLayout:
        final View vline = new View(getActivity().getApplicationContext());

        vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        //vline.setBackgroundColor(Color.BLUE);
        tbl.addView(vline);
        for(Loan rowItem : BookstoreProjectDatabase.loans)
        {
            row1 = new TableRow(getActivity());
            //row1.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            //add a new row to the TableLayout

            TextView txtCol1 = new TextView(getActivity().getApplicationContext());
            txtCol1.setText(rowItem.getBookId());
            txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol1);

            TextView txtCol2 = new TextView(getActivity().getApplicationContext());
            txtCol2.setText(rowItem.getCardId());
            txtCol2.setGravity(Gravity.CENTER_HORIZONTAL);
            txtCol2.setBackgroundResource(R.drawable.cotlibrarycard);
            row1.addView(txtCol2);

            TextView txtCol3 = new TextView(getActivity().getApplicationContext());
            txtCol3.setText(rowItem.getCopyId());
            txtCol3.setGravity(Gravity.CENTER_HORIZONTAL);
            txtCol3.setBackgroundResource(R.drawable.cotlibrarycard);
            row1.addView(txtCol3);

            TextView txtCol4 = new TextView(getActivity().getApplicationContext());
            txtCol4.setText(rowItem.getDateLoaned());
            txtCol4.setGravity(Gravity.CENTER_HORIZONTAL);
            txtCol4.setBackgroundResource(R.drawable.cotlibrarycard);
            row1.addView(txtCol4);

            TextView txtCol5 = new TextView(getActivity().getApplicationContext());
            txtCol5.setText(rowItem.getDateDue());
            txtCol5.setGravity(Gravity.CENTER_HORIZONTAL);
            txtCol5.setBackgroundResource(R.drawable.cotlibrarycard);
            row1.addView(txtCol5);

            tbl.addView(row1);
            //add a new line to the TableLayout:
            final View vline1 = new View(getActivity().getApplicationContext());

            vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            //vline.setBackgroundColor(Color.BLUE);
            tbl.addView(vline1);
        }
    }
}