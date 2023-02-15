package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inclass04.databinding.FragmentRegistrationBinding;


public class RegistrationFragment extends Fragment {
    FragmentRegistrationBinding binding;
   String name;
   String email;
   int id = -1;
   String dept;

    public RegistrationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    RegistrationListener rListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        rListener = (RegistrationListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Registration");

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.editTextTextPersonName.getText().toString();
                email = binding.editTextTextEmailAddress.getText().toString();

                if(name.isEmpty() || email.isEmpty() || id == -1 || dept.isEmpty()){
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else {

                    try{
                        id = Integer.valueOf(binding.editTextNumber.getText().toString());
                        Profile profile = new Profile(name, email, id, dept);
                        rListener.sendProfile(profile);

                    } catch (NumberFormatException ex){
                        Toast.makeText(getActivity(), "Enter a valid number", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });
    }

    public interface RegistrationListener{

        void sendProfile(Profile profile);
    }
}