package com.example.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragments.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    private String selectedEdu;

    public void setSelectedEdu(String selectedEdu) {
        this.selectedEdu = selectedEdu;
    }


    FragmentHomeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home Fragment");

        if(selectedEdu == null){
            binding.textViewEduSelected.setText("Not set");
        } else {
            binding.textViewEduSelected.setText(selectedEdu);
        }
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter a valid name", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double age = Double.valueOf(binding.editTextAge.getText().toString());
                        Profile profile = new Profile(name, age);
                        hListner.sendProfile(profile);
                    } catch(NumberFormatException ex){
                        Toast.makeText(getActivity(), "Enter a valid age", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        binding.buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hListner.goToSettings();
            }
        });

        binding.buttonEduSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hListner.gotToSetEdu();
            }
        });
    }

    HomeListner hListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        hListner = (HomeListner) context;


    }

    interface HomeListner{
        void getName(String name);
        void goToSettings();
        void sendProfile(Profile profile);

        void gotToSetEdu();
    }
}