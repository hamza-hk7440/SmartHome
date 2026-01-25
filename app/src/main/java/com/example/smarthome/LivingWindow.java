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

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LivingWindow extends AppCompatActivity {
    private TextView[] digits;
    private SwitchMaterial windowSwitch;

    // ----- LOGIC -----
    private StringBuilder code = new StringBuilder();
    private static final int MAX = 4;
    private String correctCode = "";

    // ----- FIREBASE -----
    private DatabaseReference windowPassRef, windowStateRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_living_window);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        digits = new TextView[]{
                findViewById(R.id.d1),
                findViewById(R.id.d2),
                findViewById(R.id.d3),
                findViewById(R.id.d4)
        };
        windowSwitch = findViewById(R.id.switch1);

        // -------- Firebase init --------
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        windowPassRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("livingRoomStatus")
                .child("windowPass");

        windowStateRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("livingRoomStatus")
                .child("windowStat");

        // -------- Listen for password --------
        windowPassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                correctCode = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // -------- Listen for door state --------
        windowStateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean isOpen = snapshot.getValue(Boolean.class);
                if (isOpen != null) {
                    windowSwitch.setOnCheckedChangeListener(null);
                    windowSwitch.setChecked(isOpen);
                    windowSwitch.setOnCheckedChangeListener((button, checked) -> {
                        windowStateRef.setValue(checked);
                        Toast.makeText(LivingWindow.this,
                                checked ? "Window unlocked ✅" : "Window locked 🔒",
                                Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // -------- Keypad buttons --------
        findViewById(R.id.btn0).setOnClickListener(v -> addNumber("0"));
        findViewById(R.id.btn1).setOnClickListener(v -> addNumber("1"));
        findViewById(R.id.btn2).setOnClickListener(v -> addNumber("2"));
        findViewById(R.id.btn3).setOnClickListener(v -> addNumber("3"));
        findViewById(R.id.btn4).setOnClickListener(v -> addNumber("4"));
        findViewById(R.id.btn5).setOnClickListener(v -> addNumber("5"));
        findViewById(R.id.btn6).setOnClickListener(v -> addNumber("6"));
        findViewById(R.id.btn7).setOnClickListener(v -> addNumber("7"));
        findViewById(R.id.btn8).setOnClickListener(v -> addNumber("8"));
        findViewById(R.id.btn9).setOnClickListener(v -> addNumber("9"));

        findViewById(R.id.btnDel).setOnClickListener(v -> deleteNumber());
        findViewById(R.id.btnClear).setOnClickListener(v -> clearAll());
    }

    // ----------------- LOGIC -----------------

    private void addNumber(String num) {
        if (code.length() < MAX) {
            code.append(num);
            digits[code.length() - 1].setText(num);
        }

        if (code.length() == MAX) {
            checkCode(code.toString());
        }
    }

    private void deleteNumber() {
        if (code.length() > 0) {
            digits[code.length() - 1].setText("");
            code.deleteCharAt(code.length() - 1);
        }
    }

    private void clearAll() {
        code.setLength(0);
        for (TextView t : digits) {
            t.setText("");
        }
    }

    private void checkCode(String input) {

        if (correctCode == null || correctCode.isEmpty()) {
            Toast.makeText(this, "Loading password...", Toast.LENGTH_SHORT).show();
            clearAll();
            return;
        }

        if (input.equals(correctCode)) {
            // Toggle door state
            windowStateRef.get().addOnSuccessListener(snapshot -> {
                Boolean isOpen = snapshot.getValue(Boolean.class);
                if (isOpen == null) isOpen = false;

                boolean newState = !isOpen;
                windowStateRef.setValue(newState);

                Toast.makeText(LivingWindow.this,
                        newState ? "Window unlocked ✅" : "Window locked 🔒",
                        Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Wrong code ❌", Toast.LENGTH_SHORT).show();
        }

        clearAll();
        findViewById(R.id.button7).setOnClickListener(v -> {
            startActivity(new Intent(LivingWindow.this, LivingRoom.class));
            finish();
        });
    }
}