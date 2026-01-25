package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Bath extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bath);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button lightBtn=findViewById(R.id.button4);
        Button btn=findViewById(R.id.home_btn);
        Button doorBtn=findViewById(R.id.button3);
        Button windowBtn=findViewById(R.id.button5);
        btn.setOnClickListener(v -> {
            startActivity(new Intent(Bath.this,MainActivity.class));
            finish();
        });
        findViewById(R.id.button6).setOnClickListener(v -> {
            startActivity(new Intent(Bath.this,BathMachine.class));
        });
        lightBtn.setOnClickListener(v -> {
            startActivity((new Intent(Bath.this, BathLight.class)));
            finish();
        });
        doorBtn.setOnClickListener(v -> {
            startActivity((new Intent(Bath.this, BathDoor.class)));
            finish();
        });
        windowBtn.setOnClickListener(v -> {
            startActivity((new Intent(Bath.this, BathWindow.class)));
            finish();
        });
    }
}