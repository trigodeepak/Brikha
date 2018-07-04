package com.example.deepak.brikha.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deepak.brikha.Model.BabyName;
import com.example.deepak.brikha.R;
import com.example.deepak.brikha.Activity.ShowDetailsActivity;
import com.google.android.gms.ads.InterstitialAd;


import java.util.List;

public class DispalyBabyNameAdapter extends RecyclerView.Adapter<DispalyBabyNameAdapter.MyViewHolder> {

    private List<BabyName> babyNameList;
    private InterstitialAd mInterstitial;
    private boolean test;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,gender;
        public MyViewHolder(final View view) {
            super(view);
//            mInterstitial = new InterstitialAd(view.getContext());
//            mInterstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//            AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
//            mInterstitial.loadAd(request);
            name = (TextView) view.findViewById(R.id.tv_name);
            gender = (TextView) view.findViewById(R.id.tv_gender);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (mInterstitial.isLoaded()){
////                        mInterstitial.show();
//                        mInterstitial.setAdListener(new AdListener() {
//                        @Override
//                        public void onAdClosed() {
                            // Step 2.1: Load another ad
//                            AdRequest adRequest = new AdRequest.Builder()
//                                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                                    .build();
//                            mInterstitial.loadAd(adRequest);

//                            Context context = view.getContext();
//                            Intent myIntent = new Intent(context, ShowDetailsActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("object", babyNameList.get(getLayoutPosition()));
//                            myIntent.putExtras(bundle);
//                            context.startActivity(myIntent);
//                        }
//                    });}
//
//                    // If it has not loaded due to any reason simply load the next activity
//                    else {
                        Context context = view.getContext();
                        Intent myIntent = new Intent(context, ShowDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("Index", (getLayoutPosition()));
                        myIntent.putExtras(bundle);
                        context.startActivity(myIntent);
//                }
                }
            });
        }

    }

    public DispalyBabyNameAdapter(List<BabyName> babyNameList,boolean i) {
        this.babyNameList = babyNameList;
        this.test = i;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_baby_name, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BabyName babyName = babyNameList.get(position);
        if (babyName.getName().length()>1){
        holder.name.setText(babyName.getName().substring(0, 1).toUpperCase()+babyName.getName().substring(1));}
        if (babyName.getIs_boy()){
            holder.name.setTextColor(Color.parseColor("#0A3DAA"));
            if (test){
            holder.gender.setText("Boy");}
            else
            holder.gender.setText(babyName.getSyriac());}
        else {
            holder.name.setTextColor(Color.parseColor("#FF26CE"));
            if (test){
                holder.gender.setText("Girl");}
            else
                holder.gender.setText(babyName.getSyriac());
        }
    }

    @Override
    public int getItemCount() {
        return babyNameList.size();
    }
}
