package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.MainListener, RegistrationFragment.RegistrationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContainerView, new MainFragment())
                .commit();

    }

    @Override
    public void goToRegistration() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainerView,new RegistrationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendProfile(Profile profile) {

    }
}