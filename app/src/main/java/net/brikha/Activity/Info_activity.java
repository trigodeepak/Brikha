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

        value = "<html>Brikha is the first project of its kind, to create an application and website, that's dedicated for our Assyrian baby names. Each name is presented with meaning, pronunciation and more. The project is a result of collaboration between Ninos and Sizar Hozaya. We welcome any new additions, send the name details to <a href=\"mailto:admin@brikha.net\">admin email</a> and we will include them. Our website <a href=\"https://brikha.net/\">www.brikha.net</a></html>";
        TextView text = (TextView) findViewById(R.id.info_text);
        text.setText(Html.fromHtml(value));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
