package com.example.deepak.brikha.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.deepak.brikha.Adapters.DisplayBabyNameAdapter;
import com.example.deepak.brikha.Model.BabyName;
import com.example.deepak.brikha.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.deepak.brikha.Fragment.ListOfNamesFragment.babyNameList;

public class SearchingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private DisplayBabyNameAdapter mAdapter;
    Toolbar toolbar;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        Log.d("Brikha","Inside search activity");
        Log.d("Brikha", "The value"+String.valueOf(babyNameList.size()));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new DisplayBabyNameAdapter(babyNameList,3);
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
        //todo app crash while going back
        menuItem.expandActionView();
        searchView = (SearchView) menuItem.getActionView();
//        searchView.setIconifiedByDefault(true);
//        searchView.setFocusable(true);
//        searchView.setIconified(false);
//        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter:
//                final Dialog dialog = new Dialog(this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.radiobutton_dialog);
                Rect displayRectangle = new Rect();
                Window window = this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                LayoutInflater inflater = LayoutInflater.from(this);
                View builderView = inflater.inflate(R.layout.radiobutton_dialog, null);
                builderView.setMinimumWidth((int) (displayRectangle.width() * 0.8f));
                builderView.setMinimumHeight((int) (displayRectangle.height() * 0.8f));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(builderView);
                AlertDialog alert = builder.create();
                alert.show();
//                dialog.show();
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
        List<BabyName> newList = new ArrayList<>();
        for(BabyName babyName:babyNameList){
            if(babyName.getName().toLowerCase().contains(userInput)){
                newList.add(babyName);
            }
        }
        mAdapter.updateList(newList);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }
}
