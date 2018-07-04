package com.example.deepak.brikha;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepak.brikha.Adapters.DispalyBabyNameAdapter;
import com.google.android.gms.ads.AdView;


public class AllGenderFragment extends Fragment {

    private RecyclerView recyclerView;
    private DispalyBabyNameAdapter mAdapter;
    private AdView mAdView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        mAdapter = new DispalyBabyNameAdapter(MainActivity.babyNameList,true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//        mAdView = view.findViewById(R.id.adView);
////        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
//
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("5A25B8B9D5F1512992AC6126F632ED83")
//                .build();
//
//        mAdView.loadAd(adRequest);

        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
