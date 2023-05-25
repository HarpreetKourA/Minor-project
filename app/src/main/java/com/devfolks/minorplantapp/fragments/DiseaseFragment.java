package com.devfolks.minorplantapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.devfolks.minorplantapp.databinding.FragmentDiseaseBinding;


public class DiseaseFragment extends Fragment {

    private FragmentDiseaseBinding binding;

    public DiseaseFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentDiseaseBinding.inflate(getLayoutInflater());
        View v= binding.getRoot();

        binding.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);


            }
        });

        /*binding.predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.image = Bitmap.createScaledBitmap(binding.image, 150,150,true);
                try {
                    HealthyUnhealthy model = HealthyUnhealthy.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 150, 150, 3}, Element.DataType.FLOAT32);

                    TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                    tensorImage.load(image);
                    ByteBuffer byteBuffer = tensorImage.getBuffer();
                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    HealthyUnhealthy.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();

                    textView.setText(outputFeature0.getFloatArray()[0] + "\n"+outputFeature0.getFloatArray()[1]);
                } catch (IOException e) {
                    // TODO Handle the exception
                }


            }
        });*/
        return v;
    }
    /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100)
        {
            imageView.setImageURI(data.getData());

            Uri uri = data.getData();
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }
}*/
}