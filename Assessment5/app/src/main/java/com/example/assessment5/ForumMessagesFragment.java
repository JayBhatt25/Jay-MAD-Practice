package com.example.assessment5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assessment5.databinding.ForumRowItemBinding;
import com.example.assessment5.databinding.FragmentForumMessagesBinding;
import com.example.assessment5.databinding.FragmentForumsBinding;
import com.example.assessment5.databinding.MessageRowItemBinding;
import com.example.assessment5.models.Auth;
import com.example.assessment5.models.Forum;
import com.example.assessment5.models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForumMessagesFragment extends Fragment {
    private static final String ARG_PARAM_FORUM = "ARG_PARAM_FORUM";
    private static final String ARG_PARAM_AUTH = "ARG_PARAM_AUTH";
    private Forum mForum;
    private Auth mAuth;
    ArrayList<Message> messages = new ArrayList<>();

    public ForumMessagesFragment() {
        // Required empty public constructor
    }

    public static ForumMessagesFragment newInstance(Forum forum, Auth auth) {
        ForumMessagesFragment fragment = new ForumMessagesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_FORUM, forum);
        args.putSerializable(ARG_PARAM_AUTH, auth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForum = (Forum) getArguments().getSerializable(ARG_PARAM_FORUM);
            mAuth = (Auth) getArguments().getSerializable(ARG_PARAM_AUTH);
        }
    }

    FragmentForumMessagesBinding binding;
    MessagesAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentForumMessagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forum Messages");
        adapter = new MessagesAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        getMessages();

        binding.textViewForumCreatorName.setText(mForum.getCreatedByFname()+" "+mForum.getCreatedByLname());
        binding.textViewForumTitle.setText(mForum.getTitle());
        binding.textViewForumCreatedAt.setText(mForum.getCreated_at());
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.editTextMessage.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(getContext(), "Message is required", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO : send the message to the API
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("message", message)
                            .add("thread_id", mForum.getThread_id())
                            .build();

                    Request request = new Request.Builder()
                            .url("https://www.theappsdr.com/api/message/add")
                            .addHeader("Authorization", "BEARER "+mAuth.getToken())
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            getMessages();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.editTextMessage.setText("");
                                }
                            });
                        }
                    });

                }
            }
        });
    }

    void getMessages(){
        //TODO: get the messages from the API
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.theappsdr.com/api/messages/"+mForum.getThread_id();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","BEARER "+mAuth.getToken())
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
                        messages.clear();
                        JSONObject mssgsObj = new JSONObject(body);
                        JSONArray messagesArray = mssgsObj.getJSONArray("messages");
                        for(int i = 0; i < messagesArray.length(); ++i){
                            Message message = new Message(messagesArray.getJSONObject(i));
                            messages.add(message);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }


    class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

        @NonNull
        @Override
        public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MessagesViewHolder(MessageRowItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
            Message message = messages.get(position);
            holder.setupUI(message);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        class MessagesViewHolder extends RecyclerView.ViewHolder {
            Message mMessage;
            MessageRowItemBinding mBinding;
            public MessagesViewHolder(MessageRowItemBinding mBinding) {
                super(mBinding.getRoot());
                this.mBinding = mBinding;
            }

            void setupUI(Message message){
                this.mMessage = message;
                mBinding.textViewMessage.setText(message.getMessage());
                mBinding.textViewMessageCreatedAt.setText(message.getCreated_at());
                mBinding.textViewMessageCreatorName.setText(message.getCreatedByFname() + " " + message.getCreatedByLname());

                //TODO: setup the rest of the UI the delete icon ..
                if(mMessage.getCreatedByUserId().equals(mAuth.getUser_id())){
                    mBinding.imageViewDeleteMessage.setVisibility(View.VISIBLE);
                    mBinding.imageViewDeleteMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OkHttpClient client = new OkHttpClient();
                            String url = "https://www.theappsdr.com/api/message/delete/"+mMessage.getMessage_id();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .addHeader("Authorization","BEARER "+mAuth.getToken())
                                    .build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if(response.isSuccessful()){
                                        getMessages();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    mBinding.imageViewDeleteMessage.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

}