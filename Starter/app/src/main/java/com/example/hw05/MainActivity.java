package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CitiesFragment())
                .commit();
    }

    @Override
    public void goToWeather(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, WeatherFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }
}