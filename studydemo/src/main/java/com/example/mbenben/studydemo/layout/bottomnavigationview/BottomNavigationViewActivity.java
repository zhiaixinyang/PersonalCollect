package com.example.mbenben.studydemo.layout.bottomnavigationview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.mbenben.studydemo.R;

import static android.R.attr.fragment;

/**
 * Created by MBENBEN on 2017/2/8.
 */

public class BottomNavigationViewActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TextFragment textFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigationview);
        FragmentManager fragmentManager = getSupportFragmentManager();
        textFragment=new TextFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, textFragment);
        transaction.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         String text;
        switch (item.getItemId()) {
            case R.id.recents:
                text = "这是第一个Fragment";
                break;
            case R.id.favourites:
                text = "这是第二个Fragment";
                break;
            case R.id.nearby:
                text = "这是第三个Fragment";
                break;
            default:
                return false;
        }
        switchFragmentText(text);
        return true;
    }

    private void switchFragmentText(String text) {
        textFragment.setText(text);
    }
}
