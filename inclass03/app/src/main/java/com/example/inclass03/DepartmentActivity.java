package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class DepartmentActivity extends AppCompatActivity {
    Button deptselectBtn;
    Button cancelBtn;
    RadioGroup deptGrp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        setTitle("Department");

        deptselectBtn = findViewById(R.id.buttonSelectDept);
        deptGrp = findViewById(R.id.radioGroupDept);
        cancelBtn = findViewById(R.id.buttonCancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        deptselectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(DepartmentActivity.this, RegisterActivity.class);
                int dept = deptGrp.getCheckedRadioButtonId();
                String selected = "Computer Science";
                if(dept == R.id.radioButtonCS){
                    selected = "Computer Science";
                } else  if(dept == R.id.radioButtonSIS){
                    selected = "Software Info. Systems";
                } else if(dept == R.id.radioButtonBio){
                    selected = "Bio Informatics";
                } else {
                    selected = "Data Science";
                }
                if(getIntent() != null && getIntent().getSerializableExtra("USER") != null){
                    User user = (User)getIntent().getSerializableExtra("USER");
                    Log.d("dept activity user: ", String.valueOf(user));
                    user.setDept(selected);
                    registerIntent.putExtra("USER",user );
                    startActivity(registerIntent);
                }


            }
        });
    }
}