package com.example.smarthome;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView nameOfHouse=findViewById(R.id.houseName);
        String txt = getIntent().getStringExtra("name");
        if(txt == null) txt = "User"; // default name
        nameOfHouse.setText("Welcome Mr " + txt.trim());
        Button bedroomBtn=findViewById(R.id.bedroom_btn);
        Button kitchenBtn=findViewById(R.id.kitchen_btn);
        Button bathroomBtn=findViewById(R.id.bathroom_btn);
        Button livingRoomBtn=findViewById(R.id.living_btn);
        bedroomBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,Bedrooms.class));
            finish();
        });
        kitchenBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,Kitchen.class));
            finish();
        });
        bathroomBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,Bath.class));
            finish();
        });
        livingRoomBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,LivingRoom.class));
            finish();
        });


    }
}