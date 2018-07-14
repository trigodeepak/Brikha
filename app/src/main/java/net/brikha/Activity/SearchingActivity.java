package net.brikha.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;



import net.brikha.Adapters.DisplayBabyNameAdapter;
import net.brikha.Model.BabyName;
import net.brikha.R;

import java.util.ArrayList;
import java.util.List;

import static net.brikha.Activity.MainActivity.PassInfo;
import static net.brikha.Adapters.DisplayBabyNameAdapter.test;
import static net.brikha.Fragment.ListOfNamesFragment.babyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.fbabyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.mbabyNameList;

public class SearchingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private DisplayBabyNameAdapter mAdapter;
    Toolbar toolbar;
    SearchView searchView;

    //todo ui freeze at clicking maybe because of ad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        Log.d("Brikha","Inside search activity");
        Log.d("Brikha", "The value"+String.valueOf(babyNameList.size()));


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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
                final String[] grpname = new String[3];
                grpname[0] = "Boy";
                grpname[1] = "Girl";
                grpname[2] = "Both";
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setIcon(R.drawable.ic_gender);
                alt_bld.setTitle("Choose a Gender");
                alt_bld.setSingleChoiceItems(grpname, -1, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        changeList(item);
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
        List<BabyName> newList = new ArrayList<>();
        for(BabyName babyName:babyNameList){
            if(babyName.getName().toLowerCase().contains(userInput)){
                newList.add(babyName);
            }
        }
        mAdapter.updateList(newList);
        mAdapter.notifyDataSetChanged();
        return true;
    }

    public void changeList(int item){
        switch (item){
            case 0:mAdapter.updateList(mbabyNameList);  test = 6; break;
            case 1:mAdapter.updateList(fbabyNameList); test = 5; break;
            case 2:mAdapter.updateList(babyNameList); test = 3;break;
        }
        mAdapter.notifyDataSetChanged();
        try {
            recyclerView.getLayoutManager().scrollToPosition(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
