package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

import org.jspecify.annotations.NonNull;

public class LivingRoomTv extends AppCompatActivity {
    private Boolean tvStatus;
    private DatabaseReference livingRoomStatRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_living_room_tv);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        livingRoomStatRef= FirebaseDatabase.getInstance().getReference("users").child(uid).child("livingRoomStatus").child("tv");
        livingRoomStatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean statut=snapshot.getValue(Boolean.class);
                if(statut==null) return;
                tvStatus=statut;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        findViewById(R.id.button7).setOnClickListener(v -> {
            startActivity(new Intent(LivingRoomTv.this,LivingRoom.class));
        });
        findViewById(R.id.button9).setOnClickListener(v -> {
            if(tvStatus != null && tvStatus){
                Toast.makeText(this, "The TV is OFF now", Toast.LENGTH_SHORT).show();
                livingRoomStatRef.setValue(false);
            }else {
                Toast.makeText(this, "The TV is ON now", Toast.LENGTH_SHORT).show();
                livingRoomStatRef.setValue(true);
            }
        });
    findViewById(R.id.button11).setOnClickListener(v -> {
        if(tvStatus)
            Toast.makeText(this, "Previous channel", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "The TV is OFF", Toast.LENGTH_SHORT).show();
    });
    findViewById(R.id.button13).setOnClickListener(v -> {
        if(tvStatus)
            Toast.makeText(this, "The TV is paused", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "The TV is OFF", Toast.LENGTH_SHORT).show();
    });
    findViewById(R.id.button10).setOnClickListener(v -> {
        if(tvStatus)
            Toast.makeText(this, "Returning to home", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "The TV is OFF", Toast.LENGTH_SHORT).show();
    });
    findViewById(R.id.button12).setOnClickListener(v -> {
        if(tvStatus)
            Toast.makeText(this, "The TV is muted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "The TV is OFF", Toast.LENGTH_SHORT).show();
    });
    }
}