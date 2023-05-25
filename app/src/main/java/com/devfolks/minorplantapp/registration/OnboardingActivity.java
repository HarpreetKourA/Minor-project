package com.devfolks.minorplantapp.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.devfolks.minorplantapp.R;
import com.devfolks.minorplantapp.databinding.ActivityOnboardingBinding;
import com.devfolks.minorplantapp.registration.classes.OnboardingAdapter;
import com.devfolks.minorplantapp.registration.classes.OnboardingItem;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {
    private ActivityOnboardingBinding binding;
    private OnboardingAdapter onboardingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOnboardingBinding.inflate(getLayoutInflater());
        View v= binding.getRoot();
        setContentView(v);
        setupOnboardingItems();

        binding.onboardingViewPager.setAdapter(onboardingAdapter);
        setupOnboardingIndicators();
        setCurrentOnboardingIndicators(0);
        binding.onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });
        binding.buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.onboardingViewPager.getCurrentItem()+1<onboardingAdapter.getItemCount()){
                    binding.onboardingViewPager.setCurrentItem(binding.onboardingViewPager.getCurrentItem()+1);
                }
                else{
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    finish();
                }
            }
        });
    }
    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItems=new ArrayList<>();
        OnboardingItem onboarding1= new OnboardingItem();
        onboarding1.setTitle("Detect disease through camera.");
        onboarding1.setDescription("Detect diseases and get valued medication of particular disease which will not affect your crop");
        onboarding1.setImages(R.drawable.disease);

        OnboardingItem onboarding2= new OnboardingItem();
        onboarding2.setTitle("Get Real Time Weather Data");
        onboarding2.setDescription("Decision in farming depends on weather. Get realtime weather data and get 1 week weather forecasting on our app.");
        onboarding2.setImages(R.drawable.weather_icon);

        OnboardingItem onboarding3= new OnboardingItem();
        onboarding3.setTitle("Schedule your task and monitor your crop health");
        onboarding3.setDescription("You can create event in our app for a particular task and schedule them anytime with just few clicks");
        onboarding3.setImages(R.drawable.task);

        onboardingItems.add(onboarding1);
        onboardingItems.add(onboarding2);
        onboardingItems.add(onboarding3);

        onboardingAdapter= new OnboardingAdapter(onboardingItems);
    }
    private void setupOnboardingIndicators(){
        ImageView[] indicators= new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);
        for(int i=0; i< indicators.length; i++){
            indicators[i]=new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            binding.layoutOnboardingIndicators.addView(indicators[i]);

        }
    }
    private void setCurrentOnboardingIndicators(int index){
        int childCount= binding.layoutOnboardingIndicators.getChildCount();
        for(int i=0; i< childCount; i++){
            ImageView imgView= (ImageView) binding.layoutOnboardingIndicators.getChildAt(i);
            if(i==index){
                imgView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active));

            }else{
                imgView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            }
        }
        if(index==onboardingAdapter.getItemCount()-1){
            binding.buttonOnboardingAction.setText("Start");
        }
        else{
            binding.buttonOnboardingAction.setText("Next");
        }
    }

}