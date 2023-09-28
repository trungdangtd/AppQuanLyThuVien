package edu.huflit.myapplication4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import edu.huflit.myapplication4.Fragment.CartFragment;
import edu.huflit.myapplication4.Fragment.HomePageFragment;
import edu.huflit.myapplication4.Fragment.LoginFragment;
import edu.huflit.myapplication4.Fragment.NotificationFragment;

public class MainActivity extends AppCompatActivity {

    public static Context instance;

    public BottomNavigationView menuBNV;
    // Other
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public Fragment currentFragment;


    public int amount = 0;
    public static final int maxAmount = 3;
    public Boolean isLogin;
    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLogin = false;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if(instance == null)
            instance = this;

        GetIDPalletes();
        SetPalletes();
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
                currentFragment = new LoginFragment();
                break;
            case R.id.suggest:

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

}