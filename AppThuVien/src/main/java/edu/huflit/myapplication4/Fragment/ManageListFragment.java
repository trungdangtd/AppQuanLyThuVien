package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ManageListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageListFragment newInstance(String param1, String param2) {
        ManageListFragment fragment = new ManageListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ManageListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_list, container, false);
    }

    LinearLayout quitBtn, registerBtn, accountBtn;
    TextView loadingScreen;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.VISIBLE);
        MainActivity.instance.menuBNV.setEnabled(true);
        GetIDPalletes(view);
        SetPalletes(view);
        loadingScreen.setVisibility(View.INVISIBLE);
        loadingScreen.setEnabled(false);
        quitBtn.setEnabled(true);
        registerBtn.setEnabled(true);
    }


    // Gọi các pallete có trong layout
    void GetIDPalletes(View view)
    {
        quitBtn = view.findViewById(R.id.logoutBtn);
        registerBtn = view.findViewById(R.id.RegisterBtn);
        accountBtn = view.findViewById(R.id.manageAccountBtn);
        loadingScreen = view.findViewById(R.id.loadingScreens);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        if (BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý"))
        {
            registerBtn.setOnClickListener(v -> RegisterBtn());
            accountBtn.setOnClickListener(v -> AccountBtn());

        }
        else if(BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ kho"))
        {
            registerBtn.setVisibility(View.GONE);
            registerBtn.setEnabled(false);

            accountBtn.setVisibility(View.GONE);
            accountBtn.setEnabled(false);
        }
        else if(BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ thư"))
        {
            accountBtn.setOnClickListener(v -> AccountBtn());
            registerBtn.setOnClickListener(v -> RegisterBtn());

        }
        quitBtn.setOnClickListener(v -> QuitBtn());
    }

    void Loading()
    {
        loadingScreen.setVisibility(View.VISIBLE);
        quitBtn.setEnabled(false);
        registerBtn.setEnabled(false);

    }


    void AccountBtn()
    {
        Loading();
        MainActivity.instance.currentFragment = new AccountManageFragment();
        MainActivity.instance.ReplaceFragment(-1);
    }

    void QuitBtn()
    {
        Loading();
        BookstoreProjectDatabase.UpdateAccount(BookstoreProjectDatabase.accountInfo.getAccount(), false);
        MainActivity.instance.currentFragment = new LoginFragment();
        MainActivity.instance.ReplaceFragment(-1);
        MainActivity.instance.isLogin = false;
    }

    void RegisterBtn()
    {
        Loading();
        MainActivity.instance.currentFragment = new RegisterFragment();
        MainActivity.instance.ReplaceFragment(-1);

    }


}