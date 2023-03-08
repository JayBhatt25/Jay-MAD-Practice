package edu.uncc.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.midtermapp.models.Question;
import edu.uncc.midtermapp.models.Stat;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeListener, TriviaFragment.TriviaFragmentListener, StatsFragment.StatsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new WelcomeFragment())
                .commit();
    }

    @Override
    public void sendQuestions(ArrayList<Question> questions) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, TriviaFragment.newInstance(questions))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendStats(Stat stat) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, StatsFragment.newInstance(stat))
                .commit();
    }

    @Override
    public void startNew() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new WelcomeFragment())
                .commit();
    }

    @Override
    public void startNewGame() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new WelcomeFragment())
                .commit();
    }
}