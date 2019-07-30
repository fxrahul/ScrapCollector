package com.example.scrapcollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PostGarbageDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText et_name, et_address, et_pincode, et_contact, et_quantity;
    private CheckBox chk_paper, chk_plastic, chk_metal;
    private Spinner spn_locality;

    private Button btnGo;
    private TextView logout;

    private String name, address, pincode, contact, quantity, locality, paper, plastic, metal, code, username;
    private int pap, pla, met;
    private int unique;

    ArrayAdapter adapter;

    private FirebaseAuth auth;
    DatabaseReference databaseGarbage,databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_garbage_details);
        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseGarbage = FirebaseDatabase.getInstance().getReference("garbage");
        et_name = (EditText) findViewById(R.id.et_name);
        et_address = (EditText) findViewById(R.id.et_address);
        et_pincode = (EditText) findViewById(R.id.et_pincode);
        et_contact = (EditText) findViewById(R.id.et_contact);
        et_quantity = (EditText) findViewById(R.id.et_quantity);
        spn_locality = (Spinner) findViewById(R.id.spn_locality);
        chk_paper = (CheckBox) findViewById(R.id.chk_paper);
        chk_plastic = (CheckBox) findViewById(R.id.chk_plastic);
        chk_metal = (CheckBox) findViewById(R.id.chk_metal);
        btnGo = (Button) findViewById(R.id.postDetails);
        logout = (TextView) findViewById(R.id.logout);


        //setting adapter
        adapter = ArrayAdapter.createFromResource(this, R.array.list_locality, R.layout.support_simple_spinner_dropdown_item);
        spn_locality.setAdapter(adapter);
        spn_locality.setOnItemSelectedListener(PostGarbageDetails.this);

        //getting value from the intent



        //generating unique code
        double randomDouble = Math.random();
        unique = (int) (randomDouble * 1000000);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDetail();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(PostGarbageDetails.this,"Log Out Successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PostGarbageDetails.this,MainActivity.class));
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        TextView seltdView = (TextView) view;
        locality = seltdView.getText().toString();
        // Toast.makeText(this,locality,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void postDetail() {
        //generating unique code
        double randomDouble = Math.random();
        unique = (int) (randomDouble * 10000);


        if ((et_name.getText().toString().trim().equals("")) || (et_address.getText().toString().trim().equals("")) || (et_pincode.getText().toString().trim().equals("")) || (et_contact.getText().toString().trim().equals("")) || (et_quantity.getText().toString().trim().equals(""))) {

            Toast.makeText(this, "please fill the details", Toast.LENGTH_SHORT).show();
        } else {
            username = getIntent().getStringExtra("username");
            Log.d("garbageUsername",username);
            name = et_name.getText().toString();
            address = et_address.getText().toString();
            pincode = et_pincode.getText().toString();
            contact = et_contact.getText().toString();
            quantity = et_quantity.getText().toString() + " " + "Kg";
            code = Integer.toString(unique);

            if (chk_paper.isChecked()) {
                paper = "1";
            } else
                paper = "0";

            if (chk_plastic.isChecked()) {
                plastic = "1";
            } else
                plastic = "0";
            if (chk_metal.isChecked()) {
                metal = "1";
            } else {
                metal = "0";
            }
        }

        if ((et_name.getText().toString().trim().equals("")) ||(et_address.getText().toString().trim().equals(""))||(et_pincode.getText().toString().trim().equals(""))|| (et_contact.getText().toString().trim().equals(""))|| (et_quantity.getText().toString().trim().equals(""))){

            Toast.makeText(this,"please fill the details",Toast.LENGTH_SHORT).show();
        }else {
            String id = databaseGarbage.push().getKey();
            String locked = "0";
            PostGarbage postGarbage = new PostGarbage(id, locked, username, name, address, locality, pincode, code, contact, paper, plastic, metal, quantity);
            databaseGarbage.child(id).setValue(postGarbage);
            Toast.makeText(PostGarbageDetails.this,"Details Successfully Posted, Soon Collector will reach your Home!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(PostGarbageDetails.this,PostGarbageDetails.class));
        }

    }



}
