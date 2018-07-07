package com.example.deepak.brikha.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.deepak.brikha.Model.BabyName;
import com.example.deepak.brikha.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class NameDetailsFragment extends Fragment {
    BabyName babyName;
    private int index;
    private List<BabyName> BabyNameList;
    private AdView mAdView;

    TextView name,pronun,meaning,arabic,arabic_meaning,syriac;
    TextView prev,next;
    public NameDetailsFragment(){
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_show_details_name,container,false);

//        index = MainActivity.PassInfo[0];
        index = 0;
        BabyNameList = new ArrayList<>();
        Bundle b = getArguments();
        if(b!=null){
        BabyNameList = (List<BabyName>) b.getSerializable("BabyNameList");
        index = b.getInt("Index");
        }

        mAdView = rootView.findViewById(R.id.adView);
//        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mAdView.loadAd(adRequest);

        name = rootView.findViewById(R.id.tv_name);
        pronun = rootView.findViewById(R.id.tv_pron);
        meaning = rootView.findViewById(R.id.tv_meaning);
        arabic = rootView.findViewById(R.id.tv_arabic);
        arabic_meaning = rootView.findViewById(R.id.tv_arabic_meaning);
        syriac = rootView.findViewById(R.id.tv_syriac);

        refreshTextViews();
        next = rootView.findViewById(R.id.next_button);
        prev = rootView.findViewById(R.id.prev_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index>= BabyNameList.size()){
                    index-=BabyNameList.size();
                }
                refreshTextViews();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if (index<0){
                    index = BabyNameList.size()-1;
                }
                refreshTextViews();
            }
        });

        return rootView;
    }

    void refreshTextViews(){
        if (BabyNameList.size()>0){
            babyName = BabyNameList.get(index);}
        Log.d("Size"," "+BabyNameList.size());
        if(babyName!=null) {
            name.setText(babyName.getName());
            String sourceString = "Pronunciation : " + "<i>/" + babyName.getPronunciation() + "/</i> ";
            pronun.setText(Html.fromHtml(sourceString));
            meaning.setText("Meaning : " + babyName.getMeaning());
            arabic.setText("الكتابة باللغة العربية : " + babyName.getArabic());
            syriac.setText(babyName.getSyriac());
            arabic_meaning.setText("المعنى : " + babyName.getArabicMeaning());
            if (babyName.getIs_boy()) {
                name.setTextColor(Color.parseColor("#0a3daa"));
                syriac.setTextColor(Color.parseColor("#0a3daa"));
            } else {
                name.setTextColor(Color.parseColor("#ff26ce"));
                syriac.setTextColor(Color.parseColor("#ff26ce"));
            }
        }

    }

}
