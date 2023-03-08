package edu.uncc.midtermapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.midtermapp.databinding.FragmentStatsBinding;
import edu.uncc.midtermapp.models.Stat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {


    private static final String ARG_PARAM_STAT = "stat";
    FragmentStatsBinding binding;

    // TODO: Rename and change types of parameters
    private Stat mStat;


    public StatsFragment() {
        // Required empty public constructor
    }


    public static StatsFragment newInstance(Stat stat) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_STAT, stat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStat = (Stat) getArguments().getSerializable(ARG_PARAM_STAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    StatsListener stListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        stListener = (StatsListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewTriviaStatus
                .setText(mStat.getCorrectAnswers()+" out of "+ mStat.getTotalQuestions()+" questions were answered correctly from the first attempt");

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stListener.startNewGame();
            }
        });
    }

    public interface StatsListener{
        void startNewGame();
    }
}