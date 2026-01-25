package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

public class BathMachine extends AppCompatActivity {
    private Boolean machineStatut;
    private DatabaseReference bathMachineStatRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bath_machine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView resl=findViewById(R.id.ress);
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        bathMachineStatRef=FirebaseDatabase.getInstance().getReference("users").child(uid).child("bathStatus").child("machine");
        bathMachineStatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean statut =snapshot.getValue(Boolean.class);
                if (statut == null) return;
                if(statut)
                    resl.setText("ON");

                else
                    resl.setText("OFF");

                machineStatut=statut;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        findViewById(R.id.power).setOnClickListener(v -> {
            bathMachineStatRef.setValue(!machineStatut);
        });
        findViewById(R.id.button7).setOnClickListener(v -> {
            startActivity(new Intent(BathMachine.this,Bath.class));
        });
    }
}