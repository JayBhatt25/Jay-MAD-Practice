package com.example.listview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.example.listview.databinding.FragmentMainBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements UserRecyclerViewAdapter.AdapterListener {
    FragmentMainBinding binding;
    UserRecyclerViewAdapter userRecyclerViewAdapter;

    RecyclerView recyclerView;


    LinearLayoutManager linearLayoutManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_USERS = "users";


    // TODO: Rename and change types of parameters
    private ArrayList<User> mUsers;


    public MainFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(ArrayList<User> users) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USERS, users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsers = (ArrayList<User>) getArguments().getSerializable(ARG_PARAM_USERS);
        }
    }

    Mainlistener mainListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainListener = (Mainlistener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        userRecyclerViewAdapter = new UserRecyclerViewAdapter(mUsers,this);
        recyclerView.setAdapter(userRecyclerViewAdapter);

    }


    @Override
    public void goToProfile(User user) {
        mainListener.showProfile(user);
    }

    public interface Mainlistener{
        void showProfile(User user);
    }
}