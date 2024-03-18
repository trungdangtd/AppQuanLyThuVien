package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.Entity.Loan;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddLoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddLoanFragment extends Fragment implements TextWatcher {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddLoanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddLoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddLoanFragment newInstance(String param1, String param2) {
        AddLoanFragment fragment = new AddLoanFragment();
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
        return inflater.inflate(R.layout.fragment_add_loan, container, false);
    }
    AutoCompleteTextView cardIdInput, bookIdSpin;
    Spinner bookCopySpin;
    Button clearBtn, addBtn;
    ImageView backBtn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BookstoreProjectDatabase.LoadAccounts();
        GetIdPalletes(view);
        SetIdPalletes();
    }

    void GetIdPalletes(View view)
    {
        cardIdInput = view.findViewById(R.id.loanborrowInput);
        bookIdSpin = view.findViewById(R.id.bookloanIdInput);
        bookCopySpin = view.findViewById(R.id.bookCopyIdInput);
        clearBtn = view.findViewById(R.id.clearbutton);
        addBtn = view.findViewById(R.id.addbutton);
        backBtn = view.findViewById(R.id.backBtn);
    }

    void SetIdPalletes()
    {
        clearBtn.setOnClickListener(v -> ClearButton());
        addBtn.setOnClickListener(v -> AddButton());
        backBtn.setOnClickListener(v -> BackBtn());

        cardIdInput.addTextChangedListener(this);
        cardIdInput.setAdapter(new ArrayAdapter<String>(MainActivity.instance, android.R.layout.simple_dropdown_item_1line, BookstoreProjectDatabase.accNames));
        cardIdInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    LoadBook();
                    return true;
                }
                return false;
            }
        });

        bookIdSpin.addTextChangedListener(this);
        bookIdSpin.setAdapter(new ArrayAdapter<String>(MainActivity.instance, android.R.layout.simple_dropdown_item_1line, BookstoreProjectDatabase.bookIDs));
        bookIdSpin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    LoadBook();
                    return true;
                }
                return false;
            }
        });
        bookCopySpin.setEnabled(false);
    }
    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }
    void ClearButton()
    {
        cardIdInput.setText("");
    }
    ArrayList<Copy> copyArrayList;
    void LoadBook()
    {
        ArrayList<String> copyName = new ArrayList<>();
        copyArrayList = new ArrayList<>();
        copyArrayList = BookstoreProjectDatabase.LoadCopiesWithBookId(bookIdSpin.getText().toString(), "Còn");

        for(Copy copy : copyArrayList)
        {
            copyName.add(copy.getId());
        }

        bookCopySpin.setEnabled(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.instance, android.R.layout.simple_spinner_dropdown_item, copyName);
        //set the spinners adapter to the previously created one.
        bookCopySpin.setAdapter(adapter);
    }

    void AddButton()
    {
        if(TextUtils.isEmpty(cardIdInput.getText().toString())) {
            cardIdInput.setError("Không được để trống mã sinh viên");
            return;
        }
        if(TextUtils.isEmpty(bookIdSpin.getText().toString())) {
            bookIdSpin.setError("Không được để trống mã sách");
            return;
        }
        Calendar currentCal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String currentdate = dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.DATE, 15);
        String toDate = dateFormat.format(currentCal.getTime());

        BookstoreProjectDatabase.AddLoan(new Loan(bookIdSpin.getText().toString(),
                cardIdInput.getText().toString(),
                bookCopySpin.getSelectedItem().toString(),
                currentdate,
                toDate));


        for(Copy copy : copyArrayList)
        {
            if(copy.getId().equals(bookCopySpin.getSelectedItem().toString()))
            {
                BookstoreProjectDatabase.UpdateBookCopy(copy);
                break;
            }
        }
        BookstoreProjectDatabase.LoadLoan();
        getFragmentManager().popBackStack();
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

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}