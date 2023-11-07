package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CopyBookAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CopyBookAddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CopyBookAddFragment() {
        // Required empty public constructor
    }
    Copy copy;
    public CopyBookAddFragment(Copy copy) {
        // Required empty public constructor
        this.copy = copy;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CopyBookAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CopyBookAddFragment newInstance(String param1, String param2) {
        CopyBookAddFragment fragment = new CopyBookAddFragment();
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
        return inflater.inflate(R.layout.fragment_copy_book_add, container, false);
    }
    ImageView backBtn;
    Spinner addcopybooksatusSpin;
    Button updateBtn;
    EditText copyId, copyBookID, copyBookNoteInput;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetIdPalletes(view);
        SetPalletes();
    }

    void GetIdPalletes(View view)
    {
        backBtn = view.findViewById(R.id.backaddcopybookBtn);
        addcopybooksatusSpin = view.findViewById(R.id.addcopybooksatus);
        copyId = view.findViewById(R.id.addcopyid);
        copyBookID = view.findViewById(R.id.addcopybookid);
        copyBookNoteInput = view.findViewById(R.id.addcopybooknote);
        updateBtn = view.findViewById(R.id.btnaddcopybook);
    }

    void SetPalletes()
    {
        backBtn.setOnClickListener(v -> BackBtn());
        updateBtn.setOnClickListener(v -> UpdateBtn());
        copyId.setText(copy.getId());
        copyBookID.setText(copy.getBookId());
        copyBookID.setEnabled(false);
        CopyBookStatusSpinner();
        for(int i = 0; i < statuses.length; i++)
        {
            if(statuses[i].equals(copy.getStatus()))
            {
                addcopybooksatusSpin.setSelection(i);
                break;
            }
        }
        copyBookNoteInput.setText(copy.getNotes());
    }

    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }
    String[] statuses = {"Còn", "Cho mượn", "Mất", "Hỏng"};
    void CopyBookStatusSpinner()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.instance, android.R.layout.simple_spinner_dropdown_item, statuses);
        //set the spinners adapter to the previously created one.
        addcopybooksatusSpin.setAdapter(adapter);
    }

    void UpdateBtn()
    {
        copy.setStatus(addcopybooksatusSpin.getSelectedItem().toString());
        copy.setNotes(copyBookNoteInput.getText().toString());
        BookstoreProjectDatabase.UpdateBookCopy(copy);
        getFragmentManager().popBackStack();
    }
}