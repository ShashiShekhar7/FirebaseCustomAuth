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

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etPhone = findViewById(R.id.et_login_phone);
        EditText etPass = findViewById(R.id.et_login_pass);
        TextView tvCreateAcc = findViewById(R.id.tv_create_acc);
        Button btnLogin = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        tvCreateAcc.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            String strPhone = etPhone.getText().toString().trim();
            String strPass = etPass.getText().toString().trim();

            if (strPhone.length() < 10)
                etPhone.setError("Invalid Phone Number");
            else if (strPass.length() < 8)
                etPass.setError("Password less than 8 character");
            else {
                login(strPhone, strPass);
            }
        });


    }

    private void login(String phone, String pass) {
        String email = phone + "@gmail.com";
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: createUserWithEmail:success");

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}