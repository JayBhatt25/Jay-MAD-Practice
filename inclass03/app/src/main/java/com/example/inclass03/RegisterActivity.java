package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class RegisterActivity extends AppCompatActivity {
    Button selectBtn;
    Button submitBtn;
    EditText name;
    EditText email;
    EditText id;

    TextView selectedDept;

    String nameEntry = "";
    String emailEntry="";
    int idEntry = -1;
    String deptEntry = "";
    User currentUser = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration");
        selectBtn = findViewById(R.id.buttonSelect);
        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        id = findViewById(R.id.editTextID);
        submitBtn = findViewById(R.id.buttonSubmit);
        selectedDept = findViewById(R.id.textViewSelectedDept);



        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deptIntent = new Intent(RegisterActivity.this, DepartmentActivity.class);
                if(name.getText().toString() != null) currentUser.setName(name.getText().toString());
                if(email.getText().toString() != null) currentUser.setEmail(email.getText().toString());
                if(!id.getText().toString().equals("")) currentUser.setId(Integer.valueOf(id.getText().toString()));
                deptIntent.putExtra("USER", currentUser);
                startActivity(deptIntent);

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                if(validateInputs() == false){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    currentUser.setName(name.getText().toString());
                    currentUser.setEmail(email.getText().toString());
                    try{
                        currentUser.setId(Integer.valueOf(id.getText().toString()));
                    } catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "ID should be valid positive number", Toast.LENGTH_SHORT).show();
                    }

                    Intent profileIntent = new Intent(RegisterActivity.this, ProfileActivity.class );
                    profileIntent.putExtra("USER", currentUser);
                    startActivity(profileIntent);

                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


        if(getIntent() != null && getIntent().getExtras() != null){
            currentUser = (User)getIntent().getSerializableExtra("USER");
        }
        if(currentUser.getName() != ""){
            name.setText(currentUser.getName());
        }
        if(currentUser.getEmail() != ""){
            email.setText(currentUser.getEmail());
        }
        if(currentUser.getId() != -1){
            id.setText(String.valueOf(currentUser.getId()));
        }
        if(currentUser.getDept() != ""){
            selectedDept.setText(currentUser.getDept());
        }
    }

    public boolean validateInputs(){
        if(name.getText().toString().equals("") || email.getText().toString().equals("") || id.getText().toString().equals("") || selectedDept.getText().toString().equals("")){
            return false;
        }
        return true;
    }
}