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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.LibraryCard;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateLibraryCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateLibraryCardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LibraryCard libraryCard;

    public UpdateLibraryCardFragment() {
        // Required empty public constructor
    }

    public UpdateLibraryCardFragment(LibraryCard libraryCard) {
        // Required empty public constructor
        this.libraryCard = libraryCard;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateLibraryCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateLibraryCardFragment newInstance(String param1, String param2) {
        UpdateLibraryCardFragment fragment = new UpdateLibraryCardFragment();
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
        return inflater.inflate(R.layout.fragment_update_library_card, container, false);
    }

    Spinner ExpirationDate, UseStatus;
    TextView CardId;
    Button UpdateButton;
    ImageView backBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetIdPalletes(view);
        SetPalletes();
    }

    void GetIdPalletes(View view)
    {
        ExpirationDate = view.findViewById(R.id.ExpirationDate);
        UseStatus = view.findViewById(R.id.UseStatus);
        UpdateButton = view.findViewById(R.id.updatebutton);
        backBtn=view.findViewById(R.id.backBtn);
    }

    void SetPalletes()
    {
        ExpirationDateInput();
        UseStatusInput();
        UpdateButton.setOnClickListener(v -> UpdateLibraryCardButton());
        backBtn.setOnClickListener(v -> BackBtn());
    }

    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }

    void ExpirationDateInput()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        ArrayList<String> years = new ArrayList<>();
        for(int i = year - 10; i <= year; i++)
            years.add(String.valueOf(i));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.instance, android.R.layout.simple_spinner_dropdown_item, years);
        //set the spinners adapter to the previously created one.
        ExpirationDate.setAdapter(adapter);
    }
    void UseStatusInput()
    {
        String[] statuses = {"Hiệu lực", "Không hiệu lực"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.instance, android.R.layout.simple_spinner_dropdown_item, statuses);
        //set the spinners adapter to the previously created one.
        UseStatus.setAdapter(adapter);
    }
    void UpdateLibraryCardButton(){
        BookstoreProjectDatabase.UpdateLibraryCard(libraryCard, UseStatus.getSelectedItem().toString().equals("Hiệu lực"), ExpirationDate.getSelectedItem().toString());
        getFragmentManager().popBackStack();
    }
}