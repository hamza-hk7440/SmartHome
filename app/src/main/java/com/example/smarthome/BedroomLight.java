package com.example.smarthome;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

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
import com.google.android.material.switchmaterial.SwitchMaterial;

public class BedroomLight extends AppCompatActivity {
    private SwitchMaterial lightSwitch;
    private ImageView on, off;
    private DatabaseReference lightRef;
    Button goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bedroom_light);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        lightRef= FirebaseDatabase.getInstance().getReference("users").child(uid).child("bedroomStatus").child("light");
        lightSwitch=findViewById(R.id.switch1);
        goBack=findViewById(R.id.button7);
        on=findViewById(R.id.lightOn);
        off=findViewById(R.id.lightOff);

        lightRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            Boolean isOn= snapshot.getValue(Boolean.class);
            if(isOn!=null){
                lightSwitch.setChecked(isOn);
                updateLightUi(isOn);


            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lightRef.setValue(isChecked);
        });
        goBack.setOnClickListener(v -> {
            startActivity(new Intent(BedroomLight.this,Bedrooms.class));
        });
    }
    private void updateLightUi(boolean isOn){
        if (isOn){
            off.setVisibility(View.GONE);
            on.setVisibility(View.VISIBLE);
            lightSwitch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#FFD8C2AA")));
            lightSwitch.setTrackTintList(ColorStateList.valueOf(Color.parseColor("#FFC3934F")));

        }
        else {
            on.setVisibility(View.GONE);
            off.setVisibility(View.VISIBLE);
            lightSwitch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            lightSwitch.setTrackTintList(ColorStateList.valueOf(Color.parseColor("#FF3F3D56")));

        }
    }

}