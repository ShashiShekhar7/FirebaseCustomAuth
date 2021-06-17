package com.example.firebasecustomauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    FirebaseAuth mAuth;
    DatabaseReference dbRootReference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText etPhone = findViewById(R.id.et_sign_up_phone);
        EditText etPass = findViewById(R.id.et_sign_up_pass);
        TextView tvLogin = findViewById(R.id.tv_login);
        Button btnSignUp= findViewById(R.id.btn_sign_up);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        btnSignUp.setOnClickListener(v -> {
            String strPhone = etPhone.getText().toString().trim();
            String strPass = etPass.getText().toString().trim();

            if (strPhone.length() < 10)
                etPhone.setError("Invalid Phone Number");
            else if (strPass.length() < 8)
                etPass.setError("Password less than 8 character");
            else {
                signUpUser(strPhone, strPass);
            }

        });
    }

    private void signUpUser(String phone, String pass) {
        String email = phone + "@gmail.com";
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: createUserWithEmail:success");

                            dbRootReference = database.getReference();

                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));

                        } else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}