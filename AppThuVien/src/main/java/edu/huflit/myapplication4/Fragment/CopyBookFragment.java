package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.huflit.myapplication4.Adapter.BookCopy2Adapter;
import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CopyBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CopyBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CopyBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CopyBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CopyBookFragment newInstance(String param1, String param2) {
        CopyBookFragment fragment = new CopyBookFragment();
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
        return inflater.inflate(R.layout.fragment_copy_book, container, false);
    }

    RecyclerView copybookList;
    ImageView backcopyBtn;
    int index;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
        index = 0;
        BookstoreProjectDatabase.LoadBooks();
        GetIDPalletes(view);
        SetPalletes(view);
    }
    void GetIDPalletes(View view)
    {
        copybookList = view.findViewById(R.id.copybooklist);
        backcopyBtn = view.findViewById(R.id.backcopyBtn);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        backcopyBtn.setOnClickListener(v -> BackBtn());
        LoadBookCopyList();
    }

    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }

    void LoadBookCopyList(){
        copybookList.setLayoutManager(new LinearLayoutManager(MainActivity.instance, RecyclerView.HORIZONTAL,false));
        copybookList.setAdapter(new BookCopy2Adapter(getActivity().getApplicationContext(),BookstoreProjectDatabase.books));
    }
}