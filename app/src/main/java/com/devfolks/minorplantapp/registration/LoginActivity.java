package com.devfolks.minorplantapp.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.devfolks.minorplantapp.MainActivity;
import com.devfolks.minorplantapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        binding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail= binding.loginEmail.getText().toString().trim();
                String password= binding.loginPassword.getText().toString().trim();
                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else{
                    binding.progressbarOfLogin.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkMailVerification();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Account doesn't exist.",Toast.LENGTH_SHORT).show();
                                binding.progressbarOfLogin.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }


        });
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LoginActivity.this, OnboardingActivity.class);
                startActivity(i);
            }
        });
        binding.forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
    }
    private void checkMailVerification() {
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()==true){
            Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        else{
            binding.progressbarOfLogin.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Verify your email first.",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent i= new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

}