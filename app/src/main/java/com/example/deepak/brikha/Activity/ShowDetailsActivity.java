package com.example.deepak.brikha.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.deepak.brikha.Fragment.NameDetailsFragment;
import com.example.deepak.brikha.R;

public class ShowDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NameDetailsFragment nameDetailsFragment = new NameDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.list_fragment, nameDetailsFragment).commit();

    }
    //todo if 2 pane layout finish the activity



}
