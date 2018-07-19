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
        String value = "<html>Brikha is the first project of its kind, to create an application and website, that's dedicated for our Assyrian baby names. Each name is presented with meaning, pronunciation and more. The project is a result of collaboration between Ninos and Sizar Hozaya. We welcome any new additions, send the name details to <a href=\"mailto:admin@brikha.net\">admin email</a> and we will include them. Our website <br /> <a href=\"https://brikha.net/\">www.brikha.net</a></html>";
        TextView text = (TextView) findViewById(R.id.info_text);
        text.setText(Html.fromHtml(value));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
