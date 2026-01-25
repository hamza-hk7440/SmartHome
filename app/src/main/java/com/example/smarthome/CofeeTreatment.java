package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CofeeTreatment extends AppCompatActivity {
    ProgressBar progressBar;
    Button controlBtn;

    boolean isRunning = false;
    int progress = 0;
    Thread progressThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cofee_treatment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView img=findViewById(R.id.previewCofee);
        int imgRes = getIntent().getIntExtra("pic", 0);
        img.setImageResource(imgRes);
        progressBar = findViewById(R.id.progressBar);
        controlBtn = findViewById(R.id.controlBtn);

        controlBtn.setOnClickListener(v -> {
            if (!isRunning) {
                startProgress();
                controlBtn.setText("Pause");
            } else {
                pauseProgress();
                controlBtn.setText("Start");
            }
        });
        findViewById(R.id.button7).setOnClickListener(v -> {
            startActivity(new Intent(CofeeTreatment.this, KitchenCofee.class));
            finish();
        });

    }
    private void startProgress() {
        isRunning = true;

        progressThread = new Thread(() -> {
            while (isRunning && progress <= 100) {

                runOnUiThread(() -> progressBar.setProgress(progress));

                if (progress == 100) {
                    runOnUiThread(() -> {
                        controlBtn.setText("Start");
                        Toast.makeText(this, "Completed ✅", Toast.LENGTH_SHORT).show();
                    });

                    isRunning = false;
                    break;
                }

                progress++;

                try {
                    Thread.sleep(200); // speed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        progressThread.start();
    }

    private void pauseProgress() {
        isRunning = false;
    }


}