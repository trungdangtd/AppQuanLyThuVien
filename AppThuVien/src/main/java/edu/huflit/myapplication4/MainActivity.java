package edu.huflit.myapplication4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.Entity.Nofication;
import edu.huflit.myapplication4.Fragment.AccountFragment;
import edu.huflit.myapplication4.Fragment.CartFragment;
import edu.huflit.myapplication4.Fragment.HomePageFragment;
import edu.huflit.myapplication4.Fragment.LoginFragment;
import edu.huflit.myapplication4.Fragment.ManageListFragment;
import edu.huflit.myapplication4.Fragment.NotificationFragment;
import edu.huflit.myapplication4.Fragment.SuggestFragment;

public class MainActivity extends AppCompatActivity {

    // Singleton
    public static MainActivity instance;
    // Tên của Pallete
    public BottomNavigationView menuBNV;
    // Other
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public Fragment currentFragment;

    public ArrayList<Book> bookCart;
    public int amount = 0;
    public static final int maxAmount = 3;
    public Boolean isLogin;
    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookCart = new ArrayList<>();
        isLogin = false;
        if(instance == null)
            instance = this;
        BookstoreProjectDatabase.ConnectToFirestoreDB();
        GetIDPalletes();
        SetPalletes();
        // Ẩn ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        ReplaceFragment(R.id.home);
    }

    // Gọi các pallete có trong layout
    void GetIDPalletes()
    {
        menuBNV = findViewById(R.id.MenuBottomNavigation);
    }

    // Gán chức năng cho các pallete
    void SetPalletes()
    {
        // menu bottom
        menuBNV.setOnItemSelectedListener(item ->
        {
            ReplaceFragment(item.getItemId());
            return true;
        });


    }

    // Thay đổi giao diện fragment trong activity
    @SuppressLint("NonConstantResourceId")
    public void ReplaceFragment(int idFrag)
    {
        switch (idFrag)
        {
            case R.id.home:
                currentFragment = new HomePageFragment();
                break;
            case R.id.account:
                if(!isLogin)
                    currentFragment = new LoginFragment();
                else {
                    if( BookstoreProjectDatabase.accountInfo.getRole().equals("Sinh viên"))
                        currentFragment = new AccountFragment();
                    else if(BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý") || BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ kho")  || BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ thư"))
                        currentFragment = new ManageListFragment();
                }
                break;
            case R.id.suggest:
                currentFragment = new SuggestFragment();
                break;
            case R.id.nofi:
                currentFragment = new NotificationFragment();
                break;
            case R.id.cart:
                currentFragment = new CartFragment();
                break;
            default:
                break;
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainFrame, currentFragment);
        fragmentTransaction.addToBackStack(currentFragment.toString());
        fragmentTransaction.commitAllowingStateLoss();
    }
    private Handler timerHandler = new Handler();
    private boolean shouldRun = true;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (shouldRun) {

                if (MainActivity.instance.isLogin && !TextUtils.isEmpty(BookstoreProjectDatabase.libraryCard.getId())) {
                    if (BookstoreProjectDatabase.libraryCard.getUseStatus()) {
                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);

                        String expirationDate = BookstoreProjectDatabase.libraryCard.getExpirationDate();
                        if (expirationDate.contains("/")) {
                            String[] currentDateOfCart = expirationDate.split("/");
                            String[] formattedDateSplited = formattedDate.split("/");


                                if (Integer.valueOf(currentDateOfCart[2]) < Integer.valueOf(formattedDateSplited[2])) {
                                    BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, false);
                                    BookstoreProjectDatabase.AddNofication(new Nofication("Cấm", formattedDate, "Hạn sử dụng thẻ đã vượt quá thời hạn sử dụng được", BookstoreProjectDatabase.libraryCard.getId()));
                                    BookstoreProjectDatabase.libraryCard.setUseStatus(false);
                                } else if (Integer.valueOf(currentDateOfCart[1]) < Integer.valueOf(formattedDateSplited[1])) {
                                    BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, false);
                                    BookstoreProjectDatabase.AddNofication(new Nofication("Cấm", formattedDate, "Hạn sử dụng thẻ đã vượt quá thời hạn sử dụng được", BookstoreProjectDatabase.libraryCard.getId()));
                                    BookstoreProjectDatabase.libraryCard.setUseStatus(false);
                                } else if (Integer.valueOf(currentDateOfCart[0]) < Integer.valueOf(formattedDateSplited[0])) {
                                    BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, false);
                                    BookstoreProjectDatabase.AddNofication(new Nofication("Cấm", formattedDate, "Hạn sử dụng thẻ đã vượt quá thời hạn sử dụng được", BookstoreProjectDatabase.libraryCard.getId()));
                                    BookstoreProjectDatabase.libraryCard.setUseStatus(false);
                                }
                            }

                        if (BookstoreProjectDatabase.libraryCard.getBorrowStatus()) {
                            Calendar currentCal = new GregorianCalendar();
                            Calendar currentCal1 = new GregorianCalendar();

                            String[] splitBorrowDate = BookstoreProjectDatabase.libraryCard.getDateBorrow().split("/");
                            currentCal1.setTime(new Date(Integer.valueOf(splitBorrowDate[2]) - 1900, Integer.valueOf(splitBorrowDate[1]) - 1, Integer.valueOf(splitBorrowDate[0])));

                            System.out.println("currentCal => " + currentCal.getTime());
                            System.out.println("currentCal1 => " + currentCal1.getTime());

                            System.out.println("Days= " + daysBetween(currentCal1.getTime(), currentCal.getTime()));

                            if (daysBetween(currentCal1.getTime(), currentCal.getTime()) >= 15) {
                                BookstoreProjectDatabase.UpdateLibraryCard(BookstoreProjectDatabase.libraryCard, false);
                                BookstoreProjectDatabase.AddNofication(new Nofication("Cấm", formattedDate, "Hạn sử dụng thẻ đã vượt quá thời hạn sử dụng được", BookstoreProjectDatabase.libraryCard.getId()));
                                BookstoreProjectDatabase.libraryCard.setUseStatus(false);
                            }
                        }
                    }
                }

                timerHandler.postDelayed(this, 1000);
            }
        }
    };
    //In this example, the timer is started when the activity is loaded, but this need not to be the case
    @Override
    public void onResume() {
        super.onResume();
        /* ... */
        timerHandler.postDelayed(timerRunnable, 0);
    }

    //Stop task when the user quits the activity
    @Override
    public void onPause() {
        super.onPause();
        /* ... */
        shouldRun = false;
        timerHandler.removeCallbacksAndMessages(timerRunnable);
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}