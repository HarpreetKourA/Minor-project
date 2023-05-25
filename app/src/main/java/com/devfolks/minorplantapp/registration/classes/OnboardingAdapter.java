package com.devfolks.minorplantapp.registration.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.devfolks.minorplantapp.R;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>{
    private List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_onboarding,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder{
        private TextView title, description;
        private ImageView image;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textTitle);
            description=itemView.findViewById(R.id.textDescription);
            image= itemView.findViewById(R.id.imgOnboarding);
        }
        void setOnboardingData(OnboardingItem onboardingItem){
            title.setText(onboardingItem.getTitle());
            description.setText(onboardingItem.getDescription());
            image.setImageResource(onboardingItem.getImages());
        }

    }
}
