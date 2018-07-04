package com.example.deepak.brikha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepak.brikha.Model.BabyName;

public class ShowDetailsName extends AppCompatActivity {
    BabyName babyName;
    private int index;

    TextView name,pronun,meaning,arabic,arabic_meaning,syriac;
    TextView prev,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_name);

        final Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        index = bundle.getInt("Index");

        name = findViewById(R.id.tv_name);
        pronun = findViewById(R.id.tv_pron);
        meaning = findViewById(R.id.tv_meaning);
        arabic = findViewById(R.id.tv_arabic);
        arabic_meaning = findViewById(R.id.tv_arabic_meaning);
        syriac = findViewById(R.id.tv_syriac);

        refreshTextViews();

        next = findViewById(R.id.next_button);
        prev = findViewById(R.id.prev_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index>=MainActivity.babyNameList.size()){
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    void refreshTextViews(){
        babyName = MainActivity.babyNameList.get(index);
        name.setText(babyName.getName());
        String sourceString = "Pronunciation : "+"<i>/" + babyName.getPronunciation() + "/</i> ";
        pronun.setText(Html.fromHtml(sourceString));
        meaning.setText("Meaning : "+ babyName.getMeaning());
        arabic.setText("الكتابة باللغة العربية : "+babyName.getArabic());
        syriac.setText(babyName.getSyriac());
        arabic_meaning.setText("المعنى : "+babyName.getArabicMeaning());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
