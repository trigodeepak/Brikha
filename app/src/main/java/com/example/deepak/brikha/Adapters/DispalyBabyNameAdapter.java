package com.example.deepak.brikha.Adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deepak.brikha.Activity.MainActivity;
import com.example.deepak.brikha.Fragment.ListOfNamesFragment;
import com.example.deepak.brikha.Model.BabyName;
import com.example.deepak.brikha.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.turingtechnologies.materialscrollbar.INameableAdapter;


import java.util.List;

import static com.example.deepak.brikha.Fragment.ListOfNamesFragment.fbabyNameList;
import static com.example.deepak.brikha.Fragment.ListOfNamesFragment.mbabyNameList;

public class DispalyBabyNameAdapter extends RecyclerView.Adapter<DispalyBabyNameAdapter.MyViewHolder> implements INameableAdapter {

    private List<BabyName> babyNameList;
    private InterstitialAd mInterstitial;
    private final int test;
    Typeface font;

    @Override
    public Character getCharacterForElement(int element) {
        try {
            Character c;
            switch (test) {
                case 0 : c = babyNameList.get(element).getName().charAt(0); break;
                case 1 : c = fbabyNameList.get(element).getName().charAt(0); break;
                case 2 : c = mbabyNameList.get(element).getName().charAt(0); break;
                default: c = '0';
            }
            if (Character.isDigit(c)) {
                c = '#';
            }
            return c;
        }
        catch (Exception e){
        }
        Log.d("Issue with Names",babyNameList.get(element).getName());
        return '#';
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,gender;
        public MyViewHolder(final View view) {
            super(view);
            font = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/syriac_font.ttf");
            mInterstitial = new InterstitialAd(view.getContext());
            mInterstitial.setAdUnitId("ca-app-pub-3863741641307399/5016282917");
            AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            mInterstitial.loadAd(request);
            name =  view.findViewById(R.id.tv_name);
            gender = view.findViewById(R.id.tv_gender);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mInterstitial.isLoaded()){
                        mInterstitial.show();
                        mInterstitial.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                AdRequest adRequest = new AdRequest.Builder()
                                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                                        .build();
                                mInterstitial.loadAd(adRequest);
                                ListOfNamesFragment.mListClickListener.OnListSelected(getLayoutPosition(),test);

                            }
                        });}

                    // If it has not loaded due to any reason simply load the next activity
                    else {
                        ListOfNamesFragment.mListClickListener.OnListSelected(getLayoutPosition(),test);

                    }
                }

            });
        }

    }

    public DispalyBabyNameAdapter(List<BabyName> babyNameList,int i) {
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
            if (test==0){
                holder.gender.setText("Boy");}
            else{
                holder.gender.setTypeface(font);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.gender.setLetterSpacing((float) .02);
                }
                holder.gender.setTextSize(30);
                holder.gender.setText(babyName.getSyriac());}}
        else {
            holder.name.setTextColor(Color.parseColor("#FF26CE"));
            if (test==0){
                holder.gender.setText("Girl");}
            else {
                holder.gender.setTypeface(font);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.gender.setLetterSpacing((float) .02);
                }holder.gender.setTextSize(30);
                holder.gender.setText(babyName.getSyriac());
            }
        }
    }

    @Override
    public int getItemCount() {
        return babyNameList.size();
    }
}
