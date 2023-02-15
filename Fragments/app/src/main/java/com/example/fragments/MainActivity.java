package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeListner , SettingsFragment.SettingListener, EduFragment.EduListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         getSupportFragmentManager()
                 .beginTransaction()
                 .add(R.id.containerView, new HomeFragment(),"home-fragment")
                 .commit();
    }

    @Override
    public void getName(String name) {
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToSettings() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotToSetEdu() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView,new EduFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void cancelEduSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedEdu(String education) {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home-fragment");
        if(homeFragment != null){
            homeFragment.setSelectedEdu(education);
            getSupportFragmentManager().popBackStack();
        }
    }
}