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
import com.example.projectapp.model.user.Users;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Users> users;
    private Context context;

    public ContactsAdapter(List<Users> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_contact_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = users.get(position);
        holder.username.setText(user.getUsername());
        holder.description.setText(user.getBio());
        Glide.with(context).load(user.getImageProfile()).into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username, description;
        private CircularImageView profilePic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textViewContactName);
            description = itemView.findViewById(R.id.textViewContactDescription);
            profilePic = itemView.findViewById(R.id.imageViewContact);
        }
    }
}
