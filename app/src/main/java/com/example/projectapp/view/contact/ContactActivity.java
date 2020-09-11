package com.example.projectapp.view.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import com.example.projectapp.R;
import com.example.projectapp.adapter.ContactsAdapter;
import com.example.projectapp.databinding.ActivityContactBinding;
import com.example.projectapp.databinding.ActivityPhoneLoginBinding;
import com.example.projectapp.model.user.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private static final String TAG = "ContactActivity";
    private ActivityContactBinding binding;
    private List<Users> usersList = new ArrayList<>();
    private FirebaseUser firebaseUser;
    private ContactsAdapter adapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contact);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        if(firebaseUser != null){
            getContactsList();
        }
    }

    private void getContactsList() {
        firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot s : queryDocumentSnapshots){
                    String userId = s.getString("userID");
                    String username = s.getString("username");
                    String imageUrl = s.getString("imageProfile");
                    String desc = s.getString("bio");

                    Users users = new Users();
                    users.setUserID(userId);
                    users.setUserID(username);
                    users.setBio(desc);
                    users.setImageProfile(imageUrl);

                    if (userId != null && !userId.equals(firebaseUser.getUid())) {
                        usersList.add(users);
                    }
                }
                adapter = new ContactsAdapter(usersList, ContactActivity.this);
                binding.contactRecyclerView.setAdapter(adapter);
            }
        });
    }
}