package net.brikha.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;


import net.brikha.R;

public class Info_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_activity);
        //To set link to the text
        TextView dev_info = findViewById(R.id.dev_info);
        String value = "<html>Developer : <a href=\"https://github.com/trigodeepak/\">Deepak Yadav </a></html>";
        dev_info.setText(Html.fromHtml(value));
        dev_info.setMovementMethod(LinkMovementMethod.getInstance());

        value = "<html>This app shows baby name and their meaning.\n <a href=\"https://brikha.net/\">Visit our Website</a></html>";
        TextView text = (TextView) findViewById(R.id.info_text);
        text.setText(Html.fromHtml(value));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
