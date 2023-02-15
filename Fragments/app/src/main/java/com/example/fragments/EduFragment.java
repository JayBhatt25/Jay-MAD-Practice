package com.example.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.fragments.databinding.FragmentEduBinding;


public class EduFragment extends Fragment {

    FragmentEduBinding binding;

    public EduFragment() {
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
        binding = FragmentEduBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    EduListener eListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eListener = (EduListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eListener.cancelEduSelection();
            }
        });
        binding.buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = binding.radioGroupEdu.getCheckedRadioButtonId();
                if(checkedId == -1){
                    Toast.makeText(getActivity(), "Please select an education level", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton radioButton = binding.radioGroupEdu.findViewById(checkedId);
                    String selectedEdu = radioButton.getText().toString();
                    eListener.sendSelectedEdu(selectedEdu);
                }
            }
        });
    }

    public interface EduListener{
        void cancelEduSelection();
        void sendSelectedEdu(String education);
    }
}