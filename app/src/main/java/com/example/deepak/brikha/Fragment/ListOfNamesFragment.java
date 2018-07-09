package com.example.deepak.brikha.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepak.brikha.Activity.MainActivity;
import com.example.deepak.brikha.Model.BabyName;
import com.example.deepak.brikha.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListOfNamesFragment extends Fragment {

    public static List<BabyName> babyNameList, mbabyNameList, fbabyNameList,searchbabyNameList;

    public static OnListClickListener mListClickListener;

    public interface OnListClickListener{
        void OnListSelected(int position,int fragmentNumber);
    }

    public ListOfNamesFragment(){
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //todo make this function in adapter using the fragment .replace
            mListClickListener = (OnListClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_baby_name,container,false);

        return rootView;
    }
}
