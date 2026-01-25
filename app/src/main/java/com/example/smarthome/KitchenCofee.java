package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class KitchenCofee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kitchen_cofee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.express).setOnClickListener(v -> {Intent intent=new Intent(KitchenCofee.this, CofeeTreatment.class);
            intent.putExtra("pic",R.drawable.but);
            startActivity(intent);
        });
        findViewById(R.id.traditional).setOnClickListener(v -> {Intent intent=new Intent(KitchenCofee.this, CofeeTreatment.class);
            intent.putExtra("pic",R.drawable.trad);
            startActivity(intent);
        });
        findViewById(R.id.americano).setOnClickListener(v -> {Intent intent=new Intent(KitchenCofee.this, CofeeTreatment.class);
            intent.putExtra("pic",R.drawable.americano);
            startActivity(intent);
        });
        findViewById(R.id.doubleShot).setOnClickListener(v -> {Intent intent=new Intent(KitchenCofee.this, CofeeTreatment.class);
            intent.putExtra("pic",R.drawable.doubleshot);
            startActivity(intent);
        });
        findViewById(R.id.iced).setOnClickListener(v -> {Intent intent=new Intent(KitchenCofee.this, CofeeTreatment.class);
            intent.putExtra("pic",R.drawable.ice);
            startActivity(intent);
        });
        findViewById(R.id.moca).setOnClickListener(v -> {Intent intent=new Intent(KitchenCofee.this, CofeeTreatment.class);
            intent.putExtra("pic",R.drawable.moca);
            startActivity(intent);
        });
        findViewById(R.id.button7).setOnClickListener(v -> {
            startActivity(new Intent(KitchenCofee.this, Kitchen.class));
            finish();
        });
    }
}