package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean("isFirstTime", true);

        if (!isFirstTime) {
            startActivity(new Intent(this, LogIn.class));
            finish();
            return; // VERY important
        }

        setContentView(R.layout.activity_welcome);

        ImageButton btn = findViewById(R.id.wlcBtn);
        btn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignIn.class));
            prefs.edit().putBoolean("isFirstTime", false).apply();
            finish();
        });
    }
}
