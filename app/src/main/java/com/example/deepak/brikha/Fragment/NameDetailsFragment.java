package com.example.deepak.brikha.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deepak.brikha.Activity.MainActivity;
import com.example.deepak.brikha.Model.BabyName;
import com.example.deepak.brikha.R;

public class NameDetailsFragment extends Fragment {
    BabyName babyName;
    private int index;

    TextView name,pronun,meaning,arabic,arabic_meaning,syriac;
    TextView prev,next;
    public NameDetailsFragment(){
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_show_details_name,container,false);

        index = MainActivity.PassInfo[0];

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
                if (index>= MainActivity.babyNameList.size()){
                    index-=MainActivity.babyNameList.size();
                }
                refreshTextViews();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if (index<0){
                    index = MainActivity.babyNameList.size()-1;
                }
                refreshTextViews();
            }
        });



        return rootView;
    }

    void refreshTextViews(){
        switch (MainActivity.PassInfo[1]){
            case 0: babyName = MainActivity.babyNameList.get(index); break;
            case 1: babyName = MainActivity.fbabynameList.get(index); break;
            case 2: babyName = MainActivity.mbabyNameList.get(index); break;
        }

        name.setText(babyName.getName());
        String sourceString = "Pronunciation : "+"<i>/" + babyName.getPronunciation() + "/</i> ";
        pronun.setText(Html.fromHtml(sourceString));
        meaning.setText("Meaning : "+ babyName.getMeaning());
        arabic.setText("الكتابة باللغة العربية : "+babyName.getArabic());
        syriac.setText(babyName.getSyriac());
        arabic_meaning.setText("المعنى : "+babyName.getArabicMeaning());
        if (babyName.getIs_boy()){
            name.setTextColor(Color.parseColor("#0a3daa"));
            syriac.setTextColor(Color.parseColor("#0a3daa"));
        }
        else{
            name.setTextColor(Color.parseColor("#ff26ce"));
            syriac.setTextColor(Color.parseColor("#ff26ce"));
        }

    }

}
