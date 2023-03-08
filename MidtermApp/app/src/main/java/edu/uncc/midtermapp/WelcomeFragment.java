package edu.uncc.midtermapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.midtermapp.databinding.FragmentWelcomeBinding;
import edu.uncc.midtermapp.models.Question;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WelcomeFragment extends Fragment {
    private ArrayList<Question> mTriviaQuestions = new ArrayList<Question>();

    FragmentWelcomeBinding binding;

    public WelcomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    WelcomeListener wListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        wListener = (WelcomeListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestions();
            }
        });

    }

    private void getQuestions(){
        mTriviaQuestions.clear();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/trivia")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        JSONObject resObj = new JSONObject(body);
                        JSONArray questionsArray = resObj.getJSONArray("questions");
                        for(int i = 0; i < questionsArray.length(); ++i){
                            Question question = new Question(questionsArray.getJSONObject(i));
                            mTriviaQuestions.add(question);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                wListener.sendQuestions(mTriviaQuestions);
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public interface WelcomeListener{
        void sendQuestions(ArrayList<Question> questions);
    }
}