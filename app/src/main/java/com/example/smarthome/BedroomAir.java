package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

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

public class BedroomAir extends AppCompatActivity {

    private DatabaseReference airCondRef, airDegRef;

    private TextView deg;
    private Button power, goBack;
    private SeekBar bar;

    private Boolean airStatut = false;
    private int currentDeg = 16; // default value
    private int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bedroom_air);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Views
        bar = findViewById(R.id.seekBar3);
        deg = findViewById(R.id.deg);
        power = findViewById(R.id.button8);
        goBack = findViewById(R.id.button6);

        // Firebase
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        airDegRef = FirebaseDatabase.getInstance().getReference("users")
                .child(uid).child("bedroomStatus").child("airDeg");

        airCondRef = FirebaseDatabase.getInstance().getReference("users")
                .child(uid).child("bedroomStatus").child("air");

        // ---------------- POWER LISTENER ----------------
        airCondRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean statut = snapshot.getValue(Boolean.class);
                if (statut == null) return;

                airStatut = statut;
                bar.setEnabled(statut);

                if (!statut) {
                    deg.setText("OFF");
                } else {
                    deg.setText(currentDeg + "°");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // ---------------- DEGREE LISTENER ----------------
        airDegRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer degData = snapshot.getValue(Integer.class);
                if (degData == null) return;

                currentDeg = degData;
                bar.setProgress(degData);

                if (airStatut != null && airStatut) {
                    deg.setText(degData + "°");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // ---------------- SEEK BAR ----------------
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (airStatut != null && airStatut && fromUser) {
                    p = progress;
                    deg.setText(progress + "°");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (airStatut != null && airStatut) {
                    airDegRef.setValue(p);
                }
            }
        });

        // ---------------- POWER BUTTON ----------------
        power.setOnClickListener(v -> {
            if (airStatut != null) {
                airCondRef.setValue(!airStatut);
            }
        });

        // ---------------- BACK BUTTON ----------------
        goBack.setOnClickListener(v -> {
            startActivity(new Intent(BedroomAir.this, Bedrooms.class));
            finish();
        });
    }
}
