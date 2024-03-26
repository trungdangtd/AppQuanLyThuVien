package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import edu.huflit.myapplication4.DecoratorForLogin.AuthDecorator;
import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.DecoratorForLogin.LoggingDecorator;
import edu.huflit.myapplication4.DecoratorForLogin.LoginDecorator;
import edu.huflit.myapplication4.DecoratorForLogin.LoginFunctionality;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    EditText accInput;
    EditText passInput;
    TextView loginBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetIDPalletes(view);
        SetPalletes(view);

    }

    // Gọi các pallete có trong layout
    void GetIDPalletes(View view)
    {
        accInput = view.findViewById(R.id.accountInput);
        passInput = view.findViewById(R.id.passwordInput);
        loginBtn = view.findViewById(R.id.Login);

    }

    // Phương thức đăng nhập cơ bản
    private void login(View view, String account, String password) {
        // Triển khai chức năng đăng nhập ở đây
        BookstoreProjectDatabase.SearchAccount(account, password, view);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        // Sử dụng Decorator để gắn thêm chức năng kiểm tra xác thực
        loginBtn.setOnClickListener(v -> {
            LoginFunctionality loginFunctionality = new AuthDecorator(new LoginDecorator(new LoggingDecorator(this::login)));
            loginFunctionality.login(view, accInput.getText().toString(), passInput.getText().toString());
        });
    }

}