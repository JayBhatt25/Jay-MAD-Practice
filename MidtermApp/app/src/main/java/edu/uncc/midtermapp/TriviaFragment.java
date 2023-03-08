package edu.uncc.midtermapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import edu.uncc.midtermapp.databinding.FragmentTriviaBinding;
import edu.uncc.midtermapp.models.Answer;
import edu.uncc.midtermapp.models.Question;
import edu.uncc.midtermapp.models.Stat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TriviaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TriviaFragment extends Fragment {
    private ArrayList<Question> mTriviaQuestions = new ArrayList<Question>();
    ArrayList<Answer> answers = new ArrayList<>();

    boolean isFirstAttempt;
    ArrayAdapter<Answer> adapter;
    FragmentTriviaBinding binding;
    private static final String ARG_PARAM_QUESTIONS = "questions";

    private int currQuestionIdx = 0;

    private int correctAnswers= 0;

    public TriviaFragment() {
        // Required empty public constructor
    }


    public static TriviaFragment newInstance(ArrayList<Question> qs) {
        TriviaFragment fragment = new TriviaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_QUESTIONS, qs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTriviaQuestions = (ArrayList<Question>) getArguments().getSerializable(ARG_PARAM_QUESTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTriviaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    TriviaFragmentListener tfListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        tfListener = (TriviaFragmentListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, answers);
        binding.listViewAnswers.setAdapter(adapter);
        setupTrivia(currQuestionIdx);
        binding.listViewAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Answer answer = answers.get(i);
                Question qs = mTriviaQuestions.get(currQuestionIdx);
                correctAnswer(answer, qs);

            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tfListener.startNew();
            }
        });
    }

    void setupTrivia(int idx)  {
        isFirstAttempt = true;
        Question currQs = mTriviaQuestions.get(idx);
        binding.textViewTriviaQuestion.setText(currQs.question_text);
        binding.textViewTriviaTopStatus.setText("Question "+(currQuestionIdx + 1)+" of "+mTriviaQuestions.size());
        Picasso.get().load(currQs.question_url).into(binding.imageViewQuestion);
        answers.clear();
        answers.addAll(currQs.answers);
        adapter.notifyDataSetChanged();
    }

    void correctAnswer(Answer answer, Question qs){
        OkHttpClient client = new OkHttpClient();
        RequestBody reqBody = new FormBody.Builder()
                .add("question_id", qs.question_id)
                .add("answer_id", answer.answer_id)
                .build();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/trivia/checkAnswer")
                .post(reqBody)
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
                        JSONObject res = new JSONObject(body);
                        boolean isCorrect = res.getBoolean("isCorrectAnswer");

                      getActivity().runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              if(isCorrect && currQuestionIdx == mTriviaQuestions.size() - 1){
                                  if(isFirstAttempt) correctAnswers += 1;
                                  Stat stat = new Stat(correctAnswers, mTriviaQuestions.size());
                                  tfListener.sendStats(stat);
                              } else if(isCorrect){
                                  if(isFirstAttempt) correctAnswers += 1;
                                  currQuestionIdx += 1;
                                  setupTrivia(currQuestionIdx);
                              } else {
                                  isFirstAttempt = false;
                                  Toast.makeText(getActivity(), "Wrong answer!", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public interface TriviaFragmentListener{
        void sendStats(Stat stat);
        void startNew();
    }
}