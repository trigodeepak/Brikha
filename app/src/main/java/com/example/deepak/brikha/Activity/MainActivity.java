package com.example.deepak.brikha.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deepak.brikha.Fragment.ListOfNamesFragment;
import com.example.deepak.brikha.Fragment.NameDetailsFragment;
import com.example.deepak.brikha.Model.BabyName;
import com.example.deepak.brikha.ObjectSerializer;
import com.example.deepak.brikha.R;

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


public class MainActivity extends AppCompatActivity implements ListOfNamesFragment.OnListClickListener{
    final public static String BABY_LIST= "baby_list";
    final public static String HASH_CODE= "hash_set";
    final public static String SHARED_PREFS_FILE = "BrikhasharedPref";
    final public static String LIST_FRAG = "LIST_FRAG",DETAIL="DETAIL";
    public static boolean twoPane = false;
    public final static int[] PassInfo = new int[2];
    public static List<BabyName> babyNameList, mbabyNameList, fbabyNameList,searchbabyNameList;
    public static Set<Integer> set;
    private boolean dataFetech = false;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //todo apply admob have done sample admob
        super.onCreate(savedInstanceState);
        babyNameList = new ArrayList<>();
        mbabyNameList = new ArrayList<>();
        fbabyNameList = new ArrayList<>();
        searchbabyNameList = new ArrayList<>();
        set = new HashSet<>();

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.");
        progressDialog.setCancelable(false);

        setContentView(R.layout.activity_main);
        Log.d("Error", String.valueOf(dataFetech));
        try {
            babyNameList = (List<BabyName>) ObjectSerializer.deserialize(prefs.getString(BABY_LIST, ObjectSerializer.serialize(new ArrayList<BabyName>())));
            set = (HashSet<Integer>) ObjectSerializer.deserialize(prefs.getString(HASH_CODE, ObjectSerializer.serialize(new HashSet<Integer>())));
            //todo error here
            makeMaleFemaleBabyList();
        } catch (Exception e) {
            progressDialog.show();
            SetViewPager();
            Toast.makeText(this,"Fetching data for the first time ! Please Wait",Toast.LENGTH_LONG).show();
            try {
                Log.e("Sys Error",prefs.getString(BABY_LIST,ObjectSerializer.serialize(new ArrayList<BabyName>())));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

//        if(dataFetech){
//            try {
//                new MainActivity.MyTask().execute(this);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        MobileAds.initialize(this, "ca-app-pub-5234423351540636~1347457065");
    }

    public void SetViewPager(){
        if(dataFetech) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                progressDialog.dismiss();
                Toast.makeText(this,"Data is now Fetched",Toast.LENGTH_SHORT).show();

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
        }
        else{
            try {
                Log.d("Fetching ","Fetching for the first time");
                new MainActivity.MyTask().execute(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnListSelected(int position,int fragmentNumber) {
//        Toast.makeText(this,"Position Clicked is "+position+"Fragment Number is : "+fragmentNumber,Toast.LENGTH_SHORT).show();

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
            }

            NameDetailsFragment nameDetailsFragment = new NameDetailsFragment();
            nameDetailsFragment.setArguments(bundle);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            fragmentManager1.beginTransaction().replace(R.id.display_fragment, nameDetailsFragment).commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hel",0);
    }



private class MyTask extends AsyncTask<Object, Void, String> {
    @Override
    protected String doInBackground(Object... params) {
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
        Log.d("Result",str+" ");
        parseResult(str);
//        ViewPager viewPager =  findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
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
//                URLEncoder.encode("臺北市", "utf-8")
                babyNameList.add(i,babyName);}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Flag){
            Collections.sort(babyNameList);
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
        Log.d("Error","Is this function called");
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
        if(!dataFetech) {
            dataFetech = true;
            SetViewPager();
        }
        Log.d("Setting Up View Pager","View Pager set");
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString(BABY_LIST,ObjectSerializer.serialize((Serializable) babyNameList));
            editor.putString(HASH_CODE,ObjectSerializer.serialize((Serializable) set));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

}
