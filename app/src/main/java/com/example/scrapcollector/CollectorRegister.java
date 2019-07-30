package com.example.scrapcollector;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CollectorRegister extends AppCompatActivity {
    private EditText et_fullname,et_username,et_password,et_email,et_contact;

    private RadioButton rb_male,rb_female;
    private Button button3;
    private String reg_fullname,reg_user,reg_password,reg_email,reg_contact,reg_gender;
    private FirebaseAuth auth;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_register);
        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("collector");


        et_fullname= (EditText) findViewById(R.id.et_fullname);
        et_username= (EditText) findViewById(R.id.et_username);
        et_password= (EditText) findViewById(R.id.et_password);
        et_email= (EditText) findViewById(R.id.et_email);
        et_contact= (EditText) findViewById(R.id.et_contact);
        rb_male= (RadioButton) findViewById(R.id.rb_male);
        rb_female= (RadioButton) findViewById(R.id.rb_female);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((et_fullname.getText().toString().trim().equals("")) || (et_username.getText().toString().trim().equals("")) || (et_password.getText().toString().trim().equals("")) || (et_email.getText().toString().trim().equals("")) || (et_contact.getText().toString().trim().equals(""))) {

                    Toast.makeText(CollectorRegister.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                } else {
                    reg_fullname = et_fullname.getText().toString();
                    reg_user = et_username.getText().toString();
                    reg_password = et_password.getText().toString();
                    reg_email = et_email.getText().toString();
                    reg_contact = et_contact.getText().toString();
                    reg_gender = null;

                    if (rb_male.isChecked()) {
                        reg_gender = "male";
                    } else {
                        reg_gender = "female";
                    }

                    auth.createUserWithEmailAndPassword(reg_email, reg_password)
                            .addOnCompleteListener(CollectorRegister.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(CollectorRegister.this, "User Successfully Created", Toast.LENGTH_LONG).show();
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(CollectorRegister.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        String id1 = auth.getCurrentUser().getUid();
//                                        Log.d("beforeusercall",reg_contact);
//                                    Toast.makeText(UserRegisterActivity.this, id1, Toast.LENGTH_LONG).show();
                                        addUser(id1,reg_fullname,reg_user,reg_password,reg_email,reg_contact,reg_gender);
//                                    startActivity(new Intent(UserRegisterActivity.this, UserLoginActivity.class));
//                                    finish();
                                    }
                                }
                            });


                }
            }
        });


    }

    public void addUser(String id,String name,String username,String password,String email,String contact,String gender){
//        Log.d("insideusercall",gender);
        User user = new User(id,name,username,password,email,contact,gender);
        databaseUsers.child(id).setValue(user);
        //displaying a success toast
        Toast.makeText(this, "Collector added with Key:" + " " + id, Toast.LENGTH_LONG).show();
        startActivity(new Intent(CollectorRegister.this, CollectorLogin.class));
        finish();
    }

}
