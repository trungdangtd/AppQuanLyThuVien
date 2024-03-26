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
import edu.huflit.myapplication4.Entity.Genre;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGenreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGenreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddGenreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddGenreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGenreFragment newInstance(String param1, String param2) {
        AddGenreFragment fragment = new AddGenreFragment();
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
        return inflater.inflate(R.layout.fragment_add_genre, container, false);
    }

    EditText idGenreInput, nameGenreInput;
    Button addGenreBtn, deleteTextBtn;
    ImageView backBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetIDPalletes(view);
        SetPalletes(view);
    }

    void GetIDPalletes(View view)
    {
        backBtn = view.findViewById(R.id.backBtnGenre);

        idGenreInput = view.findViewById(R.id.IDGenreInput);
        nameGenreInput = view.findViewById(R.id.NameGenreInput);
        addGenreBtn = view.findViewById(R.id.addbuttongenre);
        deleteTextBtn = view.findViewById(R.id.clearbuttongenre);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        backBtn.setOnClickListener(v -> BackBtn());
        deleteTextBtn.setOnClickListener(v -> DeleteTextBtn());
        addGenreBtn.setOnClickListener(v -> AddBookBtn(view));
    }
    Integer current;
    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }
    void DeleteTextBtn()
    {
        idGenreInput.setText("");
        nameGenreInput.setText("");
    }
    void AddBookBtn(View view)
    {
        if(TextUtils.isEmpty(idGenreInput.getText().toString())) {
            idGenreInput.setError("Không được để trống ID sách");
            return;
        }
        if(TextUtils.isEmpty(nameGenreInput.getText().toString())) {
            nameGenreInput.setError("Không được để trống tên loại sách");
            return;
        }
        BookstoreProjectDatabase.AddGenre(new Genre(idGenreInput.getText().toString(),
                nameGenreInput.getText().toString()));
        getFragmentManager().popBackStack();
    }
}