package com.devfolks.minorplantapp.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.devfolks.minorplantapp.R;
import com.devfolks.minorplantapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding= ActivityRegisterBinding.inflate(getLayoutInflater());
        View v= binding.getRoot();
        setContentView(v);
        fauth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        binding.proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail= binding.registerEmail.getText().toString().trim();
                String password= binding.createPassword.getText().toString().trim();
                String username= binding.registerName.getText().toString();
                String phone= "+91"+binding.registerPhoneNumber.getText().toString();
                if(mail.isEmpty()|| password.isEmpty()|| username.isEmpty() || phone.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter all the details first",Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<7){
                    Toast.makeText(getApplicationContext(),"Password length should be greater than 7",Toast.LENGTH_SHORT).show();
                }
                else{   Toast.makeText(getApplicationContext(),mail + password,Toast.LENGTH_SHORT).show();

                    fauth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Registration Successful!",Toast.LENGTH_SHORT).show();
                                userID=fauth.getCurrentUser().getUid();
                                DocumentReference documentReference= firestore.collection("users").document(userID);
                                Map<String,Object> user= new HashMap<>();
                                user.put("name",username);
                                user.put("phoneNo",phone);
                                user.put("mail",mail);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("preet","created"+userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("preet","failure"+e.toString());
                                    }
                                });
                                sendEmailVerification();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Failed to register.",Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
            }
        });
        binding.loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = fauth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification mail has been sent! Verify and login again.", Toast.LENGTH_SHORT).show();
                    fauth.signOut();
                    finish();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                }

            });
        } else {
            Toast.makeText(getApplicationContext(), "Failed to send verification mail.", Toast.LENGTH_SHORT).show();
        }
    }

}