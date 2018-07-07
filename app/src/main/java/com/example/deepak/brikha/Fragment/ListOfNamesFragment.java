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

import static com.example.deepak.brikha.Activity.MainActivity.babyNameList;
import static com.example.deepak.brikha.Activity.MainActivity.searchbabyNameList;

public class ListOfNamesFragment extends Fragment {

    AllGenderFragment Fragment1;
    MaleFragment Fragment3;
    FemaleFragment Fragment2;

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
            mListClickListener = (OnListClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_view_pager,container,false);

        Fragment1 = new AllGenderFragment();
        Fragment2 = new FemaleFragment();
        Fragment3 = new MaleFragment();
        ViewPager viewPager =  rootView.findViewById(R.id.viewpager);
        TabLayout tabs = rootView.findViewById(R.id.tabs);


        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        if(savedInstanceState == null) {
            setupViewPager(viewPager);
            tabs.setupWithViewPager(viewPager);

        }
        else{
            //This code will never works
            tabs.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
        }
        return rootView;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(Fragment1, "All");
        adapter.addFragment(Fragment2, "Girl");
        adapter.addFragment(Fragment3, "Boy");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater menuInflater = new MenuInflater();
//        menuInflater.inflate(R.menu.main_menu, menu);
//        super.onCreateOptionsMenu(menu, menuInflater);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.info:
//            return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public void ReverseList(List<BabyName> babyNames){
        //Reversing list works
        Collections.reverse(babyNames);
//        Fragment1 = new AllGenderFragment();
//        ViewPager viewPager =  findViewById(R.id.viewpager);
//        setupViewPager(viewPager);

    }

    public static void Searching_in_List(String s){
        //Works Seamlessly
        searchbabyNameList = new ArrayList<>();
        for(BabyName b:babyNameList){
            if(b.getName().contains(s)){
                Log.d("Find in ",b.getName());
                searchbabyNameList.add(b);
            }
        }
    }
}
