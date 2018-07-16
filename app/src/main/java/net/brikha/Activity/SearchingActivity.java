package net.brikha.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import net.brikha.Adapters.DisplayBabyNameAdapter;
import net.brikha.Model.BabyName;
import net.brikha.R;

import java.util.ArrayList;
import java.util.List;

import static net.brikha.Activity.MainActivity.PassInfo;
import static net.brikha.Fragment.ListOfNamesFragment.babyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.fbabyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.mbabyNameList;

public class SearchingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private DisplayBabyNameAdapter mAdapter;
    Toolbar toolbar;
    SearchView searchView;
    public static List<BabyName> searchBabyNameList,OriginalList;
    public final int[] fragNo = new int[1];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Search activity worked
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        searchBabyNameList = babyNameList;
        OriginalList = babyNameList;
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new DisplayBabyNameAdapter(searchBabyNameList,3);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.expandActionView();
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter:
                final String[] GroupName = new String[3];
                GroupName[0] = "Boy";
                GroupName[1] = "Girl";
                GroupName[2] = "Both";
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setIcon(R.drawable.ic_gender);
                alt_bld.setTitle("Choose a Gender");
                alt_bld.setSingleChoiceItems(GroupName, -1, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int ind) {
                        fragNo[0] = ind;
                        changeList(ind);
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = alt_bld.create();
                alert.show();
                return true;

            case android.R.id.home:
                finish();
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
        searchBabyNameList = new ArrayList<>();
        Log.d("Brikha", String.valueOf(OriginalList.size()));
        for(BabyName babyName:OriginalList){
            if(babyName.getName().toLowerCase().contains(userInput)){
                searchBabyNameList.add(babyName);
            }
        }
        mAdapter.updateList(searchBabyNameList);
        mAdapter.notifyDataSetChanged();
        return true;

    }

    public void changeList(int item){
        //todo use any other way to change the test variable or just override some interface have onclick listener
        switch (item){
            case 0: searchBabyNameList = (mbabyNameList); OriginalList = mbabyNameList;break;
            case 1: searchBabyNameList = (fbabyNameList); OriginalList = fbabyNameList;break;
            case 2: searchBabyNameList = (babyNameList); OriginalList = mbabyNameList;break;
        }
        mAdapter.updateList(searchBabyNameList);
        mAdapter.notifyDataSetChanged();
        try {
            recyclerView.getLayoutManager().scrollToPosition(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
