package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView profileName;
    TextView email;
    TextView id;
    TextView dept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        profileName = findViewById(R.id.textViewDisplayName);
        email = findViewById(R.id.textViewDisplayEmail);
        id = findViewById(R.id.textViewDisplayID);
        dept = findViewById(R.id.textViewDisplayDept);
        if(getIntent() != null || getIntent().getSerializableExtra("USER") != null){
            User user = (User)  getIntent().getSerializableExtra("USER");
            profileName.setText(user.getName());
            email.setText(user.getEmail());
            id.setText(String.valueOf(user.getId()));
            dept.setText(user.getDept());
        }
    }
}