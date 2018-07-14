package net.brikha.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import net.brikha.Adapters.DisplayBabyNameAdapter;
import net.brikha.R;

import static net.brikha.Fragment.ListOfNamesFragment.babyNameList;


public class AllGenderFragment extends Fragment {

    private RecyclerView recyclerView;
    private DisplayBabyNameAdapter mAdapter;
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

        mAdapter = new DisplayBabyNameAdapter(babyNameList,0);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        ((DragScrollBar)view.findViewById(R.id.drag_scroll_bar)).setIndicator(new AlphabetIndicator(view.getContext()), true);;


        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
