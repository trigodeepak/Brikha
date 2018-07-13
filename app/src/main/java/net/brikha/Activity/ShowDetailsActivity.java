package net.brikha.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import net.brikha.Fragment.NameDetailsFragment;
import net.brikha.R;

import java.io.Serializable;
import static net.brikha.Fragment.ListOfNamesFragment.babyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.fbabyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.historybabyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.mbabyNameList;

public class ShowDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        if(findViewById(R.id.switch_to_main)!=null){
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = new Bundle();
        bundle.putInt("Index",MainActivity.PassInfo[0]);
        switch (MainActivity.PassInfo[1]) {
            case 0:bundle.putSerializable("BabyNameList", (Serializable) babyNameList); break;
            case 5:
            case 1:bundle.putSerializable("BabyNameList", (Serializable) fbabyNameList); break;
            case 6:
            case 2:bundle.putSerializable("BabyNameList", (Serializable) mbabyNameList); break;
            case 3:bundle.putSerializable("BabyNameList",(Serializable) babyNameList); break;
            case 4:bundle.putSerializable("BabyNameList",(Serializable) historybabyNameList); break;
        }

        NameDetailsFragment nameDetailsFragment = new NameDetailsFragment();
        nameDetailsFragment.setArguments(bundle);
        FragmentManager fragmentManager1 = getSupportFragmentManager();
        fragmentManager1.beginTransaction().replace(R.id.list_fragment, nameDetailsFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
