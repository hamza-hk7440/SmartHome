package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Kitchen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kitchen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btn=findViewById(R.id.home_btn);
        Button lightBtn=findViewById(R.id.button4);
        Button airBtn=findViewById(R.id.button6);
        Button doorBtn=findViewById(R.id.button3);
        Button windowBtn=findViewById(R.id.button5);
        btn.setOnClickListener(v -> {
            startActivity(new Intent(Kitchen.this,MainActivity.class));
            finish();
        });
        lightBtn.setOnClickListener(v -> {
            startActivity((new Intent(Kitchen.this, KitchenLight.class)));
            finish();
        });
        airBtn.setOnClickListener(v -> {
            startActivity((new Intent(Kitchen.this, KitchenCofee.class)));
            finish();
        });
        doorBtn.setOnClickListener(v -> {
            startActivity((new Intent(Kitchen.this, BedroomDoor.class)));
            finish();
        });
        windowBtn.setOnClickListener(v -> {
            startActivity((new Intent(Kitchen.this, BedroomWindow.class)));
            finish();
        });

    }
}