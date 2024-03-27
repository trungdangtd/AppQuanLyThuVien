package edu.huflit.myapplication4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import edu.huflit.myapplication4.Composite.BookBorrowCheck;
import edu.huflit.myapplication4.Composite.CardExpirationCheck;
import edu.huflit.myapplication4.Composite.LibraryComponent;
import edu.huflit.myapplication4.Composite.LibraryTaskComposite;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.Fragment.AccountFragment;
import edu.huflit.myapplication4.Fragment.CartFragment;
import edu.huflit.myapplication4.Fragment.HomePageFragment;
import edu.huflit.myapplication4.Fragment.LoginFragment;
import edu.huflit.myapplication4.Fragment.ManageListFragment;
import edu.huflit.myapplication4.Fragment.NotificationFragment;
import edu.huflit.myapplication4.Fragment.SuggestFragment;

public class MainActivity extends AppCompatActivity  {

    // Singleton
    public static MainActivity instance;

    // Views
    public BottomNavigationView menuBNV;

    // Fragment management
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public Fragment currentFragment;

    // Library task
    private LibraryComponent libraryTask;

    // Data
    public ArrayList<Book> bookCart;
    public int amount = 0;
    public static final int maxAmount = 3;
    public Boolean isLogin;

    // Lifecycle methods
    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize data
        bookCart = new ArrayList<>();
        isLogin = false;

        // Singleton instance
        if (instance == null)
            instance = this;

        // Connect to Firestore
        BookstoreProjectDatabase.ConnectToFirestoreDB();

        // Initialize views
        GetIDPalletes();

        // Set up view functionalities
        SetPalletes();

        // Hide ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Replace default fragment
        ReplaceFragment(R.id.home);

        // Initialize library task
        libraryTask = new LibraryTaskComposite();
        libraryTask.addComponent(new CardExpirationCheck());
        libraryTask.addComponent(new BookBorrowCheck());
    }

    @Override
    public void onResume() {
        super.onResume();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldRun = false;
        timerHandler.removeCallbacks(timerRunnable);
    }

    // Helper methods

    // Get IDs of views
    void GetIDPalletes() {
        menuBNV = findViewById(R.id.MenuBottomNavigation);
    }

    // Set up functionalities for views
    void SetPalletes() {
        menuBNV.setOnItemSelectedListener(item -> {
            ReplaceFragment(item.getItemId());
            return true;
        });
    }

    // Replace fragments based on menu item selected
    @SuppressLint("NonConstantResourceId")
    public void ReplaceFragment(int idFrag) {
        switch (idFrag) {
            case R.id.home:
                currentFragment = new HomePageFragment();
                break;
            case R.id.account:
                if (!isLogin)
                    currentFragment = new LoginFragment();
                else {
                    if (BookstoreProjectDatabase.accountInfo.getRole().equals("Sinh viên"))
                        currentFragment = new AccountFragment();
                    else if (BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý") || BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ kho") || BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ thư"))
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

    // Runnable for library task
    private Handler timerHandler = new Handler();
    private boolean shouldRun = true;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (shouldRun) {
                libraryTask.performTask();
                timerHandler.postDelayed(this, 1000);
            }
        }
    };
}