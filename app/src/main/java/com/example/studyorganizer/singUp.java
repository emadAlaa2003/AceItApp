package com.example.studyorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class singUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        EditText etFullName = findViewById(R.id.etFullName);
        EditText etEmail    = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);

        Button btnSignUp    = findViewById(R.id.btnSignUp);
        TextView tvLoginLink = findViewById(R.id.tvLoginLink);

        btnSignUp.setOnClickListener(v -> {

            String name  = etFullName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass  = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                etFullName.setError("Full name is required");
                etFullName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email is required");
                etEmail.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(pass)) {
                etPassword.setError("Password is required");
                etPassword.requestFocus();
                return;
            }

            Toast.makeText(singUp.this, "Account created!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(singUp.this, LoginActivity.class));
            finish();
        });

        tvLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(singUp.this, LoginActivity.class));
            finish();
        });
    }
}
