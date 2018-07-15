package net.brikha.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import net.brikha.Fragment.AllGenderFragment;
import net.brikha.Fragment.FemaleFragment;
import net.brikha.Fragment.ListOfNamesFragment;
import net.brikha.Fragment.MaleFragment;
import net.brikha.Fragment.NameDetailsFragment;
import net.brikha.Model.BabyName;
import net.brikha.ObjectSerializer;
import net.brikha.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.brikha.Fragment.ListOfNamesFragment.babyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.fbabyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.historybabyNameList;
import static net.brikha.Fragment.ListOfNamesFragment.mbabyNameList;


public class MainActivity extends AppCompatActivity implements ListOfNamesFragment.OnListClickListener{
    final public static String BABY_LIST= "baby_list";
    final public static String HASH_CODE= "hash_set";
    final public static String SHARED_PREFS_FILE = "BrikhasharedPref";
    final public static String LIST_FRAG = "LIST_FRAG",DETAIL="DETAIL",HISTORYLIST = "HISTORYLIST";
    public static boolean twoPane = false;
    public final static int[] PassInfo = new int[2];
    public static Set<Integer> set;
    private boolean isDataFetch = false;
    ProgressDialog progressDialog;
    AllGenderFragment Fragment1;
    MaleFragment Fragment3;
    FemaleFragment Fragment2;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo the layout messed up
        //todo work on ads

        babyNameList = new ArrayList<>();
        mbabyNameList = new ArrayList<>();
        fbabyNameList = new ArrayList<>();
        historybabyNameList = new ArrayList<>();
        set = new HashSet<>();


        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.");
        progressDialog.setCancelable(false);

        setContentView(R.layout.activity_main);
        Log.d("Brikha", String.valueOf(isDataFetch));
        try {
            babyNameList = (List<BabyName>) ObjectSerializer.deserialize(prefs.getString(BABY_LIST, ObjectSerializer.serialize(new ArrayList<BabyName>())));
            set = (HashSet<Integer>) ObjectSerializer.deserialize(prefs.getString(HASH_CODE, ObjectSerializer.serialize(new HashSet<Integer>())));
            historybabyNameList = (List<BabyName>) ObjectSerializer.deserialize(prefs.getString(HISTORYLIST, ObjectSerializer.serialize(new ArrayList<BabyName>())));
            if(babyNameList.size()==0){
                progressDialog.show();
                SetViewPager();
                Toast.makeText(this,"Fetching data for the first time! Please Wait",Toast.LENGTH_LONG).show();
            }
            else {
                makeMaleFemaleBabyList();
            }
        } catch (Exception e) {
            progressDialog.show();
            SetViewPager();
            Toast.makeText(this,"Fetching data for the first time! Please Wait ",Toast.LENGTH_LONG).show();

        }


        setContentView(R.layout.activity_main);
        setupViewPager();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3863741641307399~5978419919");
        AdView mAdView = findViewById(R.id.adView);


        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mAdView.loadAd(adRequest);

    }

    // Add Fragments to Tabs
    private void setupViewPager() {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        viewPager =  findViewById(R.id.viewpager);
        Fragment1 = new AllGenderFragment();
        Fragment2 = new FemaleFragment();
        Fragment3 = new MaleFragment();
        adapter.addFragment(Fragment1, "All");
        adapter.addFragment(Fragment2, "Girl");
        adapter.addFragment(Fragment3, "Boy");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void SetViewPager(){
        if(isDataFetch) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            setupViewPager();
            if (findViewById(R.id.linear_layout_tablet) != null) {
                twoPane = true;
                ListOfNamesFragment listOfNamesFragment = new ListOfNamesFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("BabyNameList", (Serializable) babyNameList);
                bundle.putInt("Index", 0);

                fragmentManager.beginTransaction().add(R.id.list_fragment, listOfNamesFragment, LIST_FRAG).commit();

                NameDetailsFragment nameDetailsFragment = new NameDetailsFragment();
                nameDetailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.display_fragment, nameDetailsFragment, DETAIL).commit();
            } else {
                twoPane = false;
                ListOfNamesFragment listOfNamesFragment = new ListOfNamesFragment();
                fragmentManager.beginTransaction().add(R.id.list_fragment, listOfNamesFragment, LIST_FRAG).commit();
            }
            progressDialog.dismiss();
        }
        else{
            try {
                new MainActivity.MyTask().execute(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnListSelected(int position,int fragmentNumber) {
        Log.d("Brikha", String.valueOf(position)+" "+String.valueOf(fragmentNumber));
        AddToHistoryList(position,fragmentNumber);
        if(!twoPane) {
            final Intent myIntent = new Intent(this, ShowDetailsActivity.class);
            PassInfo[0]= position;
            PassInfo[1] = fragmentNumber;
            startActivity(myIntent);
        }
        else{
            Bundle bundle = new Bundle();
            bundle.putInt("Index",position);
            switch (fragmentNumber) {
                case 0:bundle.putSerializable("BabyNameList", (Serializable) babyNameList); break;
                case 1:bundle.putSerializable("BabyNameList", (Serializable) fbabyNameList); break;
                case 2:bundle.putSerializable("BabyNameList", (Serializable) mbabyNameList); break;
                case 5:
                case 6:
                case 3:bundle.putSerializable("BabyNameList",(Serializable) SearchingActivity.searchBabyNameList); break;
                case 4:bundle.putSerializable("BabyNameList",(Serializable) HistoryActivity.historySearchList); break;
            }

            NameDetailsFragment nameDetailsFragment = new NameDetailsFragment();
            nameDetailsFragment.setArguments(bundle);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            fragmentManager1.beginTransaction().replace(R.id.display_fragment, nameDetailsFragment).commit();
        }

    }

    public void AddToHistoryList(int pos, int f){
        switch (f){
            case 0: checkAlreadyInHistory(babyNameList.get(pos)); historybabyNameList.add(0,babyNameList.get(pos)); break;
            case 1: checkAlreadyInHistory(fbabyNameList.get(pos)); historybabyNameList.add(0,fbabyNameList.get(pos)); break;
            case 2: checkAlreadyInHistory(mbabyNameList.get(pos)); historybabyNameList.add(0,mbabyNameList.get(pos)); break;
            case 5:
            case 6:
            case 3: checkAlreadyInHistory(SearchingActivity.searchBabyNameList.get(pos)); historybabyNameList.add(0,SearchingActivity.searchBabyNameList.get(pos)); break;
        }
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString(HISTORYLIST,ObjectSerializer.serialize((Serializable) historybabyNameList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }
    private void checkAlreadyInHistory(BabyName b){
        int i=0;
        for (BabyName name : historybabyNameList) {
            if (b.getName().equals(name.getName())) {
                historybabyNameList.remove(i);
                return ;
            }
            i++;
        }
        int size = historybabyNameList.size();
        //For deleting more than 30 entries
        while (size > 29) {
            historybabyNameList.remove(size - 1);
            size--;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.search:
//                Intent intent = new Intent(this,Main2Activity.class);
//                startActivity(intent);
//                finish();
                Intent intent = new Intent(MainActivity.this,SearchingActivity.class);
                startActivity(intent);
                return true;
            case R.id.history:
                intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.info:
                intent = new Intent(MainActivity.this,Info_activity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class MyTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            Log.d("Brikha","Data Being fetched");
            try {
                StringBuilder sb = new StringBuilder();
                URL url = new URL("https://brikha.net/app/get_data.php");
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF8"));

                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    sb.append(inputLine);
                in.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String str) {
            if(str!=null){
                parseResult(str);}
            else{
                Log.d("Brikha","Connectivity Problem");
            }
        }
    }

    public void parseResult(String FetchData) {
        // Function to take json as string and parse it into the Baby Names list
        String result = "{\"Baby_Names_List\":" + FetchData + "}";
        Boolean Flag = false;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("Baby_Names_List");
            for (int i = 0; i < jsonArray.length(); i++) {
                BabyName babyName = new BabyName();
                JSONObject reader = jsonArray.getJSONObject(i);
                int id = Integer.parseInt(reader.getString("id"));
                if (!set.contains(id)){
                    set.add(id);
                    Flag = true;
                    babyName.setName(reader.getString("name"));
                    babyName.setPronunciation(reader.getString("pronunciation"));
                    babyName.setMeaning(reader.getString("meaning"));
                    babyName.setArabic(reader.getString("arabic"));
                    babyName.setArabicMeaning(reader.getString("arabic_meaning"));
                    babyName.setSyriac(reader.getString("syriac"));
                    if (Integer.parseInt(reader.getString("is_male"))==1){
                        babyName.setIs_boy(true);
                    }else
                        babyName.setIs_boy(false);
                    babyNameList.add(i,babyName);}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Flag){
            if(isDataFetch) {
                Collections.sort(babyNameList);
            }
            makeMaleFemaleBabyList();
        }
    }

    public void makeMaleFemaleBabyList(){
        for(int i = 0;i<babyNameList.size();i++){
            if(babyNameList.get(i).getIs_boy())
                mbabyNameList.add(babyNameList.get(i));
            else
                fbabyNameList.add(babyNameList.get(i));
        }
        structureBabylist();
    }

    public void structureBabylist(){
        babyNameList = new ArrayList<>();
        Log.d("Brikha","Is this function called");
        int m_size = mbabyNameList.size(),f_size = fbabyNameList.size();
        int j=0,k=0;
        for(int i = 0;i<f_size+m_size;i++){
            if(j==m_size || k==f_size)
                break;
            if(i%2==0 && j<m_size) {
                babyNameList.add(mbabyNameList.get(j));
                j+=1;
            }
            else if (k<f_size){
                babyNameList.add(fbabyNameList.get(k));
                k+=1;
            }
        }
        while(k<f_size){
            babyNameList.add(fbabyNameList.get(k));
            k++;
        }
        while(j<m_size){
            babyNameList.add(mbabyNameList.get(j));
            j++;
        }
        storeToSharedPreferences();
    }

    public void storeToSharedPreferences(){
        if(!isDataFetch) {
            isDataFetch = true;
            if(viewPager!=null){
            SetViewPager();}
        }
        if(viewPager!=null) {
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            try {
                editor.putString(BABY_LIST, ObjectSerializer.serialize((Serializable) babyNameList));
                editor.putString(HASH_CODE, ObjectSerializer.serialize((Serializable) set));
            } catch (IOException e) {
                e.printStackTrace();
            }
            editor.apply();
        }
    }

}
