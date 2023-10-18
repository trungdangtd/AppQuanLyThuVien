package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Account;
import edu.huflit.myapplication4.Entity.LibraryCard;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    TextView updateAccountBtn;
    TextView fullNameUser, cardIdUser, expirationDateUser, cardStatusUser, passwordCardUser;
    Button logoutBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.VISIBLE);
        MainActivity.instance.menuBNV.setEnabled(true);
        GetIDPalletes(view);
        SetPalletes(view);
    }

    // Gọi các pallete có trong layout
    void GetIDPalletes(View view)
    {
        updateAccountBtn = view.findViewById(R.id.chinhsua);
        fullNameUser = view.findViewById(R.id.fullName);
        cardIdUser = view.findViewById(R.id.cardId);
        expirationDateUser = view.findViewById(R.id.expirationDate);
        cardStatusUser = view.findViewById(R.id.cardStatus);
        passwordCardUser = view.findViewById(R.id.passwordCard);
        logoutBtn = view.findViewById(R.id.Logout);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        updateAccountBtn.setOnClickListener(v -> ChangePassword());
        logoutBtn.setOnClickListener(v -> LogoutBtn());

        if(BookstoreProjectDatabase.accountInfo.getRole().equals("Sinh viên")) {
            fullNameUser.setText(BookstoreProjectDatabase.libraryCard.getName());
            cardIdUser.setText(BookstoreProjectDatabase.libraryCard.getId());
            expirationDateUser.setText(BookstoreProjectDatabase.libraryCard.getExpirationDate());
            cardStatusUser.setText(BookstoreProjectDatabase.libraryCard.getUseStatus() ? "Sử dụng được" : "Không sử dụng được");

        }
        else
        {
            fullNameUser.setText(BookstoreProjectDatabase.accountInfo.getAccount());
            cardIdUser.setText("Unknown");
            expirationDateUser.setText("Unknown");
            cardStatusUser.setText("Unknown");
        }
        passwordCardUser.setText(BookstoreProjectDatabase.accountInfo.getPassword());
    }

    void ChangePassword()
    {
        MainActivity.instance.currentFragment = new ForgotPasswordFragment(BookstoreProjectDatabase.accountInfo.getAccount());
        MainActivity.instance.ReplaceFragment(-1);
    }
    void LogoutBtn()
    {
        if(BookstoreProjectDatabase.accountInfo.getRole().equals("Sinh viên"))
            BookstoreProjectDatabase.UpdateAccount(BookstoreProjectDatabase.accountInfo.getAccount(), false);
        BookstoreProjectDatabase.accountInfo = new Account();
        BookstoreProjectDatabase.libraryCard = new LibraryCard();
        MainActivity.instance.isLogin = false;
        MainActivity.instance.currentFragment = new LoginFragment();
        MainActivity.instance.ReplaceFragment(-1);
    }
}