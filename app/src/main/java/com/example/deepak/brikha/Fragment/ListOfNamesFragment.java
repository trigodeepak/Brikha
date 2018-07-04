package com.example.deepak.brikha.Fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepak.brikha.R;

import java.util.ArrayList;
import java.util.List;

public class ListOfNamesFragment extends Fragment {

    AllGenderFragment Fragment1;
    MaleFragment Fragment3;
    FemaleFragment Fragment2;

    public ListOfNamesFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_view_pager,container,false);
        Fragment1 = new AllGenderFragment();
        Fragment2 = new FemaleFragment();
        Fragment3 = new MaleFragment();

        ViewPager viewPager =  rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        TabLayout tabs = rootView.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
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


}
