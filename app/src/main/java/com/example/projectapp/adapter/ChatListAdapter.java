package com.example.projectapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectapp.R;
import com.example.projectapp.model.ChatItem;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.Holder> {

    private List<ChatItem> chatItemList;
    private Context context;

    public ChatListAdapter(List<ChatItem> chatItemList, Context context) {
        this.chatItemList = chatItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        ChatItem chat = chatItemList.get(position);
        holder.username.setText(chat.getUsername());
        holder.description.setText(chat.getDescription());
        holder.date.setText(chat.getDate());
        Glide.with(context).load(chat.getUrlProfile()).into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView username, description, date;
        private CircularImageView profilePic;

        public Holder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textViewUsername);
            description = itemView.findViewById(R.id.textViewDescription);
            date = itemView.findViewById(R.id.textViewDate);
            profilePic = itemView.findViewById(R.id.imageViewProfilePic);
        }
    }
}
