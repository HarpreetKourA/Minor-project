package com.devfolks.minorplantapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.devfolks.minorplantapp.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        firestore= FirebaseFirestore.getInstance();
        uid= user.getUid();
        /*documentReference= firestore.collection("users").document(uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String name= task.getResult().getString("name");
                    String mail= task.getResult().getString("mail");
                    String phone= task.getResult().getString("phoneNo");
                    binding.editName.setText(name);
                    binding.editEmail.setText(mail);
                    binding.editPhoneNumber.setText(phone);
                }
            }
        });*/
        binding.saveChngsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> details= new HashMap<>();
                details.put("name",binding.editName.getText());
                details.put("mail",binding.editEmail.getText());
                details.put("phoneNo",binding.editPhoneNumber.getText());
                Toast.makeText(getApplicationContext(),"name"+binding.editName+binding.editEmail+binding.editPhoneNumber,Toast.LENGTH_SHORT).show();
                documentReference=firestore.collection("users").document(uid);
                documentReference.update(details).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("tag","updated");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Can't update",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            }
        });
    }
}