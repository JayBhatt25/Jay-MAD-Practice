package com.example.listview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {


    ArrayList<User> users;
    AdapterListener mListener;
    public UserRecyclerViewAdapter(ArrayList<User> usersList, AdapterListener mListener){
        this.mListener = mListener;
        this.users = usersList;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view, mListener);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
//        Log.d("from adapter", user.toString());
        holder.textViewName.setText(user.name);
        holder.ageTv.setText(user.age+" Years Old");
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mListener.goToProfile(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView ageTv;
        Button likeBtn;
        AdapterListener mlistener;
        public UserViewHolder(@NonNull View itemView, AdapterListener mListener) {
            super(itemView);
            this.mlistener = mListener;
            textViewName = itemView.findViewById(R.id.textViewName);
            likeBtn = itemView.findViewById(R.id.buttonLike);
            ageTv = itemView.findViewById(R.id.textViewAge);
        }
    }

    public interface AdapterListener {
        void goToProfile(User user);
    }

}
