package com.example.smarthome;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email;
    private EditText pass;
    private EditText confPass;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth= FirebaseAuth.getInstance();
        email = findViewById(R.id.houseSignEmail);
        Button btn2 = findViewById(R.id.signInBtn);
        pass =findViewById(R.id.houseSignPwd);
        confPass=findViewById(R.id.confPass);
        EditText nameOfUser = findViewById(R.id.name);
        TextView loginRedirect = findViewById(R.id.loginRedirect);
        Map<String,Object> bedroomStatus=new HashMap<>();
        Map<String,Object> kitchenStatus=new HashMap<>();
        Map<String,Object> bathStatus=new HashMap<>();
        Map<String,Object> livingRoomStatus=new HashMap<>();
        bedroomStatus.put("light",false);
        bedroomStatus.put("doorStat",false);
        bedroomStatus.put("doorPass","0000");
        bedroomStatus.put("windowPass","1111");
        bedroomStatus.put("air",false);
        bedroomStatus.put("airDeg",0);
        bedroomStatus.put("windowStat",false);
        kitchenStatus.put("doorPass","0000");
        kitchenStatus.put("windowPass","1111");
        kitchenStatus.put("light",false);
        bathStatus.put("windowStat",false);
        bathStatus.put("windowPass","1111");
        bathStatus.put("doorStat",false);
        bathStatus.put("doorPass","0000");
        bathStatus.put("light",false);
        bathStatus.put("machine",false);
        livingRoomStatus.put("air",false);
        livingRoomStatus.put("tv",false);
        livingRoomStatus.put("light",false);
        livingRoomStatus.put("doorStat",false);
        livingRoomStatus.put("doorPass","0000");
        livingRoomStatus.put("windowStat",false);
        livingRoomStatus.put("windowPass","1111");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String houseEmail = email.getText().toString().trim();
                String pwd=pass.getText().toString().trim();
                String pwdConf=confPass.getText().toString().trim();
                String name=nameOfUser.getText().toString().trim();
                database=FirebaseDatabase.getInstance();

                if(houseEmail.isEmpty()){
                    email.setError("email cannot be empty");
                }
                if(!pwd.equals(pwdConf)){
                    Toast.makeText(SignIn.this, "password and confirm password dosen't match", Toast.LENGTH_SHORT).show();
                }
                if(pwd.isEmpty()){
                    pass.setError("password cannot be empty");

                }else {
                    auth.createUserWithEmailAndPassword(houseEmail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                reference=database.getReference("users");
                                HelperClass helperClass=new HelperClass(name,houseEmail,pwd,bedroomStatus,kitchenStatus,bathStatus,livingRoomStatus);
                                String uid = auth.getCurrentUser().getUid();
                                reference.child(uid).setValue(helperClass);
                                Toast.makeText(SignIn.this,"signup succesfull ",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(SignIn.this, LogIn.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(SignIn.this, "signup failed"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        loginRedirect.setOnClickListener(v -> {
            Intent intent1 = new Intent(SignIn.this, LogIn.class);
            startActivity(intent1);
            finish();
        });

    }
}
