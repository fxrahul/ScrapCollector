package com.example.scrapcollector;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;

public class SelectArea extends AppCompatActivity {

    ArrayList<String> location= new ArrayList<>();

    ProgressDialog progressDialog;
    String Location="";
    String json_string="";
    String JSON_STRING="";
    JSONObject jsonObject;
    JSONArray jsonArray;
    Spinner mySpinner;
    DatabaseReference databaseGarbage;
    Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_area);
        databaseGarbage = FirebaseDatabase.getInstance().getReference("garbage");
        mySpinner=(Spinner)findViewById(R.id.select_area);
        go = (Button) findViewById(R.id.find_garbage);
        location.add("Select Area");
        Query query = databaseGarbage.orderByChild("locality");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PostGarbage postGarbage = childSnapshot.getValue(PostGarbage.class);
                    location.add(postGarbage.getLocality());
                }
                Log.d("locality",location.toString());
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectArea.this, android.R.layout.simple_spinner_item, location); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mySpinner.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String area = mySpinner.getSelectedItem().toString();
                Log.d("selectedArea",area);
                Intent i = new Intent(SelectArea.this,GarbageDetails.class);
                i.putExtra("area",area);
                startActivity(i);
            }
        });
    }
}
