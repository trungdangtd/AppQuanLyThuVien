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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.huflit.myapplication4.Adapter.CartApdater;
import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.ObserverPattern.BorrowObserver;
import edu.huflit.myapplication4.ObserverPattern.BorrowSubject;
import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.Entity.Loan;
import edu.huflit.myapplication4.Entity.Nofication;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.ObserverPattern.Observer;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
    RecyclerView bookCartList;
    Button borrowBtn;
    TextView nofiMessage;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.instance.menuBNV.setVisibility(View.VISIBLE);
        MainActivity.instance.menuBNV.setEnabled(true);
        GetIdPalletes(view);
        SetPalletes();
        nofiMessage.setEnabled(false);
        if(MainActivity.instance.bookCart.size() == 0) {
            nofiMessage.setVisibility(View.VISIBLE);
        }
        else
            nofiMessage.setVisibility(View.INVISIBLE);
    }

    void GetIdPalletes(View view)
    {
        bookCartList = view.findViewById(R.id.cartItem);
        borrowBtn = view.findViewById(R.id.borrowbutton);
        nofiMessage = view.findViewById(R.id.message);
    }
    void notifyBorrowSuccess() {
        BorrowSubject subject = new BorrowSubject();
        // Tạo và thêm Observer vào Subject
        Observer observer = new BorrowObserver(getActivity().getApplicationContext(),"Mượn sách thành công");
        subject.attach(observer);
        // Thông báo cho tất cả các Observer
        subject.notifyObservers();
    }
    void SetPalletes()
    {
        if(MainActivity.instance.isLogin && BookstoreProjectDatabase.accountInfo.getRole().equals("Sinh viên"))
            borrowBtn.setOnClickListener(v -> BorrowBtn());
        else if(BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý") || BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ thư")) {
            borrowBtn.setVisibility(View.INVISIBLE);
            borrowBtn.setEnabled(false);
        }
        bookCartList.setLayoutManager(new LinearLayoutManager(MainActivity.instance, RecyclerView.VERTICAL, false));
        bookCartList.setAdapter(new CartApdater(getActivity().getApplicationContext(), MainActivity.instance.bookCart));
    }

    void BorrowBtn() {
        if(MainActivity.instance.bookCart.size() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Hãy chọn sách" + MainActivity.instance.amount, Toast.LENGTH_LONG).show();
            return;
        }
        else if(BookstoreProjectDatabase.libraryCard.getBorrowStatus())
        {
            Toast.makeText(getActivity().getApplicationContext(), "Hãy trả sách để mượn tiếp", Toast.LENGTH_LONG).show();
            return;
        }
        else if(!BookstoreProjectDatabase.libraryCard.getUseStatus())
        {
            Toast.makeText(getActivity().getApplicationContext(), "Hãy đến quầy và gọi trợ giúp hỗ trợ mở hiệu lực cho thẻ", Toast.LENGTH_LONG).show();
            return;
        }
        Calendar currentCal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String currentdate = dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.DATE, 15);
        String toDate = dateFormat.format(currentCal.getTime());
        for (int i = 0; i < MainActivity.instance.bookCart.size(); i++) {
            ArrayList<Copy> copyArrayList = BookstoreProjectDatabase.LoadCopiesWithBookId(MainActivity.instance.bookCart.get(i).getId(), "Còn");

            for (int j = 0; i < copyArrayList.size(); i++) {
                if (copyArrayList.get(j).getStatus().equals("Còn")) {
                    BookstoreProjectDatabase.AddLoan(new Loan(MainActivity.instance.bookCart.get(i).getId(),
                            BookstoreProjectDatabase.libraryCard.getId(),
                            copyArrayList.get(j).getId(),
                            currentdate,
                            toDate));
                    copyArrayList.get(j).setStatus("Cho mượn");
                    BookstoreProjectDatabase.UpdateBookCopy(copyArrayList.get(j));
                    MainActivity.instance.bookCart.remove( MainActivity.instance.bookCart.get(i));
                    break;
                }
            }
        }
        BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, true, BookstoreProjectDatabase.libraryCard.getUseStatus());
        notifyBorrowSuccess();
        NotifyBorrowBookSucces();
        bookCartList.getAdapter().notifyDataSetChanged();
        nofiMessage.setVisibility(View.VISIBLE);
    }

    public static void NotifyBorrowBookSucces(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        BookstoreProjectDatabase.AddNofication(new Nofication("Bình thường", formattedDate, "Mượn sách thành công", BookstoreProjectDatabase.libraryCard.getId()));
    }

}