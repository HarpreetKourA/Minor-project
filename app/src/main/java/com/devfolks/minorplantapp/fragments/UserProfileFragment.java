package com.devfolks.minorplantapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devfolks.minorplantapp.EditProfileActivity;
import com.devfolks.minorplantapp.R;
import com.devfolks.minorplantapp.SettingsActivity;
import com.devfolks.minorplantapp.databinding.FragmentUserProfileBinding;
import com.devfolks.minorplantapp.registration.AddAddressActivity;
import com.devfolks.minorplantapp.registration.AddCropActivity;
import com.devfolks.minorplantapp.registration.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentUserProfileBinding.inflate(getLayoutInflater());
        View v= binding.getRoot();


        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(binding.more);
            }
        });
        binding.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getContext(), AddAddressActivity.class);
                startActivity(i);
            }
        });
        binding.myCrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getContext(), AddCropActivity.class);
                startActivity(i);
            }
        });
        binding.myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        firestore= FirebaseFirestore.getInstance();
        String uid= user.getUid();
        documentReference=firestore.collection("users").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    binding.userName.setText(documentSnapshot.getString("name"));
                    binding.userEmail.setText(documentSnapshot.getString("mail"));
                    binding.userPhoneno.setText(documentSnapshot.getString("phoneNo"));
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v, Gravity.END);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.user_profile_menu, popup.getMenu());
        popup.show();
    }
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v, Gravity.END);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent i=new Intent(getActivity(), LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getActivity(), SettingsActivity.class));
                        return true;

                    case R.id.editProfile:
                        startActivity(new Intent(getActivity(), EditProfileActivity.class));
                        return true;
                    default:
                        return false;
                }

            }
        });
        popup.inflate(R.menu.user_profile_menu);
        popup.show();
    }

}