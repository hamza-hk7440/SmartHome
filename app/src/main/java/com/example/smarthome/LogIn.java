package com.example.smarthome;

import android.content.Intent;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {
    FirebaseAuth auth;
    private EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.loginEmail);
        pass=findViewById(R.id.loginPassword);
        Button btn1 = findViewById(R.id.loginButton);
        TextView redirectSignUp=findViewById(R.id.signUpRedirect);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String logInEmail=email.getText().toString().trim();
                String logInPasssword=pass.getText().toString().trim();
                if(!logInEmail.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(logInEmail).matches()){
                    if(!logInPasssword.isEmpty()){
                        auth.signInWithEmailAndPassword(logInEmail,logInPasssword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //on veut atrraper des information qui sont dans database
                                //1er etape avoir l'uid de user
                                FirebaseUser user = auth.getCurrentUser();

                                if(user == null){
                                    Toast.makeText(LogIn.this, "User not found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                String uid=user.getUid();
                                //avoir la ref de cette uid
                                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users").child(uid);
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            String ch=snapshot.child("name").getValue(String.class);
                                            Toast.makeText(LogIn.this, "Log in successful", Toast.LENGTH_SHORT).show();
                                            //envoyer l'inf au main
                                            Intent intent1 = new Intent(LogIn.this, MainActivity.class);
                                            intent1.putExtra("name",ch);
                                            startActivity(intent1);
                                            finish();
                                        }else {
                                            Toast.makeText(LogIn.this, "User data not found in database", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(LogIn.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogIn.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }else{
                        pass.setError("password cannot be empty");
                    }
                }else if(logInEmail.isEmpty()){
                    email.setError("email cannot be empty");
                }else{
                    email.setError("please enter a valid email");
                }
            }
        });

        redirectSignUp.setOnClickListener(v -> {
            Intent intent=new Intent(LogIn.this, SignIn.class);
            startActivity(intent);
            finish();
        });


    }
}