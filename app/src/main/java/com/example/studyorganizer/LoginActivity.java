package com.example.studyorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText etEmail = findViewById(R.id.etEmailLogin);
        EditText etPassword = findViewById(R.id.etPasswordLogin);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvSignupLink = findViewById(R.id.tvSignupLink);

        tvSignupLink.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, singUp.class))
        );

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.equals("") || pass.equals("")) {
                if (email.equals("")) etEmail.setError("Required");
                if (pass.equals("")) etPassword.setError("Required");
            } else {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });
    }
}
