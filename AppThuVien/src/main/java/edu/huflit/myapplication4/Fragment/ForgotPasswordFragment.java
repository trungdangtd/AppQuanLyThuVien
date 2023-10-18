package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String account;
    public ForgotPasswordFragment(String account) {
        // Required empty public constructor
        this.account = account;
    }
    public ForgotPasswordFragment() {
        // Required empty public constructor
        account = "";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }
    TextView accTitle;
    EditText accInput;
    EditText passInput;
    TextView resetBtn;
    TextView returnBtn;
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
        accInput = view.findViewById(R.id.accountIdentify);
        accTitle = view.findViewById(R.id.textView2);
        passInput = view.findViewById(R.id.newPassword);
        resetBtn = view.findViewById(R.id.resetPass);
        returnBtn = view.findViewById(R.id.returnBtn);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        if(!TextUtils.isEmpty(account)) {
            accInput.setText(BookstoreProjectDatabase.accountInfo.getAccount());
            accInput.setVisibility(View.GONE);
            accTitle.setVisibility(View.GONE);
            accInput.setEnabled(false);
            accTitle.setEnabled(false);
        }
        else
        {
            accInput.setVisibility(View.VISIBLE);
            accTitle.setVisibility(View.VISIBLE);
            accInput.setEnabled(true);
            accTitle.setEnabled(true);
        }

        resetBtn.setOnClickListener(v -> ResetPassword(view));
        returnBtn.setOnClickListener(v -> ReturnBtn());
    }

    void ResetPassword(View view)
    {
        if(TextUtils.isEmpty(accInput.getText().toString())) {
            accInput.setError("Tài khoản không được để trống");
            return;
        }
        if(TextUtils.isEmpty(passInput.getText().toString())) {
            passInput.setError("Mật khẩu không được để trống");
            return;
        }

        if(BookstoreProjectDatabase.UpdateAccount(accInput.getText().toString(), passInput.getText().toString()))
        {
            Snackbar.make(view, "Đổi mật khẩu thành công", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            if(!MainActivity.instance.isLogin)
                MainActivity.instance.currentFragment = new LoginFragment();
            else
            {
                if(BookstoreProjectDatabase.accountInfo.getRole().equals("Sinh viên"))
                    MainActivity.instance.currentFragment = new AccountFragment();
                else if(BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý"))
                    MainActivity.instance.currentFragment = new ManageListFragment();
            }
            MainActivity.instance.ReplaceFragment(-1);
        }
        else
        {
            Snackbar.make(view, "Tài khoản hoặc mật khẩu bị sai", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    void ReturnBtn()
    {
        if(!MainActivity.instance.isLogin)
            MainActivity.instance.currentFragment = new LoginFragment();
        else {
            if(BookstoreProjectDatabase.accountInfo.getRole().equals("Sinh viên"))
                MainActivity.instance.currentFragment = new AccountFragment();
            else if(BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý"))
                MainActivity.instance.currentFragment = new ManageListFragment();
        }
        MainActivity.instance.ReplaceFragment(-1);
    }
}