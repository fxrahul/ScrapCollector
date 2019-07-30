package com.example.scrapcollector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

public class CollectorLogin extends AppCompatActivity {
    private FirebaseAuth auth;
    String username="";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_login);
        auth = FirebaseAuth.getInstance();
        usernamee=(TextView) findViewById(R.id.login_input_email);
        passwordd=(TextView) findViewById(R.id.login_input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.link_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CollectorLogin.this, CollectorRegister.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernamee.getText().toString();
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
                        .addOnCompleteListener(CollectorLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        Toast.makeText(CollectorLogin.this, "Password shold be more than 6 characters",Toast.LENGTH_SHORT);
                                    } else {
                                        Toast.makeText(CollectorLogin.this, "Something went wrong!!" , Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(CollectorLogin.this,SelectArea.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });



    }
}
