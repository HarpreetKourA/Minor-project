package com.devfolks.minorplantapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.devfolks.minorplantapp.databinding.FragmentHomeBinding;
import com.devfolks.minorplantapp.registration.AddCropActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private String Crop="null";
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            binding= FragmentHomeBinding.inflate(getLayoutInflater());
            View v= binding.getRoot();
        /*if(Crop==null){
            binding.dashboard.setVisibility(View.GONE);
            binding.addCropBtn.setVisibility(View.VISIBLE);
        }else{
            binding.dashboard.setVisibility(View.VISIBLE);
            binding.addCropBtn.setVisibility(View.GONE);
        }*/
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        firestore= FirebaseFirestore.getInstance();
        String uid= user.getUid();
        documentReference=firestore.collection("users").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    binding.userName.setText("Welcome "+documentSnapshot.getString("name"));
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        binding.addCropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), AddCropActivity.class);
                startActivity(intent);
            }
        });
        binding.detectDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }
}