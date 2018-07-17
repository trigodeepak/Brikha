package net.brikha.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.brikha.Adapters.DisplayBabyNameAdapter;
import net.brikha.Model.BabyName;
import net.brikha.ObjectSerializer;
import net.brikha.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static net.brikha.Activity.MainActivity.HISTORYLIST;
import static net.brikha.Activity.MainActivity.SHARED_PREFS_FILE;
import static net.brikha.Fragment.ListOfNamesFragment.historybabyNameList;

public class HistoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private DisplayBabyNameAdapter mAdapter;
    Toolbar toolbar;
    public static List<BabyName> historySearchList;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        historySearchList = historybabyNameList;

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("History");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new DisplayBabyNameAdapter(historySearchList,4);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.info:
                Intent intent = new Intent(this,Info_activity.class);
                startActivity(intent);
                return true;
            case R.id.clear:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Delete History");
                builder1.setMessage("Are you sure to delete entire history ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                historybabyNameList.clear();
                                historySearchList = historybabyNameList;
                                mAdapter.updateList(historySearchList);
                                mAdapter.notifyDataSetChanged();
                                SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                try {
                                    editor.putString(HISTORYLIST, ObjectSerializer.serialize((Serializable) historybabyNameList));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                editor.apply();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        historySearchList = new ArrayList<>();
        for(BabyName babyName:historybabyNameList){
            if(babyName.getName().toLowerCase().contains(userInput)){
                historySearchList.add(babyName);
            }
        }
        mAdapter.updateList(historySearchList);
        mAdapter.notifyDataSetChanged();
        return true;
    }
}
