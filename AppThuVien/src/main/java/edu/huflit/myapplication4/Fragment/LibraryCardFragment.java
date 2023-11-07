package edu.huflit.myapplication4.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.LibraryCard;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LibraryCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LibraryCardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LibraryCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LibraryCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LibraryCardFragment newInstance(String param1, String param2) {
        LibraryCardFragment fragment = new LibraryCardFragment();
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
        return inflater.inflate(R.layout.fragment_library_card, container, false);
    }

    TableLayout tbl;
    ImageView backBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.GONE);
        MainActivity.instance.menuBNV.setEnabled(false);
        BookstoreProjectDatabase.LoadLibraryCards();
        System.out.println(BookstoreProjectDatabase.libraryCards.size());

        GetIdPalletes(view);
        SetPalletes(view);
    }


    void GetIdPalletes(View view)
    {
        tbl=view.findViewById(R.id.tbl_statistics);
        backBtn=view.findViewById(R.id.backBtn);
    }

    void SetPalletes(View view)
    {
        CreateTable(view);
        backBtn.setOnClickListener(v -> BackBtn());
    }

    void BackBtn()
    {
        getFragmentManager().popBackStack();
    }

    void CreateTable(View view)
    {
        String[] nameCols = {"MSSV", "Họ và tên", "Ngày đến hạn", "Tình trạng thẻ", "Đang mượn"};
        TableRow row1 = new TableRow(getActivity());
        //row1.setGravity(Gravity.CENTER_HORIZONTAL);
        row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        for(int i = 0; i < nameCols.length; i++)
        {

            TextView txtCol = new TextView(getActivity().getApplicationContext());
            txtCol.setText(nameCols[i]);
            txtCol.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol);

        }
        tbl.addView(row1);
        //add a new line to the TableLayout:
        final View vline = new View(getActivity().getApplicationContext());

        vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        //vline.setBackgroundColor(Color.BLUE);
        tbl.addView(vline);
        for(LibraryCard rowItem : BookstoreProjectDatabase.libraryCards)
        {
            row1 = new TableRow(getActivity());
            //row1.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            //add a new row to the TableLayout

            TextView txtCol1 = new TextView(getActivity().getApplicationContext());
            txtCol1.setText(rowItem.getId());
            txtCol1.setBackgroundResource(R.drawable.cotlibrarycard);
            txtCol1.setGravity(Gravity.CENTER_HORIZONTAL);
            row1.addView(txtCol1);

            TextView txtCol2 = new TextView(getActivity().getApplicationContext());
            txtCol2.setText(rowItem.getName());
            txtCol2.setGravity(Gravity.CENTER_HORIZONTAL);
            txtCol2.setBackgroundResource(R.drawable.cotlibrarycard);
            row1.addView(txtCol2);

            TextView txtCol3 = new TextView(getActivity().getApplicationContext());
            txtCol3.setText(rowItem.getExpirationDate());
            txtCol3.setGravity(Gravity.CENTER_HORIZONTAL);
            txtCol3.setBackgroundResource(R.drawable.cotlibrarycard);
            row1.addView(txtCol3);

            TextView txtCol4 = new TextView(getActivity().getApplicationContext());
            txtCol4.setText(rowItem.getUseStatus() ? "Hiệu lực" : "Không hiệu lực");
            txtCol4.setGravity(Gravity.CENTER_HORIZONTAL);
            txtCol4.setBackgroundResource(R.drawable.cotlibrarycard);
            row1.addView(txtCol4);

            TextView txtCol5 = new TextView(getActivity().getApplicationContext());
            txtCol5.setText(rowItem.getBorrowStatus() ? "Có" : "Không");
            txtCol5.setGravity(Gravity.CENTER_HORIZONTAL);
            txtCol5.setBackgroundResource(R.drawable.cotlibrarycard);
            row1.addView(txtCol5);

            row1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Create a new PopupWindow instance
                    PopupWindow popup = new PopupWindow(v.getContext().getApplicationContext());

                    // Inflate your custom layout
                    LayoutInflater inflater = (LayoutInflater) view.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.updateordeletebox, null);

                    Button updateBtn = layout.findViewById(R.id.updateButton);
                    Button deleteBtn = layout.findViewById(R.id.deleteButton);
                    Button cancelBtn = layout.findViewById(R.id.cancelButton);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popup.dismiss();
                        }
                    });
                    updateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.instance.currentFragment = new UpdateLibraryCardFragment(rowItem);
                            MainActivity.instance.ReplaceFragment(-1);
                            popup.dismiss();
                        }
                    });

                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BookstoreProjectDatabase.DeleteLibraryCard(rowItem.getId());
                            BookstoreProjectDatabase.DeleteAccount(rowItem.getId());
                            getFragmentManager().popBackStack();
                            popup.dismiss();
                        }
                    });

                    // Set the custom layout as the content view for the popup window
                    popup.setContentView(layout);

                    // Set the width and height of the popup window
                    popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                    popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

                    // Show the popup window
                    popup.showAtLocation(view, Gravity.CENTER, 0, 0);
                    return true;
                }
            });

            tbl.addView(row1);
            //add a new line to the TableLayout:
            final View vline1 = new View(getActivity().getApplicationContext());

            vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            //vline.setBackgroundColor(Color.BLUE);
            tbl.addView(vline1);
        }
    }
}