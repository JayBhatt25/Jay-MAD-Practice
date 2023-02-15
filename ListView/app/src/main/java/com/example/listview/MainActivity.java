package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] colors = {"Red", "Green", "Blue"};
    ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users.add(new User("Jay Bhatt", 24));
        users.add(new User("Leo Messi", 37));
        users.add(new User("Neymar Jr.", 34));
        users.add(new User("Virat Kohli", 30));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, MainFragment.newInstance(users))
                .commit();
    }
}