package net.brikha.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.turingtechnologies.materialscrollbar.INameableAdapter;

import net.brikha.Activity.ShowDetailsActivity;
import net.brikha.Fragment.ListOfNamesFragment;
import net.brikha.Model.BabyName;
import net.brikha.R;

import java.util.ArrayList;
import java.util.List;

import static net.brikha.Activity.MainActivity.PassInfo;

public class DisplayBabyNameAdapter extends RecyclerView.Adapter<DisplayBabyNameAdapter.MyViewHolder> implements INameableAdapter {

    private List<BabyName> babyNameList;
    private InterstitialAd mInterstitial;
    private int test;
    private Typeface font;

    @Override
    public Character getCharacterForElement(int element) {
        try {
            Character c = babyNameList.get(element).getName().charAt(0);
            if (Character.isDigit(c)) {
                c = '#';
            }
            return c;
        }
        catch (Exception ignored){
        }
        Log.d("Issue with Names",babyNameList.get(element).getName());
        return '#';
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,gender;
        public MyViewHolder(final View view) {
            super(view);
            font = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/syriac_font.ttf");
            name = view.findViewById(R.id.tv_name);
            gender = view.findViewById(R.id.tv_gender);

        }

    }

    public DisplayBabyNameAdapter(List<BabyName> babyNameList, int i) {
        this.babyNameList = babyNameList;
        this.test = i;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_baby_name, parent, false);
        mInterstitial = new InterstitialAd(itemView.getContext());
        mInterstitial.setAdUnitId("ca-app-pub-3863741641307399/5016282917");
        AdRequest request = new AdRequest.Builder().build();
        mInterstitial.loadAd(request);

        final RecyclerView.ViewHolder holder = new MyViewHolder(itemView);
        try {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Log.d("Brikha", String.valueOf(holder.getAdapterPosition()+test));
                    if (mInterstitial.isLoaded()) {
                        mInterstitial.show();
                        mInterstitial.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                AdRequest adRequest = new AdRequest.Builder()
                                        .build();
                                mInterstitial.loadAd(adRequest);
//                                if(test>=3){
//                                    callActivity(v,test,holder.getAdapterPosition());
//                                }
                                ListOfNamesFragment.mListClickListener.OnListSelected(holder.getAdapterPosition(), test);

                            }
                        });
                    }

                    // If it has not loaded due to any reason simply load the next activity
                    else {
//                        if(test>3){
//                            callActivity(v,test,holder.getAdapterPosition());
//                        }
                        ListOfNamesFragment.mListClickListener.OnListSelected(holder.getAdapterPosition(), test);

                    }
                }

            });
        }
        catch (Exception e){
            Log.e("Brikha", String.valueOf(e.getStackTrace())+" ");
            ListOfNamesFragment.mListClickListener.OnListSelected(holder.getAdapterPosition(), test);
        }

        return (MyViewHolder) holder;
    }

    private void callActivity(View v,int fragmentNumber,int position){
        Log.d("Brikha","Layout coming here");
        Intent myIntent = new Intent(v.getContext(), ShowDetailsActivity.class);
        PassInfo[0] = position;
        PassInfo[1] = fragmentNumber;
        v.getContext().startActivity(myIntent);
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

    public void updateList(List<BabyName> newList){
        babyNameList = new ArrayList<>();
        babyNameList.addAll(newList);
        notifyDataSetChanged();
    }


}
