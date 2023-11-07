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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Account;
import edu.huflit.myapplication4.Entity.LibraryCard;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    TextView registerBtn, logoutBtn;
    EditText accountInput, nameInput;
    TextView roleText;
    Spinner roleSpin;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
        GetIDPalletes(view);
        SetPalletes(view);
    }


    // Gọi các pallete có trong layout
    void GetIDPalletes(View view)
    {
        registerBtn = view.findViewById(R.id.btnregisteradmin2);
        roleText = view.findViewById(R.id.roleText);
        logoutBtn = view.findViewById(R.id.returnBtn);

        accountInput = view.findViewById(R.id.edtemasv);
        nameInput = view.findViewById(R.id.edtten);
        roleSpin = view.findViewById(R.id.accountRoleInput);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        if(BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý"))
        {
            nameInput.setVisibility(View.GONE);
            nameInput.setEnabled(false);
            RoleInput();
        }
        else if(BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ thư"))
        {
            roleSpin.setVisibility(View.GONE);
            roleSpin.setEnabled(false);

            roleText.setVisibility(View.GONE);
            roleText.setEnabled(false);
        }
        registerBtn.setOnClickListener(v -> RegisterBtn());
        logoutBtn.setOnClickListener(v -> QuitBtn());

    }

    void QuitBtn()
    {
        MainActivity.instance.currentFragment = new ManageListFragment();
        MainActivity.instance.ReplaceFragment(-1);
    }

    void RegisterBtn()
    {
        if(BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý"))
        {
            if(TextUtils.isEmpty(accountInput.getText().toString()))
                accountInput.setError("Tài khoản không được để trống");
            BookstoreProjectDatabase.AddAccount(new Account(accountInput.getText().toString(), accountInput.getText().toString(), roleSpin.getSelectedItem().toString()));
        }
        else {
            if(TextUtils.isEmpty(accountInput.getText().toString()))
                accountInput.setError("Tài khoản không được để trống");
            else if(TextUtils.isEmpty(nameInput.getText().toString()))
                nameInput.setError("Tên không được để trống");

            Calendar currentCal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            currentCal.add(Calendar.DATE, 1095);
            String toDate = dateFormat.format(currentCal.getTime());

            if(BookstoreProjectDatabase.AddAccount(new Account(accountInput.getText().toString(), accountInput.getText().toString(), "Sinh viên")))
                BookstoreProjectDatabase.AddLibraryCard(new LibraryCard(accountInput.getText().toString(), nameInput.getText().toString(), toDate, true, false));
        }

        MainActivity.instance.currentFragment = new ManageListFragment();
        MainActivity.instance.ReplaceFragment(-1);
    }

    void RoleInput()
    {
        String[] roles = {"Thủ thư", "Thủ kho"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.instance, android.R.layout.simple_spinner_dropdown_item, roles);
        //set the spinners adapter to the previously created one.
        roleSpin.setAdapter(adapter);
    }
}