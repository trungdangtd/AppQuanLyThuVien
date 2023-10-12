package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import edu.huflit.myapplication4.Adapter.AccountAdapter;
import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountManageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountManageFragment newInstance(String param1, String param2) {
        AccountManageFragment fragment = new AccountManageFragment();
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
        return inflater.inflate(R.layout.fragment_account_manage, container, false);
    }

    TextView roleAc;
    TextView passwordAc;
    TextView nameAc;
    RecyclerView acmanageIt;
    ImageView backBtn;
    AutoCompleteTextView searchBar_ACTV;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
        BookstoreProjectDatabase.LoadAccounts("Sinh viên");
        GetIDPalletes(view);
        SetPalletes(view);
    }

    void GetIDPalletes(View view)
    {
        searchBar_ACTV = (AutoCompleteTextView)view.findViewById(R.id.myautocomplete);
        backBtn = view.findViewById(R.id.backBtn);
        roleAc=view.findViewById(R.id.roleac);
        passwordAc=view.findViewById(R.id.passwordAc);
        acmanageIt=view.findViewById(R.id.accountmanageitem);
        nameAc=view.findViewById(R.id.Nameacmanage);
    }
    void LoadAccountManage()
    {
        acmanageIt.setLayoutManager(new LinearLayoutManager(MainActivity.instance, RecyclerView.VERTICAL, false));
        acmanageIt.setAdapter(new AccountAdapter(getActivity().getApplicationContext(), BookstoreProjectDatabase.accounts));
    }
    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        backBtn.setOnClickListener(v -> BackBtn());
        LoadAccountManage();
        SearchBar();
    }

    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }

    void SearchBar()
    {
        searchBar_ACTV.addTextChangedListener((TextWatcher) this);
        searchBar_ACTV.setAdapter(new ArrayAdapter<String>(MainActivity.instance, android.R.layout.simple_dropdown_item_1line, BookstoreProjectDatabase.accNames));
        searchBar_ACTV.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    BookstoreProjectDatabase.LoadAccountWithId("Sinh viên", searchBar_ACTV.getText().toString());
                    acmanageIt.setAdapter(new AccountAdapter(getActivity().getApplicationContext(), BookstoreProjectDatabase.accounts));
                    searchBar_ACTV.setText("");
                    return true;
                }
                return false;
            }
        });
    }
}