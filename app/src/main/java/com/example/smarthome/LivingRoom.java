package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LivingRoom extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_living_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button windowBtn=findViewById(R.id.button5);
        Button lightBtn=findViewById(R.id.button4);
        Button btn=findViewById(R.id.home_btn);
        Button airBtn=findViewById(R.id.button6);
        btn.setOnClickListener(v -> {
            startActivity(new Intent(LivingRoom.this,MainActivity.class));
            finish();
        });
        airBtn.setOnClickListener(v -> {
            startActivity((new Intent(LivingRoom.this, LivingAir.class)));
            finish();
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            startActivity(new Intent(LivingRoom.this, LivingRoomTv.class));
        });

        windowBtn.setOnClickListener(v -> {
            startActivity((new Intent(LivingRoom.this, LivingWindow.class)));
            finish();
        });
        lightBtn.setOnClickListener(v -> {
            startActivity((new Intent(LivingRoom.this, LivingLight.class)));
            finish();
        });

    }
}