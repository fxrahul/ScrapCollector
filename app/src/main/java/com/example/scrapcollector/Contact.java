package com.example.scrapcollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Contact extends AppCompatActivity {
    String code="";
    String contact="";
    String j_url="";
    String message="";
    String j_string="";
    String JSON_STRING="";
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        code=getIntent().getExtras().getString("code");
        contact=getIntent().getExtras().getString("number");
        TextView codee=(TextView)findViewById(R.id.unique_code);
        TextView contactt=(TextView)findViewById(R.id.contact);
        codee.setText(code);
        contactt.setText(contact);
        Toast.makeText(Contact.this,"Successfully Accepted, You can Collect Scarp Later!!",Toast.LENGTH_LONG).show();;
    }
}
