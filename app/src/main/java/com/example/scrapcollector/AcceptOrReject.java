package com.example.scrapcollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AcceptOrReject extends AppCompatActivity {
    private String idCopy,usernameCopy,nameCopy,addressCopy,localityCopy,pincodeCopy,codeCopy,contactCopy,paperCopy,plasticCopy,metalCopy,quantityCopy;
    private String lockedCopy = "1";
    String name="";
    String message="";
    String quantity="";
    String address="";
    String area="";
    String pincode="";
    String username="";
    String contact="";
    String code="";
    String check_plastic="";
    String  check_paper="";
    String check_metal="";
    String waste_type="";
    DatabaseReference databaseGarbage;
    Button accept,reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_reject);
        databaseGarbage = FirebaseDatabase.getInstance().getReference("garbage");
        accept = (Button) findViewById(R.id.accept);
        reject = findViewById(R.id.reject);
        code=getIntent().getExtras().getString("code");
        Query query = databaseGarbage.orderByChild("code").equalTo(code);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PostGarbage postGarbage = childSnapshot.getValue(PostGarbage.class);
                    name= postGarbage.getName();
                    address= postGarbage.getAddress();
                    pincode= postGarbage.getPincode();
                    area= postGarbage.getLocality();
                    quantity= postGarbage.getQuantity();
                    username= postGarbage.getUsername();
                    contact= postGarbage.getContact();
                    check_plastic= postGarbage.getPlastic();
                    check_paper= postGarbage.getPaper();
                    check_metal= postGarbage.getMetal();
                    if((check_plastic+check_paper+check_metal).equals("100")) {
                        waste_type="plastic";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("010")) {
                        waste_type="paper";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("001")) {
                        waste_type="metal";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("110")) {
                        waste_type="plastic paper";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("101")) {
                        waste_type="plastic metal";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("011")) {
                        waste_type="paper metal";
                    }
                    else{
                        waste_type="plastic paper metal";
                    }
                }
                TextView namee =(TextView)findViewById(R.id.name);
                TextView addresss =(TextView)findViewById(R.id.address);
                TextView quantityy=(TextView)findViewById(R.id.quantity);
                TextView areaa=(TextView)findViewById(R.id.area);
                TextView pincodee=(TextView)findViewById(R.id.pincode);
                TextView waste_typee=(TextView)findViewById(R.id.waste_type);
                namee.setText(name);
                addresss.setText(address);
                quantityy.setText(quantity);
                areaa.setText(area);
                pincodee.setText(pincode);
                waste_typee.setText(waste_type);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Query query1 = databaseGarbage.orderByChild("code").equalTo(code);
                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            PostGarbage postGarbageCopy = childSnapshot.getValue(PostGarbage.class);
                            idCopy = postGarbageCopy.getId();
                            usernameCopy = postGarbageCopy.getUsername();
                            nameCopy = postGarbageCopy.getName();
                            addressCopy = postGarbageCopy.getAddress();
                            localityCopy = postGarbageCopy.getLocality();
                            pincodeCopy = postGarbageCopy.getPincode();
                            codeCopy = postGarbageCopy.getCode();
                            contactCopy = postGarbageCopy.getContact();
                            paperCopy = postGarbageCopy.getPaper();
                            plasticCopy = postGarbageCopy.getPlastic();
                            metalCopy = postGarbageCopy.getMetal();
                            quantityCopy = postGarbageCopy.getQuantity();

                        }


                        PostGarbageCopy postGarbageCopy = new PostGarbageCopy(idCopy,lockedCopy,usernameCopy,nameCopy,addressCopy,localityCopy,pincodeCopy,codeCopy,contactCopy,paperCopy,plasticCopy,metalCopy,quantityCopy);
                        Log.d("locked",lockedCopy);
                        Log.d("id",idCopy);
                        databaseGarbage.child(idCopy).setValue(postGarbageCopy);
                        Intent i=new Intent(AcceptOrReject.this,Contact.class);
                        i.putExtra("code",code);
                        i.putExtra("number",contact);
                        startActivity(i);
                        finish();
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AcceptOrReject.this,"Rejected",Toast.LENGTH_LONG).show();
                Intent i=new Intent(AcceptOrReject.this,SelectArea.class);
                startActivity(i);
            }
        });
    }
}
