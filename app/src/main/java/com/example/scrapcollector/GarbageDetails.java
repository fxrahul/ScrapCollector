package com.example.scrapcollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GarbageDetails extends AppCompatActivity {
    String area="";
    String j_string="";
    String check_plastic="";
    String  check_paper="";
    String check_metal="";
    String JSON_STRING="";
    ArrayList<String> quantity=new ArrayList<>();
    ArrayList<String> code=new ArrayList<>();
    ArrayList<String> waste_type=new ArrayList<>();
    ArrayList<String> name=new ArrayList<>();
    JSONObject jsonObject;
    JSONArray jsonArray;
    DatabaseReference databaseGarbage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage_details);
        area=getIntent().getExtras().getString("area");
        databaseGarbage = FirebaseDatabase.getInstance().getReference("garbage");
        Query query =  databaseGarbage.orderByChild("locality").equalTo(area);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PostGarbage postGarbage = childSnapshot.getValue(PostGarbage.class);
                    if((postGarbage.getLocked()).equals("0")) {
                        quantity.add(postGarbage.getQuantity());
                        code.add(postGarbage.getCode());
                        name.add(postGarbage.getName());
                        check_plastic = postGarbage.getPlastic();
                        check_paper = postGarbage.getPaper();
                        check_metal = postGarbage.getMetal();
                        if ((check_plastic + check_paper + check_metal).equals("100")) {
                            waste_type.add("plastic");
                        } else if ((check_plastic + check_paper + check_metal).equals("010")) {
                            waste_type.add("paper");
                        } else if ((check_plastic + check_paper + check_metal).equals("001")) {
                            waste_type.add("metal");
                        } else if ((check_plastic + check_paper + check_metal).equals("110")) {
                            waste_type.add("plastic paper");
                        } else if ((check_plastic + check_paper + check_metal).equals("101")) {
                            waste_type.add("plastic metal");
                        } else if ((check_plastic + check_paper + check_metal).equals("011")) {
                            waste_type.add("paper metal");
                        } else {
                            waste_type.add("plastic paper metal");
                        }

                    }
                }

                ListAdapter mylistadapter=new CustomAdapter(GarbageDetails.this,quantity,code,name,waste_type);
                ListView mylistview=(ListView) findViewById(R.id.mylistview);
                mylistview.setAdapter(mylistadapter);
                mylistview.setOnItemClickListener(
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String code=String.valueOf(parent.getItemAtPosition(position));
                                Intent i =new Intent(GarbageDetails.this,AcceptOrReject.class);
                                i.putExtra("code",code);
                                startActivity(i);
                            }
                        }
                );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
