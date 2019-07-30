package com.example.scrapcollector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserLogin extends AppCompatActivity {
    private FirebaseAuth auth;
    private String username;
    ProgressDialog progressDialog;
    String password="";
    String message="";
    String login_status="";
    String j_string="";
    String J_STRING="";
    JSONObject jsonObject;
    JSONArray jsonArray;
    TextView usernamee,passwordd,btnSignup;
    Button btnLogin;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        FirebaseUser user = auth.getCurrentUser();


        usernamee=(TextView) findViewById(R.id.login_input_email);
        passwordd=(TextView) findViewById(R.id.login_input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.link_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, UserRegister.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = usernamee.getText().toString();
                final String password = passwordd.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(UserLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        Toast.makeText(UserLogin.this, "Password shold be more than 6 characters",Toast.LENGTH_SHORT);
                                    } else {
                                        Toast.makeText(UserLogin.this, "Something went wrong!!" , Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Query query = databaseUsers.orderByChild("email").equalTo(email);
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                User users = childSnapshot.getValue(User.class);
                                                username = users.getUsername();
                                                Log.d("loginUsernameinsidefor",username);
                                            }

                                            Intent intent = new Intent(UserLogin.this,PostGarbageDetails.class);
                                            Log.d("loginUsername",username);
                                            intent.putExtra("username",username);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        });
            }
        });



    }
}
