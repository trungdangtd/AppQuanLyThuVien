package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Account;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Account account;
    public UpdateAccountFragment() {
        // Required empty public constructor
    }
    public UpdateAccountFragment(Account account) {
        // Required empty public constructor
        this.account = account;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateAccountFragment newInstance(String param1, String param2) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
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
        return inflater.inflate(R.layout.fragment_update_account, container, false);
    }

    EditText accInput, passInput;
    ImageView backBtn;
    Button updateAccountBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetIDPalletes(view);
        SetPalletes(view);
    }

    void GetIDPalletes(View view)
    {
        backBtn = view.findViewById(R.id.backBtn);

        accInput = view.findViewById(R.id.accountInput);
        passInput = view.findViewById(R.id.passInput);

        updateAccountBtn = view.findViewById(R.id.updatebutton);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        backBtn.setOnClickListener(v -> BackBtn());
        updateAccountBtn.setOnClickListener(v -> UpdateBookBtn());

        accInput.setText(account.getAccount());
        accInput.setEnabled(false);
        passInput.setText(account.getRole());
    }
    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }
    void UpdateBookBtn()
    {
        if(TextUtils.isEmpty(passInput.getText().toString())) {
            passInput.setError("Không được để trống mật khẩu");
            return;
        }

        BookstoreProjectDatabase.UpdateAccount(account.getAccount(), passInput.getText().toString());
        getFragmentManager().popBackStack();
    }
}